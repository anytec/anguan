/*
Navicat MySQL Data Transfer

Source Server         : db_mysql
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : anguan

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-06 15:28:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fd_cameras
-- ----------------------------
DROP TABLE IF EXISTS `fd_cameras`;
CREATE TABLE `fd_cameras` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '设备名称',
  `mac_address` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT 'mac地址',
  `push_ip` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '推送ip',
  `threshold` varchar(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '阈值',
  `bz1` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预留字段',
  `bz2` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预留字段',
  `bz3` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
