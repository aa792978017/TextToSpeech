/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Schema         : text_to_speech

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 26/10/2023 09:46:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_job_history
-- ----------------------------
DROP TABLE IF EXISTS `tb_job_history`;
CREATE TABLE `tb_job_history`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `ip` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `textarea` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
                                   `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `time` datetime NULL DEFAULT NULL,
                                   `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `voice_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `style` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `rate` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `pitch` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   `voice_quality` int(4) NULL DEFAULT NULL,
                                   `job_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_service_activity_data
-- ----------------------------
DROP TABLE IF EXISTS `tb_service_activity_data`;
CREATE TABLE `tb_service_activity_data`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `date` datetime NOT NULL,
                                             ` web_visit_count` bigint(255) NULL DEFAULT 0,
                                             `audition_count` bigint(255) NULL DEFAULT 0,
                                             `download_count` bigint(255) NULL DEFAULT 0,
                                             `word_to_speech_count` bigint(255) NULL DEFAULT 0,
                                             `acvtive_user` bigint(255) NULL DEFAULT 0,
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
