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
  `type_id` int(11) NOT NULL default '1',
  `status` int(11) NOT NULL default '1',
  `name` varchar(64) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `shared_data` varchar(8192) NOT NULL,
  PRIMARY KEY  (`game_id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `game`
--

/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` (`game_id`,`type_id`,`status`,`name`,`create_date`,`modify_date`,`shared_data`) VALUES 
 (23,0,1,'Test','2008-05-15 07:48:50','2008-05-15 07:48:50','-37.8460417079354,-63.024585870816054');
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
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `player`
--

/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` (`player_id`,`name`,`description`,`email`,`geoloc`,`icon`,`create_date`,`last_login_date`) VALUES 
 (1,'Nick','Nick Cramer','nick.cramer@gmail.com','326 S Young Pl, Kennewick, WA','http://www.magic3000.com/aspbite_localinfo/template/template_images/magic_wand.gif','2008-04-25 18:53:15','2008-04-25 18:53:15'),
 (2,'Carolyn','Carolyn Nguyen','carolyn.nguyen77@gmail.com','326 S Young Pl, Kennewick, WA','','2008-04-25 19:12:22','2008-04-25 19:12:22'),
 (3,'Dlove','Doug Love','douglove@gmail.com','3720 Van Court, West Richland, WA','','2008-04-25 19:12:48','2008-04-25 19:12:48'),
 (4,'Ian',NULL,'ian.roberts@pnl.gov',NULL,'','0000-00-00 00:00:00','0000-00-00 00:00:00'),
 (5,'Joe',NULL,'joe.bruce@pnl.gov',NULL,'','0000-00-00 00:00:00','0000-00-00 00:00:00'),
 (6,'Bob',NULL,'bob.baddeley@pnl.gov',NULL,'','0000-00-00 00:00:00','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;


--
-- Definition of table `players`
--

DROP TABLE IF EXISTS `players`;
CREATE TABLE `players` (
  `game_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `join_order` int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  USING BTREE (`join_order`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `players`
--

/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` (`game_id`,`player_id`,`join_order`) VALUES 
 (23,1,30),
 (23,3,31);
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
/*!40000 ALTER TABLE `round` ENABLE KEYS */;


--
-- Definition of table `throw`
--

DROP TABLE IF EXISTS `throw`;
CREATE TABLE `throw` (
  `throw_id` int(11) NOT NULL auto_increment,
  `game_id` int(11) NOT NULL default '0',
  `player_id` int(11) NOT NULL default '0',
  `throw_date` datetime NOT NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  PRIMARY KEY  (`throw_id`)
) ENGINE=MyISAM AUTO_INCREMENT=257 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `throw`
--

/*!40000 ALTER TABLE `throw` DISABLE KEYS */;
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
