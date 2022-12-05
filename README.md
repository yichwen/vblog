# vblog
[vblog](https://github.com/lenve/VBlog) v部落 后端应用学习

## VBlog 应用分析



### 数据模型

#### 文章

- 题目，内容，简介，类别，作者，发布时间，更新时间，状态，阅读次数

```sql
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `mdContent` text COMMENT 'md文件源码',
  `htmlContent` text COMMENT 'html源码',
  `summary` text,
  `cid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `publishDate` datetime DEFAULT NULL,
  `editTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '0表示草稿箱，1表示已发表，2表示已删除',
  `pageView` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`),
  KEY `uid` (`uid`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`id`),
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;
```

#### 文章与标签关系表

- 文章唯一标识，标签唯一标识

```sql
CREATE TABLE `article_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`),
  KEY `article_tags_ibfk_1` (`aid`),
  CONSTRAINT `article_tags_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`id`) ON DELETE CASCADE,
  CONSTRAINT `article_tags_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
```

#### 标签

```sql
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tagName` (`tagName`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
```

#### 文章类别

- 类别名称

```sql
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cateName` varchar(64) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
```

#### 评论

- 文章，评论内容，父评论，发表者

```sql
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL,
  `content` text,
  `publishDate` datetime DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL COMMENT '-1表示正常回复，其他值表示是评论的回复',
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `aid` (`aid`),
  KEY `uid` (`uid`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`id`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`parentId`) REFERENCES `comments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 角色

- 角色名称

```sql
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
```

#### 用户与角色关系表

```sql
CREATE TABLE `roles_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) DEFAULT '2',
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  KEY `roles_user_ibfk_2` (`uid`),
  CONSTRAINT `roles_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `roles` (`id`),
  CONSTRAINT `roles_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;
```

#### 用户

```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `email` varchar(64) DEFAULT NULL,
  `userface` varchar(255) DEFAULT NULL,
  `regTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
```

#### 文章的阅读计数

```sql
CREATE TABLE `pv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countDate` date DEFAULT NULL,
  `pv` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pv_ibfk_1` (`uid`),
  CONSTRAINT `pv_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
```

#### pvview

- 获取每一个作者的所有 pageview

#### totalpvview

- 每一个作者统计文章的 page view
