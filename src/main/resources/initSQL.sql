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
INSERT INTO `courses` VALUES (1,'数据库原理',1),(2,'计算机组成原理',1),(3,'计算机网络',1),(4,'算法设计与分析',1),(5,'JAVA程序设计',1);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
                         `eid` int NOT NULL AUTO_INCREMENT,
                         `ename` varchar(50) DEFAULT NULL,
                         `pid` int NOT NULL,
                         `uid` varchar(20) NOT NULL,
                         `edate` date DEFAULT NULL,
                         `etime` int DEFAULT NULL,
                         `status` int DEFAULT '0',
                         `totalscore` int DEFAULT NULL,
                         `userscore` int DEFAULT NULL,
                         `cid1` int DEFAULT NULL,
                         `cid2` int DEFAULT NULL,
                         PRIMARY KEY (`eid`),
                         KEY `pid` (`pid`),
                         KEY `uid` (`uid`),
                         KEY `cid1` (`cid1`),
                         KEY `cid2` (`cid2`),
                         CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `paper` (`pid`),
                         CONSTRAINT `exams_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`),
                         CONSTRAINT `exams_ibfk_3` FOREIGN KEY (`cid1`) REFERENCES `courses` (`cid`),
                         CONSTRAINT `exams_ibfk_4` FOREIGN KEY (`cid2`) REFERENCES `courses` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (1,'我的考试',1,'37220222203581','2024-07-14',60,0,6,NULL,1,3),(2,'我的考试',2,'37220222203581','2024-07-14',60,0,15,NULL,1,3),(3,'我的考试',3,'37220222203581','2024-07-14',60,0,15,NULL,3,1),(4,'我的考试',4,'37220222203581','2024-07-14',60,0,30,NULL,3,1),(5,'我的考试12',5,'37220222203581','2024-07-15',10,0,15,NULL,1,3);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,1,1,' MAC地址在每次启动后都会改变.',NULL),(2,1,2,' MAC地址一共有48比特，它们从出厂时就被固化在网卡中.',NULL),(3,1,3,' MAC地址也称做物理地址，或通常所说的计算机的硬件地址.',NULL),(4,1,4,' MAC地址每次启动后都不会变化.',NULL),(6,2,1,'7E FE 27 7D 5D 65 7D 7E',NULL),(7,2,2,' 7E FE 27 7D 65 7E',NULL),(8,2,3,'7D 5E FE 27 7D 5D 7D 5D 65 7D 5E',NULL),(9,2,4,'7E FE 27 7D 7D 65 7E',NULL),(10,3,1,'任何一个关系模式一定有键。',NULL),(11,3,2,'任何一个包含两个属性的关系模式一定满足3NF。',NULL),(12,3,3,'7任何一个包含两个属性的关系模式一定满足BCNF。',NULL),(13,3,4,'任何一个包含三个属性的关系模式一定满足2NF。',NULL),(14,4,1,' 通信技术',NULL),(15,4,2,'电子技术 ',NULL),(16,4,3,'工业技术    ',NULL),(17,4,4,'存储技术',NULL),(56,5,1,'.class',NULL),(57,5,2,'.java',NULL),(58,5,3,'.cpp',NULL),(59,5,4,'.txt',NULL),(60,6,1,'a[0]; ',NULL),(61,6,2,'a[a.length-1];',NULL),(62,6,3,'a[3]; ',NULL),(63,6,4,'int i=1； a[i];',NULL),(64,7,1,'终端、电缆、计算机',NULL),(65,7,2,'信号发生器、通信线路、信号接收设备',NULL),(66,7,3,'源系统、传输系统、 目的系统',NULL),(67,7,4,'终端、通信设施、接收设备',NULL),(72,9,1,'仅Ⅲ， Ⅳ  ',NULL),(73,9,2,'仅Ⅰ， Ⅱ， Ⅲ',NULL),(74,9,3,'仅Ⅰ， Ⅱ， Ⅳ',NULL),(75,9,4,'Ⅰ， Ⅱ， Ⅲ ， Ⅳ',NULL),(86,11,1,'A',NULL),(87,11,2,'B',NULL),(88,11,3,'C',NULL),(89,11,4,'D',NULL),(90,12,1,'数据库系统',NULL),(91,12,2,'文件系统',NULL),(92,12,3,'人工管理',NULL),(93,12,4,'数据项管理',NULL),(94,13,1,'Data Base System',NULL),(95,13,2,'Data Dictionary',NULL),(96,13,3,'Data Base',NULL),(97,13,4,'Data Base Managerement System',NULL),(98,14,1,'数据库避免了一切数据的重复',NULL),(99,14,2,'若系统是完全可以控制的，则系统可确保更新时的一致性',NULL),(100,14,3,'数据库中的数据可以共享',NULL),(101,14,4,'数据库减少了数据冗余',NULL),(102,15,1,'True',NULL),(103,15,2,'False',NULL),(104,16,1,'True',NULL),(105,16,2,'False',NULL),(106,17,1,'True',NULL),(107,17,2,'False',NULL),(108,18,1,'True',NULL),(109,18,2,'False',NULL),(110,19,1,'True',NULL),(111,19,2,'False',NULL),(112,20,1,'True',NULL),(113,20,2,'False',NULL),(114,21,1,'True',NULL),(115,21,2,'False',NULL),(116,22,1,'True',NULL),(117,22,2,'False',NULL),(118,23,1,'True',NULL),(119,23,2,'False',NULL),(120,24,1,'True',NULL),(121,24,2,'False',NULL),(122,25,1,'每个数据文件中有且只有一个主数据文件',NULL),(123,25,2,'日志文件可以存在于任意文件组中',NULL),(124,25,3,'主数据文件默认为primary文件组',NULL),(125,25,4,'文件组是为了更好的实现数据库文件组织',NULL),(126,26,1,'经常被查询的列不适合建索引',NULL),(127,26,2,'列值唯一的列适合建索引',NULL),(128,26,3,'有很多重复值的列适合建索引 ',NULL),(129,26,4,'是外键或主键的列不适合建索引',NULL),(130,27,1,'SQL中局部变量可以不声明就使用',NULL),(131,27,2,'SQL中全局变量必须先声明再使用',NULL),(132,27,3,'SQL中所有变量都必须先声明后使用',NULL),(133,27,4,'SQL中只有局部变量先声明后使用；全局变量是由系统提供的用户不能自己建立',NULL),(134,28,1,'是一张虚拟的表',NULL),(135,28,2,'在存储视图时存储的是视图的定义',NULL),(136,28,3,'在存储视图时存储的是视图中的数据',NULL),(137,28,4,'可以像查询表一样来查询视图',NULL),(138,29,1,'主要数据文件',NULL),(139,29,2,'次要数据文件',NULL),(140,29,3,'日志文件',NULL),(141,29,4,'系统文件',NULL),(142,30,1,'连通',NULL),(143,30,2,'连接 ',NULL),(144,30,3,'交换 ',NULL),(145,30,4,'共享',NULL),(146,31,1,'信道带宽',NULL),(147,31,2,'发送速率',NULL),(148,31,3,'信道中的信噪比',NULL),(149,31,4,'处理时延',NULL),(150,32,1,'FDM',NULL),(151,32,2,'WDM  ',NULL),(152,32,3,'CDMA ',NULL),(153,32,4,'XDFY',NULL),(154,33,1,'ADSL',NULL),(155,33,2,'HFC   ',NULL),(156,33,3,'FTTX',NULL),(157,33,4,'WWW',NULL),(158,34,1,'10.0.0.6',NULL),(159,34,2,'192.168.0.5',NULL),(160,34,3,'172.16.20.32',NULL),(161,34,4,'210.44.64.66',NULL);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paper`
--

DROP TABLE IF EXISTS `paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paper` (
                         `pid` int NOT NULL AUTO_INCREMENT,
                         `qid` int NOT NULL,
                         `useranswer` varchar(20) DEFAULT NULL,
                         PRIMARY KEY (`pid`,`qid`),
                         KEY `qid` (`qid`),
                         CONSTRAINT `paper_ibfk_1` FOREIGN KEY (`qid`) REFERENCES `questions` (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paper`
