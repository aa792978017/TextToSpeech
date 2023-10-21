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

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_arp_attack_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_arp_attack_info`;
CREATE TABLE `tb_arp_attack_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `ip` varchar(16) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `attack_times` int(255) DEFAULT NULL,
  `monitor_id` int(255) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_arp_honey_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_arp_honey_info`;
CREATE TABLE `tb_arp_honey_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `src_ip` varchar(255) NOT NULL,
  `src_mac` varchar(255) NOT NULL,
  `dst_ip` varchar(255) NOT NULL,
  `dst_mac` varchar(255) NOT NULL,
  `arp_type` varchar(255) NOT NULL COMMENT 'request:1,reply:2',
  `flow_type` int(2) NOT NULL,
  `monitor_id` int(255) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58228 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_arp_ip
-- ----------------------------
DROP TABLE IF EXISTS `tb_arp_ip`;
CREATE TABLE `tb_arp_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `fake_mac` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_flow
-- ----------------------------
DROP TABLE IF EXISTS `tb_flow`;
CREATE TABLE `tb_flow` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `src_port` int(255) NOT NULL DEFAULT '-1' COMMENT '源端口号',
  `protocol` varchar(10) NOT NULL COMMENT '协议',
  `date` datetime NOT NULL COMMENT '时间',
  `src_ip` varchar(255) DEFAULT NULL,
  `dst_port` int(255) NOT NULL DEFAULT '-1' COMMENT '目的端口',
  `dst_ip` varchar(255) DEFAULT NULL COMMENT '目的ip',
  `src_mac` varchar(255) NOT NULL COMMENT '源mac',
  `dst_mac` varchar(255) NOT NULL DEFAULT '' COMMENT '目的mac',
  `length` int(255) NOT NULL COMMENT '数据报长度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168442 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_honey_run_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_honey_run_info`;
CREATE TABLE `tb_honey_run_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `mode` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `port` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_monitor_flow
-- ----------------------------
DROP TABLE IF EXISTS `tb_monitor_flow`;
CREATE TABLE `tb_monitor_flow` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `monitor_object_id` int(255) NOT NULL,
  `proxy_ip` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `flow_type` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `mac` varchar(255) NOT NULL,
  `response_action` varchar(255) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_monitor_object
-- ----------------------------
DROP TABLE IF EXISTS `tb_monitor_object`;
CREATE TABLE `tb_monitor_object` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `proxy_ip` varchar(255) NOT NULL,
  `proxy_mac` varchar(255) NOT NULL,
  `manage_date` datetime NOT NULL,
  `stop_manage_date` datetime DEFAULT NULL,
  `honey_pot_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_redis_attack_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_redis_attack_info`;
CREATE TABLE `tb_redis_attack_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `handler_msg` varchar(50) NOT NULL,
  `raw_msg` varchar(50) NOT NULL,
  `reply_msg` varchar(50) NOT NULL,
  `visitor_id` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11127 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_smtp_interact_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_smtp_interact_info`;
CREATE TABLE `tb_smtp_interact_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `honey_run_id` int(11) NOT NULL,
  `raw_msg` varchar(255) NOT NULL,
  `interact_time` datetime NOT NULL,
  `visitor_id` int(255) NOT NULL,
  `ans_msg` varchar(255) DEFAULT NULL,
  `mail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53087 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_smtp_mail_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_smtp_mail_info`;
CREATE TABLE `tb_smtp_mail_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail_from` varchar(255) NOT NULL,
  `mail_to` varchar(255) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `visitor_id` int(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `hello_msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22367 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_socket_interact_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_socket_interact_info`;
CREATE TABLE `tb_socket_interact_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `visitor_id` int(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `interact_time` datetime NOT NULL,
  `honey_run_id` int(255) NOT NULL,
  `server_port` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

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
