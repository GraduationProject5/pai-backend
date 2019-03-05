-- MySQL dump 10.13  Distrib 5.7.20, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: GraduationProject5
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `component`
--

DROP TABLE IF EXISTS `component`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `component`
(
  `component_id`   int(11)      NOT NULL AUTO_INCREMENT,
  `component_name` varchar(100) NOT NULL,
  PRIMARY KEY (`component_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `component`
--

LOCK TABLES `component` WRITE;
/*!40000 ALTER TABLE `component`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `component`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiment`
--

DROP TABLE IF EXISTS `experiment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experiment`
(
  `experiment_id`   bigint(20)   NOT NULL AUTO_INCREMENT,
  `experiment_name` varchar(100) NOT NULL,
  `description`     varchar(100) DEFAULT NULL,
  PRIMARY KEY (`experiment_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiment`
--

LOCK TABLES `experiment` WRITE;
/*!40000 ALTER TABLE `experiment`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `experiment`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence`
(
  `next_val` bigint(20) DEFAULT NULL
) ENGINE = MyISAM
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence`
  DISABLE KEYS */;
INSERT INTO `hibernate_sequence`
VALUES (6);
/*!40000 ALTER TABLE `hibernate_sequence`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model`
(
  `model_id`            bigint(20) NOT NULL AUTO_INCREMENT,
  `first_component_id`  int(11)    NOT NULL,
  `second_component_id` int(11)    NOT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `model`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_experiment_table_component`
--

DROP TABLE IF EXISTS `r_experiment_table_component`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_experiment_table_component`
(
  `retc_id`               bigint(20)   NOT NULL AUTO_INCREMENT,
  `experiment_id`         bigint(20)   NOT NULL,
  `first_component_type`  varchar(100) NOT NULL,
  `second_component_type` varchar(100) NOT NULL,
  `first_component_id`    bigint(20)   NOT NULL,
  `second_component_id`   bigint(20)   NOT NULL,
  PRIMARY KEY (`retc_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_experiment_table_component`
--

LOCK TABLES `r_experiment_table_component` WRITE;
/*!40000 ALTER TABLE `r_experiment_table_component`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `r_experiment_table_component`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_section_component`
--

DROP TABLE IF EXISTS `r_section_component`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_section_component`
(
  `rsc_id`       bigint(20) NOT NULL AUTO_INCREMENT,
  `section_id`   bigint(20) NOT NULL,
  `component_id` bigint(20) NOT NULL,
  PRIMARY KEY (`rsc_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_section_component`
--

LOCK TABLES `r_section_component` WRITE;
/*!40000 ALTER TABLE `r_section_component`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `r_section_component`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_experiment`
--

DROP TABLE IF EXISTS `r_user_experiment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_user_experiment`
(
  `rue_id`        bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id`       bigint(20) NOT NULL,
  `experiment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`rue_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_experiment`
--

LOCK TABLES `r_user_experiment` WRITE;
/*!40000 ALTER TABLE `r_user_experiment`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `r_user_experiment`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_user_table`
--

DROP TABLE IF EXISTS `r_user_table`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_user_table`
(
  `rut_id`   bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id`  bigint(20) NOT NULL,
  `table_id` bigint(20) NOT NULL,
  PRIMARY KEY (`rut_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_user_table`
--

LOCK TABLES `r_user_table` WRITE;
/*!40000 ALTER TABLE `r_user_table`
  DISABLE KEYS */;
INSERT INTO `r_user_table`
VALUES (5, 1, 4);
/*!40000 ALTER TABLE `r_user_table`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section`
(
  `section_id`        int(11)      NOT NULL AUTO_INCREMENT,
  `section_name`      varchar(100) NOT NULL,
  `father_section_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`section_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `section`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tablePO`
--

DROP TABLE IF EXISTS `tablePO`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tablePO`
(
  `table_id`    bigint(20)   NOT NULL AUTO_INCREMENT,
  `table_name`  varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tablePO`
--

LOCK TABLES `tablePO` WRITE;
/*!40000 ALTER TABLE `tablePO`
  DISABLE KEYS */;
INSERT INTO `tablePO`
VALUES (4, 'user1_testCreateTableByColumn', 'testCreateTableByColumn');
/*!40000 ALTER TABLE `tablePO`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user`
(
  `user_id`  bigint(20)  NOT NULL AUTO_INCREMENT,
  `email`    varchar(50) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
  DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, 'javalem@163.com', 'asdasd'),
       (3, 'trere@rere.com', 'asdasdasd');
/*!40000 ALTER TABLE `user`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user1_testCreateTableByColumn`
--

DROP TABLE IF EXISTS `user1_testCreateTableByColumn`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user1_testCreateTableByColumn`
(
  `c2` varchar(100) NOT NULL,
  `c1` int(11)      NOT NULL,
  PRIMARY KEY (`c1`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user1_testCreateTableByColumn`
--

LOCK TABLES `user1_testCreateTableByColumn` WRITE;
/*!40000 ALTER TABLE `user1_testCreateTableByColumn`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `user1_testCreateTableByColumn`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2019-02-18 23:15:47