--

LOCK TABLES `paper` WRITE;
/*!40000 ALTER TABLE `paper` DISABLE KEYS */;
INSERT INTO `paper` VALUES (1,2,NULL),(1,4,NULL),(1,7,NULL),(1,9,NULL),(1,10,NULL),(1,11,NULL),(2,2,NULL),(2,3,NULL),(2,4,NULL),(2,7,NULL),(2,12,NULL),(2,15,NULL),(2,17,NULL),(2,19,NULL),(2,20,NULL),(2,23,NULL),(2,25,NULL),(2,29,NULL),(2,30,NULL),(2,32,NULL),(2,33,NULL),(3,1,NULL),(3,2,NULL),(3,7,NULL),(3,11,NULL),(3,12,NULL),(3,15,NULL),(3,16,NULL),(3,17,NULL),(3,18,NULL),(3,24,NULL),(3,26,NULL),(3,27,NULL),(3,28,NULL),(3,30,NULL),(3,34,NULL),(4,1,NULL),(4,2,NULL),(4,3,NULL),(4,4,NULL),(4,7,NULL),(4,9,NULL),(4,11,NULL),(4,12,NULL),(4,13,NULL),(4,14,NULL),(4,15,NULL),(4,16,NULL),(4,17,NULL),(4,18,NULL),(4,19,NULL),(4,20,NULL),(4,21,NULL),(4,22,NULL),(4,23,NULL),(4,24,NULL),(4,25,NULL),(4,26,NULL),(4,27,NULL),(4,28,NULL),(4,29,NULL),(4,30,NULL),(4,31,NULL),(4,32,NULL),(4,33,NULL),(4,34,NULL),(5,3,NULL),(5,7,NULL),(5,11,NULL),(5,12,NULL),(5,14,NULL),(5,15,NULL),(5,17,NULL),(5,18,NULL),(5,21,NULL),(5,24,NULL),(5,26,NULL),(5,27,NULL),(5,29,NULL),(5,31,NULL),(5,34,NULL);
/*!40000 ALTER TABLE `paper` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,3,1,'以下关于MAC的说法中错误的是.',NULL,0,'A'),(2,3,1,'一个PPP帧的数据部分（用十六进制写出）是7D 5E FE 27 7D 5D 7D 5D 65 7D 5E。\r\n试问真正的数据是什么（用十六进制写出）？',NULL,0,'D'),(3,1,1,'在下列关于规范化理论的叙述中，不正确的是_________。\r\n',NULL,0,'D'),(4,3,1,'计算机网络是计算机技术和（ ）技术的产物。',NULL,0,'A'),(5,5,1,'使用Java语言编写的源程序保存时的文件扩展名是（ ）。',NULL,0,'B'),(6,5,1,'设有数组的定义int[] a = new int[3]，则下面对数组元素的引用错误的是（ ）。',NULL,0,'D'),(7,3,1,'通信系统必须具备的三个基本要素是（）。',NULL,0,'C'),(9,3,1,'下列关于IP路由器功能的描述中，正确的是\r\nⅠ.运行路由协议，设备路由表\r\nⅡ.检测到拥塞时，合理丢弃IP分组\r\nⅢ.对收到的IP分组头进行差错校验，确保传输的IP分组不丢失\r\nⅣ.根据收到的IP分组的目的IP地址，将其转发至合适的输出线路上\r\n',NULL,0,'C'),(10,1,3,'这道题是对的吗？',NULL,0,'A'),(11,1,1,'TESTA',NULL,0,'A'),(12,1,1,'在数据管理技术的发展过程中，经历了人工管理阶段、文件系统阶段和数据库系统阶段。在这几个阶段中，数据独立性最高的是阶段。',NULL,0,'A'),(13,1,1,'存储在计算机外表存储介质上结构化的数据集合，其英文名称是',NULL,0,'C'),(14,1,1,'在数据库中，下列说法 是不正确的。',NULL,0,'A'),(15,1,3,'关系数据模型能较好地表示实体间的1:1 联系、1:n 联系以及m:n 联系。',NULL,0,'A'),(16,1,3,' SQL Server的字符型系统数据类型主要包括char、varchar、text和binary。',NULL,0,'B'),(17,1,3,'ODBC、OLE DB、API和ADO.NET都是访问数据库系统的常用接口。',NULL,0,'B'),(18,1,3,'事务可用于保持数据的一致性，应避免在事务中进行人工输入输出操作。',NULL,0,'A'),(19,1,3,'[my delete]、_mybase 、$money和trigger1都是合法的标志符。',NULL,0,'B'),(20,3,3,'数字传输系统一般不采用FDM方式进行多路复用。',NULL,0,'A'),(21,3,3,'ADSL技术的下行信息传输速率比上行信息传输速率低。',NULL,0,'B'),(22,3,3,'对讲机采用全双工的通信方式。',NULL,0,'B'),(23,3,3,'在数据传输中，多模光纤的性能要优于单模光纤。',NULL,0,'B'),(24,3,3,'如果要实现双向同时通信就必须要有两条数据传输线路。',NULL,0,'B'),(25,1,2,'下面描述正确的是________。',NULL,0,'ACD'),(26,1,2,'下面对索引的相关描述不正确的是________。',NULL,0,'ACD'),(27,1,2,'下列说法中错误的是________。',NULL,0,'ABC'),(28,1,2,'对视图的描述正确的是________。',NULL,0,'ABD'),(29,1,2,'新建的数据库至少包含（）。',NULL,0,'AC'),(30,3,2,'计算机网络的主要功能包括：',NULL,0,'AD'),(31,3,2,'依据香农公式，信道的极限信息传输速率与（ ）有关',NULL,0,'AC'),(32,3,2,'以下属于信道复用技术的是（ ）',NULL,0,'ABC'),(33,3,2,'下列技术中，哪几个是接入网的技术',NULL,0,'ABC'),(34,3,2,'以下哪是专用地址？',NULL,0,'ABC');
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
INSERT INTO `user_info` VALUES ('37220222203581','dujunhao','M','17382319551','2724948893@qq.com','/avatar/uid37220222203581.jpg'),('admin','admin','M',NULL,NULL,'/avatar/default.jpg'),('user1','user1','M',NULL,NULL,'/avatar/default.jpg'),('user10','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg'),('user11','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg'),('user2','user2','M','183369','37220222203581@stu.xmu.edu.cn','/avatar/default.jpg'),('user5','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg'),('user6','user6',NULL,NULL,NULL,'/avatar/default.jpg'),('user7','user7',NULL,NULL,NULL,'/avatar/default.jpg'),('user8','杜俊豪',NULL,NULL,NULL,'/avatar/default.jpg');
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

-- Dump completed on 2024-07-16 15:16:03
