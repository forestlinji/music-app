/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : music

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 03/09/2020 19:17:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `user_id` int(11) NOT NULL COMMENT '收藏者id',
  `music_id` int(11) NOT NULL COMMENT '歌曲id',
  PRIMARY KEY (`user_id`, `music_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (1, 1);
INSERT INTO `collect` VALUES (1, 2);
INSERT INTO `collect` VALUES (1, 5);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `song_id` int(11) NULL DEFAULT NULL COMMENT '音乐id',
  `reviewer_id` int(11) NULL DEFAULT NULL COMMENT '评论人id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (2, 1, 2, 'bbbbb', '2020-07-30 19:22:15');
INSERT INTO `comment` VALUES (3, 2, 1, 'test comment', '2020-07-30 19:51:55');
INSERT INTO `comment` VALUES (4, 1, 1, '测试评论', '2020-08-29 11:46:07');
INSERT INTO `comment` VALUES (5, 1, 1, '这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论这是一条评论', '2020-08-29 13:33:52');
INSERT INTO `comment` VALUES (6, 5, 1, '发表', '2020-08-29 13:35:12');
INSERT INTO `comment` VALUES (7, 5, 1, '啦啦啦', '2020-08-29 13:35:39');
INSERT INTO `comment` VALUES (8, 1, 1, '。。。', '2020-08-29 13:48:41');
INSERT INTO `comment` VALUES (9, 2, 1, 'dasdasd', '2020-08-29 15:22:07');
INSERT INTO `comment` VALUES (10, 4, 1, '12345', '2020-09-01 15:39:32');

-- ----------------------------
-- Table structure for music_musiclist
-- ----------------------------
DROP TABLE IF EXISTS `music_musiclist`;
CREATE TABLE `music_musiclist`  (
  `musiclist_id` int(11) NOT NULL,
  `music_id` int(11) NOT NULL,
  PRIMARY KEY (`musiclist_id`, `music_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of music_musiclist
-- ----------------------------
INSERT INTO `music_musiclist` VALUES (4, 1);
INSERT INTO `music_musiclist` VALUES (4, 2);
INSERT INTO `music_musiclist` VALUES (6, 4);
INSERT INTO `music_musiclist` VALUES (7, 2);
INSERT INTO `music_musiclist` VALUES (7, 3);
INSERT INTO `music_musiclist` VALUES (8, 1);
INSERT INTO `music_musiclist` VALUES (8, 3);

-- ----------------------------
-- Table structure for musiclist
-- ----------------------------
DROP TABLE IF EXISTS `musiclist`;
CREATE TABLE `musiclist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '歌单id',
  `owner_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '歌单名字',
  `num` int(11) NULL DEFAULT NULL COMMENT '歌曲数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  `open` tinyint(1) NULL DEFAULT 0 COMMENT '是否公开',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of musiclist
-- ----------------------------
INSERT INTO `musiclist` VALUES (4, 1, '123', 2, '2020-07-28 19:43:50', 1, 0);
INSERT INTO `musiclist` VALUES (5, 1, '测试歌单', 0, '2020-08-29 16:13:29', 1, 0);
INSERT INTO `musiclist` VALUES (6, 1, '我的歌单', 1, '2020-08-29 16:16:24', 0, 0);
INSERT INTO `musiclist` VALUES (7, 1, '第二个歌单', 2, '2020-08-29 19:20:52', 0, 1);
INSERT INTO `musiclist` VALUES (8, 2, '另一个号的歌单', 2, '2020-09-01 14:56:18', 0, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_USER', '用户');
INSERT INTO `role` VALUES (2, 'ROLE_ADMIN', '管理员');
INSERT INTO `role` VALUES (3, 'ROLE_ROOT', '超管');

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '歌曲id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '歌曲名字',
  `singer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '歌手名',
  `longs` int(11) NULL DEFAULT NULL COMMENT '长度(单位秒)',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '歌曲链接',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '歌曲封面图链接',
  `deleted` int(255) NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of song
-- ----------------------------
INSERT INTO `song` VALUES (1, '风神少女', 'zun', 145, '/music/music/1_ZUN - Wind God Girl.mp3', '/music/img/1_aya3.jpg', 0);
INSERT INTO `song` VALUES (2, 'ZUN - An Ice Fairy in Spring', '9', 133, '/music/music/2_ZUN - An Ice Fairy in Spring.mp3', '/music/img/2_84a979b2e64948b01153430cb3e30f192336309b rescaled.jpg', 0);
INSERT INTO `song` VALUES (3, '幽雅地绽放吧,墨染的樱花', 'zun', 151, '/music/music/3_Yuuga ni Sakase, Sumizome no Sakura ~ Border of Life.mp3', '/music/img/3_BG.jpg', 0);
INSERT INTO `song` VALUES (4, 'Red fog incident of Sunny Milk', 'asdas', 184, '/music/music/4_Red fog incident of Sunny Milk.mp3', '/music/img/4_Three Fairies.jpg', 0);
INSERT INTO `song` VALUES (5, 'Little Princess', 'zun', 228, '/music/music/5_Little Princess.mp3', '/music/img/5_bg.jpg', 0);
INSERT INTO `song` VALUES (6, 'True Administrator', 'fffff', 192, '/music/music/6_True Administrator.mp3', '/music/img/6_bg.jpg', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后修改密码时间',
  `is_ban` tinyint(1) NULL DEFAULT 0 COMMENT '是否被封禁',
  `is_active` tinyint(1) NULL DEFAULT NULL COMMENT '是否激活',
  `collects` int(11) NULL DEFAULT NULL COMMENT '收藏数',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '27709873044@qq.com', '$2a$10$GtLh4MglEzWhTJnhAcUlJeI1.mCONCBzElPYpWz4dnQm/whtAUJdW', '2020-07-22 19:34:39', '2020-08-29 09:51:25', 0, 1, 3);
INSERT INTO `user` VALUES (2, 'forestj', '2770987304@qqq.com', '$2a$10$dnP59qRUTZ3WMH3kdW/qM.QYl/a55WaC5vn/CRJuxn7mbyy3bm3Ma', '2020-07-23 20:33:03', '2020-07-23 20:33:03', 0, 1, 0);
INSERT INTO `user` VALUES (3, 'test1', '2770987304@qq.com', '$2a$10$DPZif3kB27fjWQAsSp2qs.ceq6pVdewalFfsQm5QgLkQKePkA.wfq', '2020-08-28 22:56:23', '2020-08-29 09:30:59', 0, 1, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 2);
INSERT INTO `user_role` VALUES (1, 3);
INSERT INTO `user_role` VALUES (2, 1);
INSERT INTO `user_role` VALUES (3, 1);

SET FOREIGN_KEY_CHECKS = 1;
