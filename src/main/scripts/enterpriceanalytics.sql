--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 7.0.52.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 01.12.2017 21:47:05
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
AUTO_INCREMENT = 13
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
AUTO_INCREMENT = 10
AVG_ROW_LENGTH = 4096
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
AUTO_INCREMENT = 17
AVG_ROW_LENGTH = 3276
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
  id INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (idDocument, idBisnesRole),
  UNIQUE INDEX id (id),
  CONSTRAINT `FK_document-hash-bisnes_roles_bisnes_roles_id` FOREIGN KEY (idBisnesRole)
    REFERENCES bisnes_roles(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_document-hash-bisnes_roles_document_id` FOREIGN KEY (idDocument)
    REFERENCES documents(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AUTO_INCREMENT = 23
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы correspondence
--
DROP TABLE IF EXISTS correspondence;
CREATE TABLE correspondence (
  id INT(11) NOT NULL AUTO_INCREMENT,
  debet INT(11) NOT NULL,
  credit INT(11) NOT NULL,
  documentId INT(11) NOT NULL,
  annotation TEXT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_correspondence_acount_id FOREIGN KEY (debet)
    REFERENCES acount(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_correspondence_acount2_id FOREIGN KEY (credit)
    REFERENCES acount(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_correspondence_documents_id FOREIGN KEY (documentId)
    REFERENCES documents(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 16384
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
(5, 'Касса', 'А', 1, '30', NULL, 'отображение наличніх средств р', 109, 179),
(6, 'Расчеты с поставщиками', 'П/А', 0, '70', NULL, 'Счет предназначен для отбражения операция расчетов с поставщиками\r\n', 231, 442),
(7, 'Основная касса', 'А', 0, '301', 5, 'Наличніе средства для расчетов с покупателями и моментальніх расчетов', 0, 0),
(10, 'Резервная касса', 'А', 0, '302', 5, 'Наличніе средства для черизвичайной ситуации', 0, 0),
(11, 'Расчеты с покупателями', 'П/А', 0, '40', NULL, 'Предназначен для отображения операций с покупателями', 295, 188);

-- 
-- Вывод данных для таблицы bisnes_roles
--
INSERT INTO bisnes_roles VALUES
(4, 'Кладовщик', 'Отвечает за материальные ценности', 25, 230, 7),
(5, 'Бухгалтер', 'Отвечает за финансы', 228, 146, 7),
(6, 'РОП', 'Работник отдел продаж', 170, 163, 7),
(7, 'Директор', 'принимает основные решения', 5, 414, NULL);

-- 
-- Вывод данных для таблицы documents
--
INSERT INTO documents VALUES
(4, 'Расходная накладная', 'Документ предназначен для отображения факта передачи материальных ценностей покупателю', 485, 82),
(13, 'Заявка в СЭС', 'Заявка на обслуживание сервисной обслужбой\r\nДокумент отображает факт обращения клиента в сервисную службу ', 200, 225),
(14, 'Поступление на счет', 'Документ предназначен для отображения факта поступления денег на счет в банке', 456, 499),
(15, 'Расход со счета', 'Документ предназначен для отображения факта списания денег с банковского счета', 197, 34),
(16, 'Приходная накладная', 'Документ предназначен для отбражения операций постепления материальных ценностей и взятие их на учет (в совновном предназначеня для отображения факта поставки материалов и товаров поставщиками)', 156, 128);

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
(4, 4, 'офрмляэт этот документ при передачи материальных ценностей', 18),
(13, 6, 'Оформляет обращения клиента и описывает, что клиенту необходимо', 17),
(14, 5, 'заполняет документ и отвечает за правильно его правильность', 19),
(15, 5, 'отвечает за заполнение документа, несет материальную ответственнотсть за допущеные ошибки', 20),
(16, 4, 'созддает документ при факческой поступлении материальных ценностей ', 21);

-- 
-- Вывод данных для таблицы correspondence
--
INSERT INTO correspondence VALUES
(1, 11, 7, 4, 'необходимо поменять на документ оплата счета или что-то на подобии того, а пока считаем что при поступлении товара или аматериалов расплачиваемся с поставщиком налчкой во время приема товара ty');

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