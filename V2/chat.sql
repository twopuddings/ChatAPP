/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50561
 Source Host           : localhost:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 50561
 File Encoding         : 65001

 Date: 06/01/2019 23:47:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `User_Id` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `User_Password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `User_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`User_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'tim', '123456', 'true');
INSERT INTO `user` VALUES (2, 'tom', '123456', 'true');
INSERT INTO `user` VALUES (3, 'sam', '123456', 'true');

SET FOREIGN_KEY_CHECKS = 1;
