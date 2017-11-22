
DROP TABLE IF EXISTS `acount`;

CREATE TABLE `acount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(6) NOT NULL DEFAULT 'А/П',
  `group` tinyint(4) NOT NULL DEFAULT '0',
  `code` varchar(10) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `anotation` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_acount_parent` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

LOCK TABLES `acount` WRITE;
INSERT INTO `acount` VALUES (5,'Касса','А',0,'30',NULL,'отображение наличніх средств');
UNLOCK TABLES;



DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES ('alex','123456',1),('mkyong','123456',1);
UNLOCK TABLES;




DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`role`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `FK_user_roles_users_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `user_roles` WRITE;
INSERT INTO `user_roles` VALUES (2,'mkyong','ROLE_ADMIN'),(3,'alex','ROLE_USER'),(1,'mkyong','ROLE_USER');
UNLOCK TABLES;


