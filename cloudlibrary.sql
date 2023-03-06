/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : cl

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 19/10/2021 10:14:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `press` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书出版社',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书作者',
  `pagination` int(0) NULL DEFAULT NULL COMMENT '图书页数',
  `price` decimal(8, 2) NULL DEFAULT NULL COMMENT '图书价格',
  `uploadtime` date NULL DEFAULT NULL COMMENT '图书上架时间',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '图书状态（0：可借阅，1:已借阅，2：归还中，3：已下架）',
  `borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
  `borrowtime` date NULL DEFAULT NULL COMMENT '图书借阅时间',
  `returntime` date NULL DEFAULT NULL COMMENT '图书预计归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Java基础案例教程（第2版）', '人民邮电出版社', '传智播客', 291, 59.00, '2020-12-11', '0', '', NULL, NULL);
INSERT INTO `book` VALUES (2, '挪威的森林', '上海译文出版社', '村上春树', 380, 34.00, '2020-12-12', '0', '', NULL, NULL);
INSERT INTO `book` VALUES (4, 'JavaWeb程序设计任务教程', '人民邮电出版社', '传智播客', 419, 56.00, '2020-12-14', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (5, 'Java基础入门（第2版）', '清华大学出版社', '传智播客', 413, 59.00, '2020-12-15', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (6, 'SpringCloud微服务架构开发', '人民邮电出版社', '传智播客', 196, 43.00, '2020-12-16', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (7, 'SpringBoot企业级开发教程', '人民邮电出版社', '传智播客', 270, 56.00, '2020-12-17', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (8, 'Spark大数据分析与实战', '清华大学出版社', '传智播客', 228, 49.00, '2020-12-18', '0', NULL, NULL, NULL);
INSERT INTO `book` VALUES (10, '边城', '武汉出版社', '沈从文', 368, 26.00, '2020-12-20', '1', '黑马程序员', '2021-08-06', '2021-08-13');
INSERT INTO `book` VALUES (12, '自在独行', '长江文艺出版社', '贾平凹', 320, 39.00, '2020-12-22', '0', '', NULL, NULL);
INSERT INTO `book` VALUES (13, '沉默的巡游', '南海出版公司', '东野圭吾', 400, 59.00, '2020-12-23', '0', '\r\n', NULL, NULL);
INSERT INTO `book` VALUES (17, '围城', '人民文学出版社', '钱钟书', 382, 24.80, '2021-08-19', '3', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for book_copy1
-- ----------------------------
DROP TABLE IF EXISTS `book_copy1`;
CREATE TABLE `book_copy1`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `press` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书出版社',
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书作者',
  `pagination` int(0) NULL DEFAULT NULL COMMENT '图书页数',
  `price` double(10, 0) NULL DEFAULT NULL COMMENT '图书价格',
  `uploadtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书上架时间',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '图书状态（0：可借阅，1:已借阅，2：归还中，3：已下架）',
  `borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
  `borrowtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅时间',
  `returntime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书预计归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_copy1
-- ----------------------------
INSERT INTO `book_copy1` VALUES (1, 'Java基础案例教程（第2版）', '人民邮电出版社', '传智播客', 291, 59, '2020-12-11', '0', '', '', '');
INSERT INTO `book_copy1` VALUES (2, '挪威的森林', '上海译文出版社', '村上春树', 380, 34, '2020-12-12', '1', '张三', '2021-08-06', '2021-08-12');
INSERT INTO `book_copy1` VALUES (4, 'JavaWeb程序设计任务教程', '人民邮电出版社', '传智播客', 419, 56, '2020-12-14', '0', NULL, NULL, NULL);
INSERT INTO `book_copy1` VALUES (5, 'Java基础入门（第2版）', '清华大学出版社', '传智播客', 413, 59, '2020-12-15', '0', NULL, NULL, NULL);
INSERT INTO `book_copy1` VALUES (6, 'SpringCloud微服务架构开发', '人民邮电出版社', '传智播客', 196, 43, '2020-12-16', '0', NULL, NULL, NULL);
INSERT INTO `book_copy1` VALUES (7, 'SpringBoot企业级开发教程', '人民邮电出版社', '传智播客', 270, 56, '2020-12-17', '0', NULL, NULL, NULL);
INSERT INTO `book_copy1` VALUES (8, 'Spark大数据分析与实战', '清华大学出版社', '传智播客', 228, 49, '2020-12-18', '0', NULL, NULL, NULL);
INSERT INTO `book_copy1` VALUES (10, '边城', '武汉出版社', '沈从文', 368, 26, '2020-12-20', '1', '黑马程序员', '2021-08-06', '2021-08-13');
INSERT INTO `book_copy1` VALUES (12, '自在独行', '长江文艺出版社', '贾平凹', 320, 39, '2020-12-22', '0', NULL, NULL, NULL);
INSERT INTO `book_copy1` VALUES (13, '沉默的巡游', '南海出版公司', '东野圭吾', 400, 59, '2020-12-23', '0', '', '', '');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '借阅记录id',
  `bookname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅的图书名称',
  `borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
  `borrowtime` date NULL DEFAULT NULL COMMENT '图书借阅时间',
  `remandtime` date NULL DEFAULT NULL COMMENT '图书归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (28, 'Java基础案例教程（第2版）', '张三', '2021-08-20', '2021-08-20');
INSERT INTO `record` VALUES (29, '自在独行', '黑马程序员', '2021-08-19', '2021-10-19');
INSERT INTO `record` VALUES (30, '挪威的森林', '张三', '2021-08-20', '2021-10-19');

-- ----------------------------
-- Table structure for record_copy1_copy1
-- ----------------------------
DROP TABLE IF EXISTS `record_copy1_copy1`;
CREATE TABLE `record_copy1_copy1`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '借阅记录id',
  `bookname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅的图书名称',
  `bookisbn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '借阅的图书的ISBN编号',
  `borrower` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅人',
  `borrowtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书借阅时间',
  `remandtime` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书归还时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record_copy1_copy1
-- ----------------------------
INSERT INTO `record_copy1_copy1` VALUES (23, 'Java基础案例教程（第2版）', '9787115547477', '张三', '2020-12-21', '2020-12-21');
INSERT INTO `record_copy1_copy1` VALUES (24, '沉默的巡游', '9787544280662', '张三', '2020-12-11', '2021-01-06');
INSERT INTO `record_copy1_copy1` VALUES (25, '沉默的巡游', '9787544280662', '张三', '2012-12-11', '2021-08-06');
INSERT INTO `record_copy1_copy1` VALUES (26, 'Java基础案例教程（第2版）', '9787115547477', '张三', '2021-08-06', '2021-08-06');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱（用户账号）',
  `role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户角色',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户状态（0:正常,1:禁用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '黑马程序员', '', 'itheima@itcast.cn', 'ADMIN', '0');
INSERT INTO `user` VALUES (2, '张三', '', 'zhangsan@itcast.cn', 'USER', '0');

SET FOREIGN_KEY_CHECKS = 1;
