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

 Date: 04/08/2021 16:53:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart_goods
-- ----------------------------
DROP TABLE IF EXISTS `cart_goods`;
CREATE TABLE `cart_goods`  (
  `cart_goods_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `product_id` int(10) UNSIGNED NOT NULL COMMENT '商品ID',
  `product_amount` int(11) NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`cart_goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车商品表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`customer_id`) USING BTREE,
  INDEX `login_name_password`(`login_name`, `password`) USING BTREE COMMENT '登录名与密码联合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户积分日志表' ROW_FORMAT = Dynamic;

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
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `order_detail_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单详情表ID',
  `order_id` int(10) UNSIGNED NOT NULL COMMENT '订单表ID',
  `product_id` int(10) UNSIGNED NOT NULL COMMENT '订单商品ID',
  `product_cnt` int(11) NOT NULL DEFAULT 1 COMMENT '购买商品数量',
  PRIMARY KEY (`order_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `order_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_sn` bigint(20) UNSIGNED NOT NULL COMMENT '订单编号 yyyymmddnnnnnnnn',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT '下单人ID',
  `shipping_user` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人姓名',
  `customer_addr_id` int(10) UNSIGNED NOT NULL COMMENT '收获地址id',
  `order_money` decimal(8, 2) NOT NULL COMMENT '订单金额',
  `district_money` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
  `payment_money` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT '支付金额',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'csmLogin:*', '操作用户');
INSERT INTO `permission` VALUES (2, 'csmLogin:view', '查看用户');
INSERT INTO `permission` VALUES (3, 'csmLogin:edit', '编辑用户');
INSERT INTO `permission` VALUES (4, 'csmLogin:add', '增加用户');
INSERT INTO `permission` VALUES (5, 'csmLogin:delete', '删除用户');

