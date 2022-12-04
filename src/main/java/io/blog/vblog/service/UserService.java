package io.blog.vblog.service;

import io.blog.vblog.dto.UserCreationDto;
import io.blog.vblog.dto.admin.RoleDto;
import io.blog.vblog.dto.admin.UserDto;
import io.blog.vblog.entity.Role;
import io.blog.vblog.entity.User;
import io.blog.vblog.entity.UserRole;
import io.blog.vblog.entity.UserRoleId;
import io.blog.vblog.repository.UserRepository;
import io.blog.vblog.repository.UserRoleRepository;
import io.blog.vblog.util.P;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    private static final Function<User, UserDto> mapper = (user) -> {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        List<RoleDto> roleDtoList = user.getUserRoles().stream().map(userRole -> {
            RoleDto roleDto = new RoleDto();
            Role role = userRole.getRole();
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
        userDto.setRoles(roleDtoList);
        return userDto;
    };

    public void createUser(UserCreationDto userCreationDto) {
        User user = new User();
        BeanUtils.copyProperties(userCreationDto, user);
        user.setRegistrationDateTime(new Date());
        Role defaultRole = roleService.getDefaultUserRole();
        UserRole userRole = new UserRole();
        userRole.setRole(defaultRole);
        userRole.setUser(user);
        user.getUserRoles().add(userRole);
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            String message = String.format("user with id [%d] is not found", id);
            throw new IllegalStateException(message);
        });
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public P<UserDto> getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return P.getInstance(usersPage, mapper);
    }

    public UserDto getUserById(Long id) {
        return mapper.apply(getUser(id));
    }

    public void deleteUserById(Long id) {
        User user = getUser(id);
        if (user.getArticles().size() > 0) {
            throw new IllegalStateException("still have articles linked with this user");
        }
        userRepository.delete(user);
    }

    public void toggleUserEnabled(Long id) {
        User user = getUser(id);
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

    public void updateUserRoles(Long userId, List<Long> roleIds) {
        List<UserRole> existingUserRoles = userRoleRepository.findByIdUserId(userId);
        User user = getUser(userId);
        // delete all roles
        List<UserRole> deleteUserRoles = findUserRolesNotInRoleIds(existingUserRoles, roleIds);
        userRoleRepository.deleteAll(deleteUserRoles);
        // add all roles
        List<Long> newRoleIds = findRoleIdsNotInUserRoles(existingUserRoles, roleIds);
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : newRoleIds) {
            Role role = roleService.getRole(roleId);
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRole.setId(new UserRoleId(userId, roleId));
            userRoles.add(userRole);
        }
        if (userRoles.size() > 0) {
            userRoleRepository.saveAll(userRoles);
        }
    }

    private List<UserRole> findUserRolesNotInRoleIds(Collection<UserRole> userRoles, Collection<Long> roleIds) {
        return userRoles.stream()
                .filter(userRole -> roleIds.stream().noneMatch(roleId -> userRole.getRole().getId().equals(roleId)))
                .collect(Collectors.toList());
    }

    private List<Long> findRoleIdsNotInUserRoles(Collection<UserRole> userRoles, Collection<Long> roleIds) {
        return roleIds.stream()
                .filter(roleId -> userRoles.stream().noneMatch(userRole -> userRole.getRole().getId().equals(roleId)))
                .collect(Collectors.toList());
    }

}
