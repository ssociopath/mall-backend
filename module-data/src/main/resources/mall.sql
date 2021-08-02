/*
 Navicat Premium Data Transfer

 Source Server         : LK_Connection
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 02/08/2021 10:02:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer_addr
-- ----------------------------
DROP TABLE IF EXISTS `customer_addr`;
CREATE TABLE `customer_addr`  (
  `customer_addr_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT 'customer_login表的自增ID',
  `zipcode` int(6) NOT NULL COMMENT '邮政编码',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '具体的地址门牌号',
  `is_default` tinyint(4) NOT NULL COMMENT '是否为默认地址',
  PRIMARY KEY (`customer_addr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer_addr
-- ----------------------------
INSERT INTO `customer_addr` VALUES (1, 1, 130426, '辽城乡刘家庄村001', 1);
INSERT INTO `customer_addr` VALUES (2, 2, 130426, '辽城乡刘家庄村002', 1);
INSERT INTO `customer_addr` VALUES (3, 3, 130426, '辽城乡刘家庄村003', 0);
INSERT INTO `customer_addr` VALUES (4, 1, 430063, '海虹四栋405', 0);
INSERT INTO `customer_addr` VALUES (5, 2, 430063, '海虹四栋708', 0);
INSERT INTO `customer_addr` VALUES (6, 3, 430063, '海虹四栋612', 1);

-- ----------------------------
-- Table structure for customer_inf
-- ----------------------------
DROP TABLE IF EXISTS `customer_inf`;
CREATE TABLE `customer_inf`  (
  `customer_inf_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT 'customer_login表的自增ID',
  `customer_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户真实姓名',
  `identity_card_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '证件类型：1 身份证，2 军官\r\n证，3 护照',
  `identity_card_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `mobile_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `user_point` int(11) NOT NULL DEFAULT 0 COMMENT '用户积分',
  PRIMARY KEY (`customer_inf_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer_inf
-- ----------------------------
INSERT INTO `customer_inf` VALUES (1, 1, '李航飞', 1, '41234319870123001X', '13223412424', '男', 100);
INSERT INTO `customer_inf` VALUES (2, 2, '曾昊宇', 2, '421341200012010021', '12414523123', '男', 50);
INSERT INTO `customer_inf` VALUES (3, 3, '李彦佳', 3, '412421199802240134', '12351235643', '女', 0);

-- ----------------------------
-- Table structure for customer_login
-- ----------------------------
DROP TABLE IF EXISTS `customer_login`;
CREATE TABLE `customer_login`  (
  `customer_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录名',
  `password` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'md5加密的密码',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_stats` tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户状态',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer_login
-- ----------------------------
INSERT INTO `customer_login` VALUES (1, 'zack', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, 1);
INSERT INTO `customer_login` VALUES (2, 'Jack', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, 1);
INSERT INTO `customer_login` VALUES (3, 'Alice', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1, 1);
INSERT INTO `customer_login` VALUES (4, 'Tom', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2, 1);

-- ----------------------------
-- Table structure for customer_point_log
-- ----------------------------
DROP TABLE IF EXISTS `customer_point_log`;
CREATE TABLE `customer_point_log`  (
  `point_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '积分日志ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `source_id` tinyint(3) UNSIGNED NOT NULL COMMENT '积分来源编号：0订单，1签到，2活动',
  `change_point` smallint(6) NOT NULL DEFAULT 0 COMMENT '变更积分数',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '积分日志生成时间',
  PRIMARY KEY (`point_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户积分日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer_point_log
-- ----------------------------
INSERT INTO `customer_point_log` VALUES (1, 1, 0, 10, '2021-08-01 20:42:28');
INSERT INTO `customer_point_log` VALUES (2, 2, 1, -10, '2021-08-01 20:42:59');
INSERT INTO `customer_point_log` VALUES (3, 3, 2, 20, '2021-08-01 20:43:17');
INSERT INTO `customer_point_log` VALUES (4, 1, 2, -50, '2021-08-01 20:52:09');
INSERT INTO `customer_point_log` VALUES (5, 3, 0, 20, '2021-08-01 20:52:30');
INSERT INTO `customer_point_log` VALUES (6, 2, 2, 80, '2021-08-01 20:52:49');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'csmLogin:*', '操作用户');
INSERT INTO `permission` VALUES (2, 'csmLogin:view', '查看用户');
INSERT INTO `permission` VALUES (3, 'csmLogin:edit', '编辑用户');
INSERT INTO `permission` VALUES (4, 'csmLogin:add', '增加用户');
INSERT INTO `permission` VALUES (5, 'csmLogin:delete', '删除用户');

-- ----------------------------
-- Table structure for punch_in
-- ----------------------------
DROP TABLE IF EXISTS `punch_in`;
CREATE TABLE `punch_in`  (
  `punch_in_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT ' 自增主键',
  `customer_id` int(11) NOT NULL COMMENT '用户ID',
  `year` smallint(4) NOT NULL COMMENT '年份',
  `month` tinyint(2) NOT NULL COMMENT '月份',
  `daily_bitmap` int(255) NOT NULL COMMENT '签到图',
  PRIMARY KEY (`punch_in_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'user');
INSERT INTO `role` VALUES (2, 'admin');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`role_permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 2, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);

SET FOREIGN_KEY_CHECKS = 1;
