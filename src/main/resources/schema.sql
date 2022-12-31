ALTER USER 'root'@'%' IDENTIFIED BY 'Winthegame';

create database IF NOT EXISTS `dinehouse`;
use `dinehouse`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `phoneNo` varchar(50),
  `status` varchar(20) NOT NULL,
  `email` varchar(100),
  `pwd` varchar(200) NOT NULL,
  `dob` varchar(20),
  `isAdmin` char(1) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `address` varchar(500),
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_unique` (`userId`),
  UNIQUE KEY `phone_no_unique` (`phoneNo`),
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `user_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL,
  `token` varchar(100) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `liftTime` int NOT NULL,
  `expireOn` TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint(20),
  `name` varchar(100) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL,
  `categoryId` int NOT NULL,
  `price` double NOT NULL,
  `userId` varchar(20) NOT NULL,
  `isVeg` char(1) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `orderMaster` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  `location` varchar(30) NOT NULL,
  `userId` varchar(30) NOT NULL,
  `price` double not null,
  `type` varchar(10) NOT NULL,
  `discount` double,
  `payable` double not null,
  `paymentType` varchar(30) NOT NULL,
  `address` varchar(300) NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `orderItems` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  `orderId` varchar(30) NOT NULL,
  `quantity` int NOT NULL,
  `itemId` int NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paymentType` varchar(30) NOT NULL,
  `type` varchar(10) NOT NULL,
  `orderId` bigint(20),
  `name` varchar(200) NOT NULL,
  `amount` double NOT NULL,
  `status` varchar(10) NOT NULL,
  `description` varchar(300),
  `userId` varchar(30) not null,
  `createdOn` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;