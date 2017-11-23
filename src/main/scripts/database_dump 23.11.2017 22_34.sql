DROP TABLE IF EXISTS `acount`;

CREATE TABLE `acount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(6) NOT NULL DEFAULT '╨Р/╨Я',
  `group` tinyint(4) NOT NULL DEFAULT '0',
  `code` varchar(10) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `anotation` text NOT NULL,
  `positionX` int(11) NOT NULL,
  `positionY` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_acount_acount_id` (`parentId`),
  CONSTRAINT `FK_acount_acount_id` FOREIGN KEY (`parentId`) REFERENCES `acount` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



LOCK TABLES `acount` WRITE;
/*!40000 ALTER TABLE `acount` DISABLE KEYS */;
INSERT INTO `acount` VALUES (5,'╨Ъ╨░╤Б╤Б╨░','╨Р',1,'30',NULL,'╨╛╤В╨╛╨▒╤А╨░╨╢╨╡╨╜╨╕╨╡ ╨╜╨░╨╗╨╕╤З╨╜╤Ц╤Е ╤Б╤А╨╡╨┤╤Б╤В╨▓',0,0),(6,'╨а╨░╤Б╤З╨╡╤В╤Л ╤Б ╨┐╨╛╤Б╤В╨░╨▓╤Й╨╕╨║╨░╨╝╨╕','╨Р/╨Я',0,'70',NULL,'╨б╤З╨╡╤В ╨┐╤А╨╡╨┤╨╜╨░╨╖╨╜╨░╤З╨╡╨╜ ╨┤╨╗╤П ╨╛╤В╨▒╤А╨░╨╢╨╡╨╜╨╕╤П ╨╛╨┐╨╡╤А╨░╤Ж╨╕╤П ╤А╨░╤Б╤З╨╡╤В╨╛╨▓ ╤Б ╨┐╨╛╤Б╤В╨░╨▓╤Й╨╕╨║╨░╨╝╨╕\n',0,0),(7,'╨Ю╤Б╨╜╨╛╨▓╨╜╨░╤П ╨║╨░╤Б╤Б╨░','╨Р',0,'301',5,'╨Э╨░╨╗╨╕╤З╨╜╤Ц╨╡ ╤Б╤А╨╡╨┤╤Б╤В╨▓╨░ ╨┤╨╗╤П ╤А╨░╤Б╤З╨╡╤В╨╛╨▓ ╤Б ╨┐╨╛╨║╤Г╨┐╨░╤В╨╡╨╗╤П╨╝╨╕ ╨╕ ╨╝╨╛╨╝╨╡╨╜╤В╨░╨╗╤М╨╜╤Ц╤Е ╤А╨░╤Б╤З╨╡╤В╨╛╨▓',0,0),(10,'╨а╨╡╨╖╨╡╤А╨▓╨╜╨░╤П ╨║╨░╤Б╤Б╨░','╨Р',0,'302',5,'╨Э╨░╨╗╨╕╤З╨╜╤Ц╨╡ ╤Б╤А╨╡╨┤╤Б╤В╨▓╨░ ╨┤╨╗╤П ╤З╨╡╤А╨╕╨╖╨▓╨╕╤З╨░╨╣╨╜╨╛╨╣ ╤Б╨╕╤В╤Г╨░╤Ж╨╕╨╕',0,0),(11,'╨а╤Л╤Б╤З╨╡╤В╤Л ╤Б ╨┐╨╛╨║╤Г╨┐╨░╤В╨╡╨╗╤П╨╝╨╕','╨Р/╨Я',0,'40',NULL,'╨Я╤А╨╡╨┤╨╜╨░╨╖╨╜╨░╤З╨╡╨╜ ╨┤╨╗╤П ╨╛╤В╨╛╨▒╤А╨░╨╢╨╡╨╜╨╕╤П ╨╛╨┐╨╡╤А╨░╤Ж╨╕╨╣ ╤Б ╨┐╨╛╨║╤Г╨┐╨░╤В╨╡╨╗╤П╨╝╨╕',0,0);
/*!40000 ALTER TABLE `acount` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `bisnes_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bisnes_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `annotation` text NOT NULL,
  `positionX` int(11) NOT NULL,
  `positionY` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bisnes_roles_bisnes_roles_id` (`parent`),
  CONSTRAINT `FK_bisnes_roles_bisnes_roles_id` FOREIGN KEY (`parent`) REFERENCES `bisnes_roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bisnes_roles`
--

LOCK TABLES `bisnes_roles` WRITE;
/*!40000 ALTER TABLE `bisnes_roles` DISABLE KEYS */;
INSERT INTO `bisnes_roles` VALUES (1,'╨Ф╨╕╤А╨╡╨║╨╛╤А','╨▓╨░╤Ц╨▓╨░╤Ц╨▓',15,256,NULL),(2,'╨С╤Г╤Е╨│╨░╨╗╤В╨╡╤А','╤З╤В╨╛-╤В╨╛ ╤В╨░╨╝ ╨┤╨╡╨╗╨░╨╡╤В',152,245,1),(3,'╨Ъ╨╗╨░╨┤╨╛╨▓╤Й╨╕╨║','╨Ч╨░╨╜╨╕╨╝╨░╨╡╤В╤Б╤П ╨║╨╛╨╗╨╗╨╕╤З╤Б╤В╨▓╨╡╨╜╤Л╨╝ ╤Г╤З╨╡╤В╨╛╨╝ ╨╝╨░╤В╨╡╤А╨╕╨░╨╗╤М╨╜╤Л╤Е ╤Б╤А╨╡╨┤╤Б╤В╨▓\r\n\r\n╨Ю╤В╨▓╨╡╤В╨▓╨╡╨╜╨╜╤Л╨╣ ╨╖╨░ ╨▓╨▓╨╡╨┤╨╡╨╜╨╕╨╡ ╨┐╨╡╤А╨▓╨╕╤З╨╜╤Л╤Е ╨┤╨╛╨║╤Г╨╝╨╡╨╜╤В╨╛╨▓ ╨┐╨╛ ╨┤╨▓╨╕╨╢╨╡╨╜╨╕╤О ╨╝╨░╤В╨╡╤А╨╕╨░╨╗╨╕╨╜╤Л╤Е ╤Б╤А╨╡╨┤╤Б╤В╨▓.',245,208,2);
/*!40000 ALTER TABLE `bisnes_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`role`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `FK_user_roles_users_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (2,'mkyong','ROLE_ADMIN'),(3,'alex','ROLE_USER'),(1,'mkyong','ROLE_USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('alex','123456',1),('mkyong','123456',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