-- ----------------------------
-- Table structure for product_addition_info
-- ----------------------------
DROP TABLE IF EXISTS `product_addition_info`;
CREATE TABLE `product_addition_info`  (
  `product_addition_info_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `product_id` int(10) UNSIGNED NOT NULL COMMENT '商品ID',
  `product_type_id` int(10) NOT NULL COMMENT '商品型号id',
  `product_addtion_info_id` int(11) NOT NULL,
  PRIMARY KEY (`product_addition_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品附加信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_addition_info
-- ----------------------------
INSERT INTO `product_addition_info` VALUES (1, 1, 1, 0);
INSERT INTO `product_addition_info` VALUES (2, 1, 2, 0);
INSERT INTO `product_addition_info` VALUES (3, 2, 1, 0);
INSERT INTO `product_addition_info` VALUES (4, 2, 3, 0);
INSERT INTO `product_addition_info` VALUES (5, 3, 4, 0);
INSERT INTO `product_addition_info` VALUES (6, 3, 5, 0);
INSERT INTO `product_addition_info` VALUES (7, 4, 6, 0);
INSERT INTO `product_addition_info` VALUES (8, 4, 7, 0);
INSERT INTO `product_addition_info` VALUES (9, 5, 8, 0);
INSERT INTO `product_addition_info` VALUES (10, 5, 9, 0);
INSERT INTO `product_addition_info` VALUES (11, 6, 4, 0);
INSERT INTO `product_addition_info` VALUES (12, 6, 5, 0);
INSERT INTO `product_addition_info` VALUES (13, 7, 10, 0);
INSERT INTO `product_addition_info` VALUES (14, 7, 11, 0);
INSERT INTO `product_addition_info` VALUES (15, 8, 6, 0);
INSERT INTO `product_addition_info` VALUES (16, 8, 7, 0);
INSERT INTO `product_addition_info` VALUES (17, 9, 2, 0);
INSERT INTO `product_addition_info` VALUES (18, 9, 3, 0);
INSERT INTO `product_addition_info` VALUES (19, 10, 4, 0);
INSERT INTO `product_addition_info` VALUES (20, 10, 5, 0);
INSERT INTO `product_addition_info` VALUES (21, 11, 8, 0);
INSERT INTO `product_addition_info` VALUES (22, 11, 9, 0);
INSERT INTO `product_addition_info` VALUES (23, 12, 1, 0);
INSERT INTO `product_addition_info` VALUES (24, 12, 2, 0);
INSERT INTO `product_addition_info` VALUES (25, 13, 1, 0);
INSERT INTO `product_addition_info` VALUES (26, 13, 3, 0);
INSERT INTO `product_addition_info` VALUES (27, 14, 4, 0);
INSERT INTO `product_addition_info` VALUES (28, 14, 5, 0);
INSERT INTO `product_addition_info` VALUES (29, 15, 6, 0);
INSERT INTO `product_addition_info` VALUES (30, 15, 7, 0);
INSERT INTO `product_addition_info` VALUES (31, 16, 8, 0);
INSERT INTO `product_addition_info` VALUES (32, 16, 9, 0);
INSERT INTO `product_addition_info` VALUES (33, 17, 4, 0);
INSERT INTO `product_addition_info` VALUES (34, 17, 5, 0);
INSERT INTO `product_addition_info` VALUES (35, 18, 6, 0);
INSERT INTO `product_addition_info` VALUES (36, 18, 7, 0);
INSERT INTO `product_addition_info` VALUES (37, 19, 12, 0);
INSERT INTO `product_addition_info` VALUES (38, 19, 13, 0);
INSERT INTO `product_addition_info` VALUES (39, 20, 1, 0);
INSERT INTO `product_addition_info` VALUES (40, 20, 2, 0);

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `category_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (1, '手机');
INSERT INTO `product_category` VALUES (2, '电脑');
INSERT INTO `product_category` VALUES (3, '家具');
INSERT INTO `product_category` VALUES (4, '女装');
INSERT INTO `product_category` VALUES (5, '美妆个护');
INSERT INTO `product_category` VALUES (6, '箱包');
INSERT INTO `product_category` VALUES (7, '男鞋');
INSERT INTO `product_category` VALUES (8, '汽车');
INSERT INTO `product_category` VALUES (9, '母婴');
INSERT INTO `product_category` VALUES (10, '生鲜');
INSERT INTO `product_category` VALUES (11, '礼品鲜花');
INSERT INTO `product_category` VALUES (12, '医药保健');
INSERT INTO `product_category` VALUES (13, '图书');
INSERT INTO `product_category` VALUES (14, '家用电器');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `product_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `category_id` smallint(5) UNSIGNED NOT NULL COMMENT '分类ID',
  `supplier_id` int(10) UNSIGNED NOT NULL COMMENT '商品的供应商ID',
  `price` decimal(8, 2) NOT NULL COMMENT '商品销售价格',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品描述',
  `inventory` int(10) NOT NULL COMMENT '商品库存',
  `pic_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品图片',
  `rest_time` timestamp(0) NULL DEFAULT NULL COMMENT '秒杀字段',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '麦芒5全网通', 1, 1, 7299.00, '国产手机', 20, 'http://img.alicdn.com/bao/uploaded/i1/1659916565/TB2EWe8aA1M.eBjSZFOXXc0rFXa_!!1659916565.jpg', NULL);
INSERT INTO `product_info` VALUES (2, '华为honor/荣耀 V8 大屏4G智能拍照手机指纹解锁官方正品', 1, 1, 3399.00, '国产手机', 30, 'http://img.alicdn.com/bao/uploaded/i2/TB1QGBIKpXXXXXcaXXXHctQ8pXX_022619.jpg', NULL);
INSERT INTO `product_info` VALUES (3, 'Apple/苹果 iPhone 7 Plus 128G 全网通4G智能手机', 1, 2, 6999.00, '非国产手机', 15, 'http://img.alicdn.com/bao/uploaded/i2/TB1bL9PNXXXXXcmaXXXeDqP9XXX_034934.jpg', NULL);
INSERT INTO `product_info` VALUES (4, '预售Xiaomi/小米 红米手机4A 超长待机双卡双系统超薄智能机', 1, 3, 2999.00, '国产手机', 20, 'http://img.alicdn.com/bao/uploaded/i5/TB1KaeAOXXXXXcUapXXLpWA8VXX_032158.jpg', NULL);
INSERT INTO `product_info` VALUES (5, 'Samsung/三星 Galaxy C9 Pro SM-C9000 6+64G全金属超薄手机', 1, 6, 3199.00, '非国产手机', 40, 'https://img.alicdn.com/imgextra/i4/370627083/TB2JmhxXc2DjeFjSspnXXb20XXa-370627083.jpg', NULL);
INSERT INTO `product_info` VALUES (6, 'Huawei/华为 G9 青春版【双12领券立减100+178元礼包】智能手机', 1, 1, 1999.00, '国产手机', 25, 'http://img.alicdn.com/bao/uploaded/i2/2838892713/TB2u1yptVXXXXbZXXXXXXXXXXXX_!!2838892713.jpg', NULL);
INSERT INTO `product_info` VALUES (7, '华为honor/荣耀 荣耀7i全网通智能手机 官方正品', 1, 1, 2299.00, '国产手机', 30, 'http://img.alicdn.com/bao/uploaded/i6/TB1OCF0KpXXXXacXXXXFoYl.pXX_073637.jpg', NULL);
INSERT INTO `product_info` VALUES (8, '华为honor/荣耀 畅玩5x 4G智能手机官方大屏正品', 1, 1, 2599.00, '国产手机', 20, 'http://img.alicdn.com/bao/uploaded/i4/TB1hD7NHVXXXXavXVXXZKVp9XXX_034218.jpg', NULL);
INSERT INTO `product_info` VALUES (9, '【旗舰新品】OPPO R9s Plus全网通指纹识别6G大运存拍照4G手机r9s', 1, 4, 3599.00, '国产手机', 25, 'http://img.alicdn.com/bao/uploaded/i8/TB16HgDOpXXXXcCXpXXvx6S.pXX_104208.jpg', NULL);
INSERT INTO `product_info` VALUES (10, 'vivo X9前置双摄全网通4G美颜自拍超薄智能手机大屏vivox9', 1, 5, 2999.00, '国产手机', 35, 'http://img.alicdn.com/bao/uploaded/i1/TB1ltVvOpXXXXcpXXXXBY2o.VXX_082533.jpg', NULL);
INSERT INTO `product_info` VALUES (11, '华为honor/荣耀 畅玩5A 4G智能手机 大屏官方正品', 1, 1, 1699.00, '国产手机', 20, 'http://img.alicdn.com/bao/uploaded/i4/TB1p4PjKpXXXXaGXXXX3WfO8FXX_030147.jpg', NULL);
INSERT INTO `product_info` VALUES (12, '【秒杀6666】麦芒5全网通', 1, 1, 6666.00, '国产手机', 30, 'http://img.alicdn.com/bao/uploaded/i1/1659916565/TB2EWe8aA1M.eBjSZFOXXc0rFXa_!!1659916565.jpg', '2021-08-10 10:57:13');
INSERT INTO `product_info` VALUES (13, '【秒杀3000】华为honor/荣耀 V8 大屏4G智能拍照手机指纹解锁官方正品', 1, 1, 3000.00, '国产手机', 20, 'http://img.alicdn.com/bao/uploaded/i2/TB1QGBIKpXXXXXcaXXXHctQ8pXX_022619.jpg', '2021-08-10 16:58:33');
INSERT INTO `product_info` VALUES (14, '【秒杀6500】Apple/苹果 iPhone 7 Plus 128G 全网通4G智能手机', 1, 2, 6500.00, '非国产手机', 40, 'http://img.alicdn.com/bao/uploaded/i2/TB1bL9PNXXXXXcmaXXXeDqP9XXX_034934.jpg', '2021-08-11 10:59:35');
INSERT INTO `product_info` VALUES (15, '【秒杀2500】预售Xiaomi/小米 红米手机4A 超长待机双卡双系统超薄智能机', 1, 3, 2500.00, '国产手机', 50, 'http://img.alicdn.com/bao/uploaded/i5/TB1KaeAOXXXXXcUapXXLpWA8VXX_032158.jpg', '2021-08-11 12:01:03');
INSERT INTO `product_info` VALUES (16, '【秒杀3000】Samsung/三星 Galaxy C9 Pro SM-C9000 6+64G全金属超薄手机', 1, 6, 3000.00, '国产手机', 30, 'https://img.alicdn.com/imgextra/i4/370627083/TB2JmhxXc2DjeFjSspnXXb20XXa-370627083.jpg', '2021-08-10 18:02:05');
INSERT INTO `product_info` VALUES (17, '【秒杀1800】Huawei/华为 G9 青春版【双12领券立减100+178元礼包】智能手机', 1, 1, 1800.00, '国产手机', 60, 'http://img.alicdn.com/bao/uploaded/i2/2838892713/TB2u1yptVXXXXbZXXXXXXXXXXXX_!!2838892713.jpg', '2021-08-11 17:03:09');
INSERT INTO `product_info` VALUES (18, '湖北电信合约机 OPPO R9全网通正面指纹识别新款智能拍照4G手机', 1, 4, 1999.00, '国产手机', 40, 'http://img.alicdn.com/bao/uploaded/i6/TB1KOzwKpXXXXaYXVXXCbB.8FXX_025720.jpg', NULL);
INSERT INTO `product_info` VALUES (19, '正品Xiaomi/小米 红米手机3X 4g双卡双待智能手机 小米官方旗舰店', 1, 3, 2999.00, '国产手机', 20, 'https://img14.360buyimg.com/n1/s546x546_jfs/t1/190256/21/11986/351134/60e432bfE63e14615/3d0d9b82937e5751.jpg', NULL);
INSERT INTO `product_info` VALUES (20, 'OPPO Find X3 5000万双主摄IMX766 10亿色臻彩屏 60倍显微镜 骁龙870 8+128镜黑 5G年度旗舰手机 ', 1, 4, 3999.00, '国产手机', 50, 'https://img14.360buyimg.com/n0/jfs/t1/159876/20/15398/91091/605d8789Eb22ccd0d/3bfd6f8a27ca24d8.jpg', NULL);

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type`  (
  `product_type_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '型号id',
  `product_type_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品型号名',
  PRIMARY KEY (`product_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品型号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_type
-- ----------------------------
INSERT INTO `product_type` VALUES (1, '幻夜黑+128G');
INSERT INTO `product_type` VALUES (2, '樱雪晴空+128G');
INSERT INTO `product_type` VALUES (3, '金色+256G');
INSERT INTO `product_type` VALUES (4, '官方标配');
INSERT INTO `product_type` VALUES (5, '优惠套装');
INSERT INTO `product_type` VALUES (6, '8G+128G 墨羽');
INSERT INTO `product_type` VALUES (7, '8G+128G 幻境');
INSERT INTO `product_type` VALUES (8, '快充套装');
INSERT INTO `product_type` VALUES (9, '换修套装');
INSERT INTO `product_type` VALUES (10, '5G有充版');
INSERT INTO `product_type` VALUES (11, '4G无充版');
INSERT INTO `product_type` VALUES (12, '全网通');
INSERT INTO `product_type` VALUES (13, '合约机');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 2, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);

-- ----------------------------
-- Table structure for supplier_info
-- ----------------------------
DROP TABLE IF EXISTS `supplier_info`;
CREATE TABLE `supplier_info`  (
  `supplier_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商名称',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商地址',
  PRIMARY KEY (`supplier_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '供应商信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier_info
-- ----------------------------
INSERT INTO `supplier_info` VALUES (1, '华为', '深圳');
INSERT INTO `supplier_info` VALUES (2, '苹果', '加利福尼亚');
INSERT INTO `supplier_info` VALUES (3, '小米', '北京');
INSERT INTO `supplier_info` VALUES (4, 'OPPO', '东莞');
INSERT INTO `supplier_info` VALUES (5, 'VIVO', '深圳');
INSERT INTO `supplier_info` VALUES (6, '三星', '首尔');

-- ----------------------------
-- View structure for product_detail_view
-- ----------------------------
DROP VIEW IF EXISTS `product_detail_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `bobo`@`localhost` SQL SECURITY DEFINER VIEW `product_detail_view` AS select `product_info`.`product_id` AS `product_id`,`product_info`.`product_name` AS `product_name`,`product_info`.`description` AS `description`,`product_info`.`pic_url` AS `pic_url`,`product_info`.`price` AS `price`,`product_info`.`inventory` AS `inventory`,`supplier_info`.`supplier_name` AS `supplier_name`,`product_type`.`product_type_name` AS `product_type_name` from (((`product_info` join `supplier_info`) join `product_type`) join `product_addition_info`) where ((`product_info`.`product_id` = `product_addition_info`.`product_id`) and (`product_info`.`supplier_id` = `supplier_info`.`supplier_id`) and (`product_type`.`product_type_id` = `product_addition_info`.`product_type_id`));

-- ----------------------------
-- View structure for product_view
-- ----------------------------
DROP VIEW IF EXISTS `product_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `bobo`@`localhost` SQL SECURITY DEFINER VIEW `product_view` AS select `product_info`.`category_id` AS `category_id`,`product_info`.`product_id` AS `product_id`,`product_info`.`product_name` AS `product_name`,`product_info`.`price` AS `price`,`product_info`.`pic_url` AS `pic_url`,`supplier_info`.`supplier_name` AS `supplier_name` from (`product_info` join `supplier_info`) where (`product_info`.`supplier_id` = `supplier_info`.`supplier_id`);

SET FOREIGN_KEY_CHECKS = 1;
