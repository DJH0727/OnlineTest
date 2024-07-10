-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: onlineexam
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
                           `cid` int NOT NULL,
                           `cname` varchar(30) NOT NULL,
                           `is_major` tinyint(1) NOT NULL,
                           PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'数据库原理',1),(2,'计算机组成原理',1),(3,'计算机网络',1),(4,'算法设计与分析',1),(5,'JAVA程序设计',1),(101,'math',0);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
                           `opid` int NOT NULL AUTO_INCREMENT,
                           `qid` int NOT NULL,
                           `op` int NOT NULL,
                           `optext` varchar(255) DEFAULT NULL,
                           `opurl` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`opid`),
                           KEY `qid` (`qid`),
                           CONSTRAINT `options_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,1,1,' MAC地址在每次启动后都会改变',NULL),(2,1,2,' MAC地址一共有48比特，它们从出厂时就被固化在网卡中',NULL),(3,1,3,' MAC地址也称做物理地址，或通常所说的计算机的硬件地址',NULL),(4,1,4,' MAC地址每次启动后都不会变化',NULL),(6,2,1,'7E FE 27 7D 5D 65 7D 7E',NULL),(7,2,2,' 7E FE 27 7D 65 7E',NULL),(8,2,3,'7D 5E FE 27 7D 5D 7D 5D 65 7D 5E',NULL),(9,2,4,'7E FE 27 7D 7D 65 7E',NULL),(10,3,1,'任何一个关系模式一定有键。',NULL),(11,3,2,'任何一个包含两个属性的关系模式一定满足3NF。',NULL),(12,3,3,'7任何一个包含两个属性的关系模式一定满足BCNF。',NULL),(13,3,4,'任何一个包含三个属性的关系模式一定满足2NF。',NULL);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
                             `qid` int NOT NULL AUTO_INCREMENT,
                             `cid` int NOT NULL,
                             `qtype` int NOT NULL,
                             `qtext` text NOT NULL,
                             `qurl` varchar(255) DEFAULT NULL,
                             `qscore` int DEFAULT NULL,
                             `answer` varchar(20) DEFAULT NULL,
                             PRIMARY KEY (`qid`),
                             KEY `cid` (`cid`),
                             CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `courses` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,3,1,'以下关于MAC的说法中错误的是',NULL,0,'A'),(2,3,1,'一个PPP帧的数据部分（用十六进制写出）是7D 5E FE 27 7D 5D 7D 5D 65 7D 5E。\r\n试问真正的数据是什么（用十六进制写出）？',NULL,0,'D'),(3,1,1,'在下列关于规范化理论的叙述中，不正确的是_________。\r\n',NULL,0,'D');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
                             `uid` varchar(20) NOT NULL,
                             `name` varchar(30) NOT NULL,
                             `sex` enum('M','F') DEFAULT NULL,
                             `phone` varchar(30) DEFAULT NULL,
                             `email` varchar(30) DEFAULT NULL,
                             PRIMARY KEY (`uid`),
                             CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('37220222203581','djh','M','17382319551','2724948893@qq.com'),('admin','admin','M',NULL,NULL),('user1','user1','M',NULL,NULL),('user2','user2','M','183369','37220222203581@stu.xmu.edu.cn'),('user3','user3','M','','sarah3122212@gmail.com'),('user4','djh',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `uid` varchar(20) NOT NULL,
                         `role` int NOT NULL,
                         `pwd` varchar(20) NOT NULL,
                         PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('37220222203581',1,'djh666'),('admin',0,'123456'),('user1',1,'user1'),('user2',1,'user2'),('user3',1,'user3'),('user4',1,'user4');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-10 18:49:05
