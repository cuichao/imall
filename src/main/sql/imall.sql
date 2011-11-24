/*
SQLyog Enterprise Trial - MySQL GUI v8.05 
MySQL - 5.0.37-community-nt-log : Database - imall
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`imall` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `imall`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(11) NOT NULL auto_increment,
  `accepter` varchar(30) collate utf8_bin default NULL,
  `address` varchar(255) collate utf8_bin default NULL,
  `createtime` datetime default NULL,
  `mailcode` varchar(10) collate utf8_bin default NULL,
  `phone` varchar(30) collate utf8_bin default NULL,
  `updatetime` datetime default NULL,
  `userid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `history_order` */

DROP TABLE IF EXISTS `history_order`;

CREATE TABLE `history_order` (
  `id` int(11) NOT NULL auto_increment,
  `accepter` varchar(30) collate utf8_bin default NULL,
  `createtime` datetime default NULL,
  `orderid` int(11) default NULL,
  `ordertime` datetime default NULL,
  `status` int(11) default NULL,
  `toAddress` varchar(255) collate utf8_bin default NULL,
  `totalpay` double default NULL,
  `updatetime` datetime default NULL,
  `userid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL auto_increment,
  `num` int(11) default NULL,
  `orderid` int(11) default NULL,
  `price` double default NULL,
  `productid` int(11) default NULL,
  `totalpay` double default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ordering` */

DROP TABLE IF EXISTS `ordering`;

CREATE TABLE `ordering` (
  `id` int(11) NOT NULL auto_increment,
  `accepter` varchar(30) collate utf8_bin default NULL,
  `createtime` datetime default NULL,
  `status` int(11) default NULL,
  `toAddress` varchar(255) collate utf8_bin default NULL,
  `totalpay` double default NULL,
  `updatetime` datetime default NULL,
  `userid` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL auto_increment,
  `description` longtext collate utf8_bin,
  `name` varchar(255) collate utf8_bin default NULL,
  `price` double default NULL,
  `videoPath` varchar(255) collate utf8_bin default NULL,
  `createtime` datetime default NULL,
  `updatetime` datetime default NULL,
  `shortDesc` mediumtext collate utf8_bin,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `product_comment` */

DROP TABLE IF EXISTS `product_comment`;

CREATE TABLE `product_comment` (
  `id` int(11) NOT NULL auto_increment,
  `comment` varchar(10000) collate utf8_bin default NULL,
  `productid` int(11) default NULL,
  `userid` int(11) default NULL,
  `createtime` datetime default NULL,
  `updatetime` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `product_detail` */

DROP TABLE IF EXISTS `product_detail`;

CREATE TABLE `product_detail` (
  `id` int(11) NOT NULL auto_increment,
  `color` varchar(20) collate utf8_bin default NULL,
  `picturePath` varchar(255) collate utf8_bin default NULL,
  `productid` int(11) default NULL,
  `note` varchar(255) collate utf8_bin default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `productid` (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_starred` */

DROP TABLE IF EXISTS `user_starred`;

CREATE TABLE `user_starred` (
  `id` int(11) NOT NULL auto_increment,
  `productid` int(11) default NULL,
  `userid` int(11) default NULL,
  `createtime` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL auto_increment,
  `city` varchar(255) collate utf8_bin default NULL,
  `email` varchar(255) collate utf8_bin default NULL,
  `nickname` varchar(255) collate utf8_bin default NULL,
  `password` varchar(255) collate utf8_bin default NULL,
  `phone` varchar(30) collate utf8_bin default NULL,
  `createtime` datetime default NULL,
  `updatetime` datetime default NULL,
  `active` bit(1) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
