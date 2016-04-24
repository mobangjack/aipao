/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : aipao

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2016-04-24 00:03:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pass` varchar(32) NOT NULL,
  `token` varchar(32) DEFAULT NULL,
  `login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'bj', '1314233', null, null);

-- ----------------------------
-- Table structure for `run`
-- ----------------------------
DROP TABLE IF EXISTS `run`;
CREATE TABLE `run` (
  `imei` varchar(32) NOT NULL,
  `token` varchar(32) DEFAULT NULL,
  `userId` mediumint(5) unsigned DEFAULT NULL,
  `runId` varchar(32) DEFAULT NULL,
  `fieldId` mediumint(5) unsigned DEFAULT '99',
  `lastLat` float(9,6) DEFAULT '30.545273',
  `lastLng` float(9,6) DEFAULT '114.365715',
  `lat` float(9,6) DEFAULT '30.544746',
  `lng` float(9,6) DEFAULT '114.365715',
  `scores` mediumint(5) unsigned DEFAULT '5000',
  `coins` mediumint(5) unsigned DEFAULT '2000',
  `times` mediumint(5) unsigned DEFAULT '540',
  `length` mediumint(5) unsigned DEFAULT '2000',
  `result` varchar(2000) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  PRIMARY KEY (`imei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of run
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pass` varchar(32) NOT NULL,
  `imei` varchar(32) DEFAULT NULL,
  `token` varchar(32) DEFAULT NULL,
  `login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'bj', '1314233', null, null, null);
