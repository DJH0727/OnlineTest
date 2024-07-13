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
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,1,1,' MAC地址在每次启动后都会改变.',NULL),(2,1,2,' MAC地址一共有48比特，它们从出厂时就被固化在网卡中.',NULL),(3,1,3,' MAC地址也称做物理地址，或通常所说的计算机的硬件地址.',NULL),(4,1,4,' MAC地址每次启动后都不会变化.',NULL),(6,2,1,'7E FE 27 7D 5D 65 7D 7E',NULL),(7,2,2,' 7E FE 27 7D 65 7E',NULL),(8,2,3,'7D 5E FE 27 7D 5D 7D 5D 65 7D 5E',NULL),(9,2,4,'7E FE 27 7D 7D 65 7E',NULL),(10,3,1,'任何一个关系模式一定有键。',NULL),(11,3,2,'任何一个包含两个属性的关系模式一定满足3NF。',NULL),(12,3,3,'7任何一个包含两个属性的关系模式一定满足BCNF。',NULL),(13,3,4,'任何一个包含三个属性的关系模式一定满足2NF。',NULL),(14,4,1,' 通信技术',NULL),(15,4,2,'电子技术 ',NULL),(16,4,3,'工业技术    ',NULL),(17,4,4,'存储技术',NULL),(56,5,1,'.class',NULL),(57,5,2,'.java',NULL),(58,5,3,'.cpp',NULL),(59,5,4,'.txt',NULL),(60,6,1,'a[0]; ',NULL),(61,6,2,'a[a.length-1];',NULL),(62,6,3,'a[3]; ',NULL),(63,6,4,'int i=1； a[i];',NULL),(64,7,1,'终端、电缆、计算机',NULL),(65,7,2,'信号发生器、通信线路、信号接收设备',NULL),(66,7,3,'源系统、传输系统、 目的系统',NULL),(67,7,4,'终端、通信设施、接收设备',NULL),(72,9,1,'仅Ⅲ， Ⅳ  ',NULL),(73,9,2,'仅Ⅰ， Ⅱ， Ⅲ',NULL),(74,9,3,'仅Ⅰ， Ⅱ， Ⅳ',NULL),(75,9,4,'Ⅰ， Ⅱ， Ⅲ ， Ⅳ',NULL),(76,10,1,'True',NULL),(77,10,2,'False',NULL),(86,11,1,'A',NULL),(87,11,2,'B',NULL),(88,11,3,'C',NULL),(89,11,4,'D',NULL);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_type`
--

DROP TABLE IF EXISTS `question_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_type` (
                                 `typeid` int NOT NULL,
                                 `typename` varchar(50) NOT NULL,
                                 PRIMARY KEY (`typeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_type`
--

LOCK TABLES `question_type` WRITE;
/*!40000 ALTER TABLE `question_type` DISABLE KEYS */;
INSERT INTO `question_type` VALUES (1,'单选题'),(2,'多选题'),(3,'判断题'),(4,'填空题'),(5,'简答题');
/*!40000 ALTER TABLE `question_type` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,3,1,'以下关于MAC的说法中错误的是.',NULL,0,'A'),(2,3,1,'一个PPP帧的数据部分（用十六进制写出）是7D 5E FE 27 7D 5D 7D 5D 65 7D 5E。\r\n试问真正的数据是什么（用十六进制写出）？',NULL,0,'D'),(3,1,1,'在下列关于规范化理论的叙述中，不正确的是_________。\r\n',NULL,0,'D'),(4,3,1,'计算机网络是计算机技术和（ ）技术的产物。',NULL,0,'A'),(5,5,1,'使用Java语言编写的源程序保存时的文件扩展名是（ ）。',NULL,0,'B'),(6,5,1,'设有数组的定义int[] a = new int[3]，则下面对数组元素的引用错误的是（ ）。',NULL,0,'D'),(7,3,1,'通信系统必须具备的三个基本要素是（）。',NULL,0,'C'),(9,3,1,'下列关于IP路由器功能的描述中，正确的是\r\nⅠ.运行路由协议，设备路由表\r\nⅡ.检测到拥塞时，合理丢弃IP分组\r\nⅢ.对收到的IP分组头进行差错校验，确保传输的IP分组不丢失\r\nⅣ.根据收到的IP分组的目的IP地址，将其转发至合适的输出线路上\r\n',NULL,0,'C'),(10,1,3,'这道题是对的吗？',NULL,0,'A'),(11,1,1,'TESTA',NULL,0,'A');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `roleid` int NOT NULL,
                        `rolename` varchar(50) NOT NULL,
                        PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (0,'Admin'),(1,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
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
                             `imgurl` varchar(255) DEFAULT '/avatar/default.jpg',
                             PRIMARY KEY (`uid`),
                             CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('37220222203581','djh','M','17382319551','2724948893@qq.com','/avatar/default.jpg'),('admin','admin','M',NULL,NULL,'/avatar/default.jpg'),('user1','user1','M',NULL,NULL,'/avatar/default.jpg'),('user10','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg'),('user11','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg'),('user2','user2','M','183369','37220222203581@stu.xmu.edu.cn','/avatar/default.jpg'),('user5','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg'),('user6','user6',NULL,NULL,NULL,'/avatar/default.jpg'),('user7','user7',NULL,NULL,NULL,'/avatar/default.jpg'),('user8','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg');
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
INSERT INTO `users` VALUES ('37220222203581',1,'djh666'),('admin',0,'123456'),('user1',1,'user1'),('user10',1,'123456'),('user11',1,'123456'),('user2',1,'user2'),('user5',1,'12345'),('user6',1,'123456'),('user7',1,'123456'),('user8',1,'123456');
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

-- Dump completed on 2024-07-13 15:21:57
