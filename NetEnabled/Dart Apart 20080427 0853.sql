-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51a


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema dartapart
--

CREATE DATABASE IF NOT EXISTS dartapart;
USE dartapart;

--
-- Definition of table `ai`
--

DROP TABLE IF EXISTS `ai`;
CREATE TABLE `ai` (
  `ai_id` int(11) NOT NULL auto_increment,
  `player_id` int(11) NOT NULL,
  `target_zone_id` int(11) NOT NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  PRIMARY KEY  (`ai_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ai`
--

/*!40000 ALTER TABLE `ai` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai` ENABLE KEYS */;


--
-- Definition of table `game`
--

DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `game_id` int(11) NOT NULL auto_increment,
  `type_id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  PRIMARY KEY  (`game_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `game`
--

/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` (`game_id`,`type_id`,`name`,`create_date`,`modify_date`) VALUES 
 (1,1,'Nick\'s Game','2008-04-24 19:38:18','2008-04-24 19:38:18'),
 (2,1,'Nicks 2nd game','2008-04-24 20:29:28','2008-04-24 20:29:28'),
 (3,1,'Nicks 2nd game','2008-04-24 20:29:30','2008-04-24 20:29:30'),
 (4,1,'Nicks 2nd game','2008-04-24 20:29:31','2008-04-24 20:29:31'),
 (5,1,'Nicks 2nd game','2008-04-24 20:29:31','2008-04-24 20:29:31'),
 (6,1,'Nicks 2nd game','2008-04-24 20:29:31','2008-04-24 20:29:31'),
 (7,1,'Nicks 2nd game','2008-04-24 20:29:31','2008-04-24 20:29:31');
/*!40000 ALTER TABLE `game` ENABLE KEYS */;


--
-- Definition of table `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `player_id` int(11) NOT NULL auto_increment,
  `name` varchar(64) NOT NULL,
  `description` varchar(1024) default NULL,
  `email` varchar(64) NOT NULL,
  `geoloc` varchar(1024) default NULL,
  `icon` varchar(1024) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_login_date` datetime NOT NULL,
  PRIMARY KEY  (`player_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `player`
--

/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` (`player_id`,`name`,`description`,`email`,`geoloc`,`icon`,`create_date`,`last_login_date`) VALUES 
 (1,'Nick','Nick Cramer','nick.cramer@gmail.com','326 S Young Pl, Kennewick, WA','http://www.magic3000.com/aspbite_localinfo/template/template_images/magic_wand.gif','2008-04-25 18:53:15','2008-04-25 18:53:15'),
 (2,'Carolyn','Carolyn Nguyen','carolyn.nguyen77@gmail.com','326 S Young Pl, Kennewick, WA','','2008-04-25 19:12:22','2008-04-25 19:12:22'),
 (3,'Dlove','Doug Love','douglove@gmail.com','3720 Van Court, West Richland, WA','','2008-04-25 19:12:48','2008-04-25 19:12:48');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;


--
-- Definition of table `players`
--

DROP TABLE IF EXISTS `players`;
CREATE TABLE `players` (
  `game_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  PRIMARY KEY  (`game_id`,`player_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `players`
--

/*!40000 ALTER TABLE `players` DISABLE KEYS */;
/*!40000 ALTER TABLE `players` ENABLE KEYS */;


--
-- Definition of table `round`
--

DROP TABLE IF EXISTS `round`;
CREATE TABLE `round` (
  `round_id` int(11) NOT NULL auto_increment,
  `game_id` int(11) NOT NULL,
  PRIMARY KEY  (`round_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `round`
--

/*!40000 ALTER TABLE `round` DISABLE KEYS */;
INSERT INTO `round` (`round_id`,`game_id`) VALUES 
 (1,1);
/*!40000 ALTER TABLE `round` ENABLE KEYS */;


--
-- Definition of table `throw`
--

DROP TABLE IF EXISTS `throw`;
CREATE TABLE `throw` (
  `throw_id` int(11) NOT NULL auto_increment,
  `round_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `throw_date` datetime NOT NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  PRIMARY KEY  (`throw_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `throw`
--

/*!40000 ALTER TABLE `throw` DISABLE KEYS */;
INSERT INTO `throw` (`throw_id`,`round_id`,`player_id`,`throw_date`,`x`,`y`) VALUES 
 (1,1,1,'2008-04-25 20:41:41',0.75,0.15),
 (2,1,1,'2008-04-25 20:42:17',0.25,0.35),
 (3,1,1,'2008-04-25 20:45:08',0.445,0.23123565),
 (4,1,3,'2008-04-25 22:48:30',0.473,0.185),
 (5,1,3,'2008-04-25 22:48:30',0.249,0.182),
 (6,1,3,'2008-04-25 22:48:30',0.204,0.962),
 (7,1,2,'2008-04-25 22:55:33',0.685,0.234),
 (8,1,2,'2008-04-25 22:55:33',0.826,0.436),
 (9,1,2,'2008-04-25 22:55:33',0.528,0.146),
 (10,1,2,'2008-04-25 23:02:42',0.726,0.677),
 (11,1,2,'2008-04-25 23:02:42',0.418,0.322),
 (12,1,2,'2008-04-25 23:02:42',0.856,0.49);
/*!40000 ALTER TABLE `throw` ENABLE KEYS */;


--
-- Definition of table `zone`
--

DROP TABLE IF EXISTS `zone`;
CREATE TABLE `zone` (
  `zone_id` int(11) NOT NULL auto_increment,
  `wedge` int(11) NOT NULL,
  `ring` int(11) NOT NULL,
  PRIMARY KEY  (`zone_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `zone`
--

/*!40000 ALTER TABLE `zone` DISABLE KEYS */;
/*!40000 ALTER TABLE `zone` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
