--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 7.0.52.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 25.11.2017 2:35:49
-- Версия сервера: 5.5.23
-- Версия клиента: 4.1
--


-- 
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установить режим SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Установка кодировки, с использованием которой клиент будет посылать запросы на сервер
--
SET NAMES 'utf8';

-- 
-- Установка базы данных по умолчанию
--
USE enterpriceanalytics;

--
-- Описание для таблицы acount
--
DROP TABLE IF EXISTS acount;
CREATE TABLE acount (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  type VARCHAR(6) NOT NULL DEFAULT 'А/П',
  `group` TINYINT(4) NOT NULL DEFAULT 0,
  code VARCHAR(10) NOT NULL,
  parentId INT(11) DEFAULT NULL,
  anotation TEXT NOT NULL,
  positionX INT(11) NOT NULL,
  positionY INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_acount_acount_id FOREIGN KEY (parentId)
    REFERENCES acount(id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 12
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы bisnes_roles
--
DROP TABLE IF EXISTS bisnes_roles;
CREATE TABLE bisnes_roles (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  annotation TEXT NOT NULL,
  positionX INT(11) NOT NULL,
  positionY INT(11) NOT NULL,
  parent INT(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_bisnes_roles_bisnes_roles_id FOREIGN KEY (parent)
    REFERENCES bisnes_roles(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы documents
--
DROP TABLE IF EXISTS documents;
CREATE TABLE documents (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  annotation TEXT NOT NULL,
  positionX INT(11) NOT NULL,
  positionY INT(11) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы users
--
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  enabled TINYINT(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы `documents-hash-bisnes_roles`
--
DROP TABLE IF EXISTS `documents-hash-bisnes_roles`;
CREATE TABLE `documents-hash-bisnes_roles` (
  idDocument INT(11) NOT NULL,
  idBisnesRole INT(11) NOT NULL,
  annotation TEXT NOT NULL,
  PRIMARY KEY (idDocument, idBisnesRole),
  CONSTRAINT `FK_document-hash-bisnes_roles_bisnes_roles_id` FOREIGN KEY (idBisnesRole)
    REFERENCES bisnes_roles(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_document-hash-bisnes_roles_document_id` FOREIGN KEY (idDocument)
    REFERENCES documents(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_roles
--
DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
  user_role_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  INDEX fk_username_idx (username),
  UNIQUE INDEX uni_username_role (role, username),
  CONSTRAINT FK_user_roles_users_username FOREIGN KEY (username)
    REFERENCES users(username) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- Вывод данных для таблицы acount
--
INSERT INTO acount VALUES
(5, 'Касса', 'А', 1, '30', NULL, 'отображение наличніх средств', 0, 0),
(6, 'Расчеты с поставщиками', 'А/П', 0, '70', NULL, 'Счет предназначен для отбражения операция расчетов с поставщиками\n', 0, 0),
(7, 'Основная касса', 'А', 0, '301', 5, 'Наличніе средства для расчетов с покупателями и моментальніх расчетов', 0, 0),
(10, 'Резервная касса', 'А', 0, '302', 5, 'Наличніе средства для черизвичайной ситуации', 0, 0),
(11, 'Рысчеты с покупателями', 'А/П', 0, '40', NULL, 'Предназначен для отображения операций с покупателями', 0, 0);

-- 
-- Вывод данных для таблицы bisnes_roles
--
INSERT INTO bisnes_roles VALUES
(1, 'Дирекор', 'ваіваів', 15, 256, NULL),
(2, 'Бухгалтер', 'что-то там делает', 152, 245, 1),
(3, 'Кладовщик', 'Занимается колличственым учетом материальных средств\r\n\r\nОтветвенный за введение первичных документов по движению материалиных средств.', 245, 208, 2);

-- 
-- Вывод данных для таблицы documents
--
INSERT INTO documents VALUES
(1, 'Приходная накладная', 'Документ содержащий перечень материалов и товаров которіе призодят, их количество а также стоимость, соответственно указаной стоимости делаються движения по регистрам', 162, 235);

-- 
-- Вывод данных для таблицы users
--
INSERT INTO users VALUES
('alex', '123456', 1),
('mkyong', '123456', 1);

-- 
-- Вывод данных для таблицы `documents-hash-bisnes_roles`
--
INSERT INTO `documents-hash-bisnes_roles` VALUES
(1, 2, 'Віборочно проверяет правильность заполнения программи'),
(1, 3, 'Вводит в программу и отвечает за сохранность первичной документации до конца месяца. \r\nв 25-30 днях месяца документі сдаются бухгалтеру для перепроверки правильности учета.\r\n');

-- 
-- Вывод данных для таблицы user_roles
--
INSERT INTO user_roles VALUES
(2, 'mkyong', 'ROLE_ADMIN'),
(3, 'alex', 'ROLE_USER'),
(1, 'mkyong', 'ROLE_USER');

-- 
-- Восстановить предыдущий режим SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;