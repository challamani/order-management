ALTER USER 'root'@'%' IDENTIFIED BY 'Winthegame';

create database IF NOT EXISTS `dinehouse`;
use dinehouse;

--CREATE USER 'systemuser'@'%' IDENTIFIED BY '<password>';
--CREATE USER 'systemuser'@'localhost' identified by '<password>';
--GRANT ALL PRIVILEGES ON dinehouse. * TO 'systemuser'@'localhost';
--GRANT ALL PRIVILEGES ON dinehouse. * TO 'systemuser'@'%';


DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `agentId` varchar(50) NOT NULL,
  `phoneNo` varchar(50),
  `status` varchar(20) NOT NULL,
  `email` varchar(100),
  `dob` varchar(20) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `address` varchar(500),
  PRIMARY KEY (`id`),
  UNIQUE KEY `agent_id_unique` (`agentId`),
  UNIQUE KEY `phone_no_unique` (`phoneNo`),
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `agent_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `agentId` varchar(100) NOT NULL,
  `token` varchar(100) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `liftTime` bigint(20) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;