/*
Navicat MySQL Data Transfer

Source Server         : grad
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : honeynet

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-05-05 10:17:09
*/
-- ----------------------------
-- Table structure for tb_visitor_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_visitor_record`;
CREATE TABLE `tb_visitor_record` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `honey_run_id` int(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `ip` varchar(255) NOT NULL,
  `port` int(255) NOT NULL,
  `host_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=597 DEFAULT CHARSET=utf8;
