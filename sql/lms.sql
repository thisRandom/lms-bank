/*
 Navicat Premium Dump SQL

 Source Server         : remote_user@ 103.47.81.108
 Source Server Type    : MySQL
 Source Server Version : 80045 (8.0.45-0ubuntu0.22.04.1)
 Source Host           : 103.47.81.108:3306
 Source Schema         : lms

 Target Server Type    : MySQL
 Target Server Version : 80045 (8.0.45-0ubuntu0.22.04.1)
 File Encoding         : 65001

 Date: 13/05/2026 14:31:14
*/
create database lms;
use lms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dis_dispatch
-- ----------------------------
DROP TABLE IF EXISTS `dis_dispatch`;
CREATE TABLE `dis_dispatch`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '调度ID',
  `dispatch_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调度单号',
  `order_id` bigint NOT NULL COMMENT '关联订单ID',
  `vehicle_id` bigint NOT NULL COMMENT '分配车辆ID',
  `driver_id` bigint NOT NULL COMMENT '分配司机ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'ASSIGNED' COMMENT '状态',
  `current_location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当前位置',
  `estimated_departure_time` datetime NULL DEFAULT NULL COMMENT '预计发车时间',
  `estimated_arrival_time` datetime NULL DEFAULT NULL COMMENT '预计到达时间',
  `actual_departure_time` datetime NULL DEFAULT NULL COMMENT '实际发车时间',
  `actual_arrival_time` datetime NULL DEFAULT NULL COMMENT '实际到达时间',
  `sign_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签收人姓名',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dispatch_no`(`dispatch_no` ASC) USING BTREE,
  INDEX `idx_dispatch_no`(`dispatch_no` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_vehicle_id`(`vehicle_id` ASC) USING BTREE,
  INDEX `idx_driver_id`(`driver_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `fk_dis_driver` FOREIGN KEY (`driver_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_dis_order` FOREIGN KEY (`order_id`) REFERENCES `ord_order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_dis_vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `veh_vehicle` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dis_dispatch
-- ----------------------------
INSERT INTO `dis_dispatch` VALUES (22, 'DIS202605040001', 27, 44, 3, 'ARRIVED', '吉林省 长春市', '2026-05-03 19:06:35', '2026-05-05 19:06:37', '2026-05-04 11:08:57', '2026-05-04 11:09:27', NULL, NULL, '2026-05-04 11:06:38', '2026-05-04 11:09:27');
INSERT INTO `dis_dispatch` VALUES (23, 'DIS202605040002', 28, 44, 3, 'ARRIVED', '河北省 石家庄市', '2026-05-05 19:17:37', '2026-05-06 19:17:42', '2026-05-04 11:18:57', '2026-05-12 11:39:23', NULL, NULL, '2026-05-04 11:17:44', '2026-05-12 11:39:23');
INSERT INTO `dis_dispatch` VALUES (24, 'DIS202605040003', 29, 45, 4, 'SIGNED', '云南省 昆明市', '2026-05-05 19:21:27', '2026-05-06 19:21:31', '2026-05-04 11:22:06', '2026-05-04 11:22:50', '吴静怡', NULL, '2026-05-04 11:21:46', '2026-05-04 11:23:12');
INSERT INTO `dis_dispatch` VALUES (25, 'DIS202605040004', 31, 45, 4, 'ASSIGNED', NULL, '2026-05-07 19:26:52', '2026-05-07 19:26:55', NULL, NULL, NULL, NULL, '2026-05-04 11:26:59', '2026-05-04 11:26:59');
INSERT INTO `dis_dispatch` VALUES (26, 'DIS202605040005', 33, 46, 3, 'ARRIVED', '新疆维吾尔自治区 巴音郭楞蒙古自治州', '2026-05-04 00:04:00', '2026-05-03 21:49:15', '2026-05-12 11:39:30', '2026-05-12 11:39:46', NULL, NULL, '2026-05-04 13:49:23', '2026-05-12 11:39:46');
INSERT INTO `dis_dispatch` VALUES (27, 'DIS202605040006', 34, 47, 3, 'ASSIGNED', NULL, '2026-05-06 22:15:38', '2026-05-13 22:15:47', NULL, NULL, NULL, NULL, '2026-05-04 14:15:49', '2026-05-04 14:15:49');
INSERT INTO `dis_dispatch` VALUES (28, 'DIS202605060001', 35, 48, 4, 'ARRIVED', '四川省 资阳市', '2026-05-06 19:55:14', '2026-05-09 19:55:18', '2026-05-06 11:56:17', '2026-05-06 11:56:38', NULL, NULL, '2026-05-06 11:55:32', '2026-05-06 11:56:38');
INSERT INTO `dis_dispatch` VALUES (29, 'DIS202605110001', 36, 48, 4, 'ASSIGNED', NULL, '2026-05-11 16:24:54', '2026-05-14 16:25:00', NULL, NULL, NULL, NULL, '2026-05-11 08:25:06', '2026-05-11 08:25:06');
INSERT INTO `dis_dispatch` VALUES (30, 'DIS202605120001', 41, 51, 3, 'ASSIGNED', NULL, '2026-05-01 20:07:21', '2026-05-04 20:07:27', NULL, NULL, NULL, NULL, '2026-05-12 12:07:46', '2026-05-12 12:07:46');
INSERT INTO `dis_dispatch` VALUES (31, 'DIS202605120002', 42, 59, 3, 'ASSIGNED', NULL, '2026-05-12 20:19:40', '2026-05-13 20:19:51', NULL, NULL, NULL, NULL, '2026-05-12 12:19:56', '2026-05-12 12:19:56');
INSERT INTO `dis_dispatch` VALUES (32, 'DIS202605120003', 40, 56, 3, 'ASSIGNED', NULL, '2026-05-12 00:04:00', '2026-05-28 00:02:00', NULL, NULL, NULL, NULL, '2026-05-12 13:09:28', '2026-05-12 13:09:28');
INSERT INTO `dis_dispatch` VALUES (33, 'DIS202605120004', 43, 60, 3, 'ASSIGNED', NULL, '2024-01-01 08:00:00', '2024-01-02 18:00:00', NULL, NULL, NULL, NULL, '2026-05-12 13:18:37', '2026-05-12 13:18:37');

-- ----------------------------
-- Table structure for dis_location_log
-- ----------------------------
DROP TABLE IF EXISTS `dis_location_log`;
CREATE TABLE `dis_location_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '轨迹ID',
  `dispatch_id` bigint NOT NULL COMMENT '调度ID',
  `latitude` decimal(10, 7) NOT NULL COMMENT '纬度',
  `longitude` decimal(10, 7) NOT NULL COMMENT '经度',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '位置描述（可选，选点后反向填充或手动填写）',
  `record_time` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dispatch_id`(`dispatch_id` ASC) USING BTREE,
  INDEX `idx_dispatch_time`(`dispatch_id` ASC, `record_time` ASC) USING BTREE,
  CONSTRAINT `fk_location_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `dis_dispatch` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '轨迹记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dis_location_log
-- ----------------------------
INSERT INTO `dis_location_log` VALUES (15, 22, 38.0454740, 114.5024610, '河北省 石家庄市', '2026-05-04 11:08:57');
INSERT INTO `dis_location_log` VALUES (16, 22, 40.1289360, 116.6535250, '北京市 顺义区', '2026-05-04 11:09:12');
INSERT INTO `dis_location_log` VALUES (17, 22, 43.8868410, 125.3245000, '吉林省 长春市', '2026-05-04 11:09:20');
INSERT INTO `dis_location_log` VALUES (18, 23, 39.1018970, 117.2175360, '天津市 河西区', '2026-05-04 11:18:57');
INSERT INTO `dis_location_log` VALUES (19, 23, 43.8868410, 125.3245000, '吉林省 长春市', '2026-05-04 11:19:13');
INSERT INTO `dis_location_log` VALUES (20, 23, 45.7569670, 126.6424640, '黑龙江省 哈尔滨市', '2026-05-04 11:19:23');
INSERT INTO `dis_location_log` VALUES (21, 24, 26.0753020, 119.3062390, '福建省 福州市', '2026-05-04 11:22:06');
INSERT INTO `dis_location_log` VALUES (22, 24, 23.0794040, 114.4125990, '广东省 惠州市', '2026-05-04 11:22:32');
INSERT INTO `dis_location_log` VALUES (23, 24, 25.0406090, 102.7122510, '云南省 昆明市', '2026-05-04 11:22:47');
INSERT INTO `dis_location_log` VALUES (24, 23, 38.0454740, 114.5024610, '河北省 石家庄市', '2026-05-06 11:53:08');
INSERT INTO `dis_location_log` VALUES (25, 28, 34.7970490, 114.3414470, '河南省 开封市', '2026-05-06 11:56:17');
INSERT INTO `dis_location_log` VALUES (26, 28, 30.1222110, 104.6419170, '四川省 资阳市', '2026-05-06 11:56:29');
INSERT INTO `dis_location_log` VALUES (27, 26, 42.2753170, 118.9568060, '内蒙古自治区 赤峰市', '2026-05-12 11:39:30');
INSERT INTO `dis_location_log` VALUES (28, 26, 31.0354200, 112.2042510, '湖北省 荆门市', '2026-05-12 11:39:35');
INSERT INTO `dis_location_log` VALUES (29, 26, 33.0040490, 97.0085220, '青海省 玉树藏族自治州', '2026-05-12 11:39:39');
INSERT INTO `dis_location_log` VALUES (30, 26, 41.7685520, 86.1509690, '新疆维吾尔自治区 巴音郭楞蒙古自治州', '2026-05-12 11:39:44');

-- ----------------------------
-- Table structure for ord_order
-- ----------------------------
DROP TABLE IF EXISTS `ord_order`;
CREATE TABLE `ord_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `shipper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货人姓名',
  `shipper_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货人电话',
  `shipper_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货详细地址',
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人电话',
  `receiver_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货详细地址',
  `goods_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货物类型',
  `weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '货物重量（吨）',
  `volume` decimal(10, 2) NULL DEFAULT NULL COMMENT '货物体积（方）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'PENDING' COMMENT '状态',
  `customer_id` bigint NULL DEFAULT NULL COMMENT '下单客户ID',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_customer_id`(`customer_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  CONSTRAINT `fk_ord_customer` FOREIGN KEY (`customer_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ord_order
-- ----------------------------
INSERT INTO `ord_order` VALUES (27, 'ORD202605040001', 'wjh', '15585692563', '河北省石家庄市藁城区信息工程学院', 'qyy', '17654321098', '吉林省长春市朝阳区吉林大学', '电子产品', 10.00, 20.00, 'ARRIVED', 5, '1', '2026-05-04 11:06:22', '2026-05-13 06:30:36');
INSERT INTO `ord_order` VALUES (28, 'ORD202605040002', '李芳华', '17345678901', '天津市市辖区河西区友谊路35号城市大厦A座2306室', '刘志强', '18398765432', '黑龙江省哈尔滨市南岗区西大直街92号哈尔滨工业大学科学园2A栋', '食品饮料', 23.00, 21.00, 'ARRIVED', 5, NULL, '2026-05-04 11:16:47', '2026-05-12 11:39:23');
INSERT INTO `ord_order` VALUES (29, 'ORD202605040003', '赵雅芝', '15912341234', '福建省福州市台江区江滨中大道358号海峡银行大厦15层', '吴静怡', '14700001111', '云南省昆明市五华区人民中路202号戈登大厦18楼1805', '服装鞋帽', 52.00, 26.00, 'SIGNED', 5, NULL, '2026-05-04 11:19:47', '2026-05-04 11:23:12');
INSERT INTO `ord_order` VALUES (30, 'ORD202605040004', '陈晓丽', '18977778888', '湖南省长沙市岳麓区潇湘中路328号麓谷企业广场C3栋202', '刘志强', '15233445566', '广西壮族自治区南宁市青秀区民族大道146号三祺广场17楼', '家具家居', 25.00, 45.00, 'CANCELLED', 6, NULL, '2026-05-04 11:25:02', '2026-05-12 06:50:01');
INSERT INTO `ord_order` VALUES (31, 'ORD202605040005', '张明伟', '13588889999', '山东省济南市历下区经十路17703号华润中心大厦38层', '林雨桐', '17654321098', '海南省海口市龙华区滨海大道105号百方广场B座9层901', '医药用品', 23.00, 50.00, 'DISPATCHED', 6, '这是备注信息', '2026-05-04 11:26:37', '2026-05-04 11:41:01');
INSERT INTO `ord_order` VALUES (32, 'ORD202605040006', '刘志强', '15912341234', '江西省南昌市红谷滩区赣江中大道1218号秋水广场旁新地中心2703', '周浩然', '18398765432', '安徽省合肥市包河区徽州大道838号宝利丰广场写字楼2410', '医药用品', 23.00, 45.00, 'CANCELLED', 6, NULL, '2026-05-04 11:28:32', '2026-05-04 11:47:21');
INSERT INTO `ord_order` VALUES (33, 'ORD202605040007', '王美丽', '13929993929', '天津市市辖区河东区顺义大厦D区1209', '李漂亮', '13232323232', '山西省阳泉市郊区向阳路23号', '其他', 3.00, 2.00, 'ARRIVED', NULL, NULL, '2026-05-04 13:33:00', '2026-05-12 11:39:46');
INSERT INTO `ord_order` VALUES (34, 'ORD202605040008', '陈小花', '13367543466', '山西省晋城市泽州县县政府', '封副', '13787666666', '河北省石家庄市长安区长安广场2602', '服装鞋帽', 1.00, 1.00, 'DISPATCHED', 6, NULL, '2026-05-04 13:40:27', '2026-05-04 14:15:49');
INSERT INTO `ord_order` VALUES (35, 'ORD202605040009', '哈哈哈', '15531485915', '山西省晋城市沁水县人民广场', '你好', '15698563256', '内蒙古自治区通辽市科尔沁左翼后旗快递驿站', '家具家居', 21.00, 25.00, 'ARRIVED', 6, NULL, '2026-05-04 14:50:44', '2026-05-13 06:30:50');
INSERT INTO `ord_order` VALUES (36, 'ORD202605070001', '发货人A', '18713520849', '北京市市辖区西城区发货详细地址', '收货人B', '18713520849', '河北省石家庄市桥西区收货详细地址', '服装', 12.00, 5.00, 'DISPATCHED', NULL, NULL, '2026-05-07 03:02:22', '2026-05-11 08:25:06');
INSERT INTO `ord_order` VALUES (37, 'ORD202605120001', '测试发件人', '13800001111', '北京市朝阳区测试路1号', '测试收件人', '13900002222', '上海市浦东新区测试路2号', '修改后货物', 3.00, 1.00, 'CANCELLED', 5, 'API测试订单', '2026-05-12 06:50:01', '2026-05-12 07:28:23');
INSERT INTO `ord_order` VALUES (38, 'ORD202605120002', '测试发件', '13800001111', '北京测试路1号', '测试收件', '13900002222', '上海测试路2号', '测试货物', 1.00, 0.50, 'CANCELLED', 5, NULL, '2026-05-12 07:28:22', '2026-05-12 11:40:24');
INSERT INTO `ord_order` VALUES (39, 'ORD202605120003', '小马', '15632486526', '北京市市辖区东城区超验', '小刘', '15632486526', '天津市市辖区和平区超杨', '电子产品', 10.00, 10.00, 'PENDING', NULL, NULL, '2026-05-12 11:45:29', '2026-05-12 11:45:29');
INSERT INTO `ord_order` VALUES (40, 'ORD202605120004', '小马', '15632486522', '北京市市辖区东城区帅哥', '小刘', '15632486526', '河北省秦皇岛市山海关区帅哥', '家具', 10.00, 10.00, 'DISPATCHED', NULL, NULL, '2026-05-12 11:58:39', '2026-05-12 13:09:28');
INSERT INTO `ord_order` VALUES (41, 'ORD202605120005', '小马', '13800138001', '河北省秦皇岛市山海关区帅哥', '小刘', '13800138002', '北京市市辖区东城区帅哥', '电子产品', 5.00, 10.00, 'DISPATCHED', NULL, NULL, '2026-05-12 12:05:42', '2026-05-12 12:07:46');
INSERT INTO `ord_order` VALUES (42, 'ORD202605120006', '小马', '13800138001', '河北省秦皇岛市山海关区帅哥', '小刘', '13800138002', '北京市市辖区东城区帅哥', '电子产品', 5.00, 10.00, 'DISPATCHED', NULL, NULL, '2026-05-12 12:18:34', '2026-05-12 12:19:56');
INSERT INTO `ord_order` VALUES (43, 'ORD202605120007', '小马', '13800138001', '河北省秦皇岛市山海关区帅哥', '小刘', '13800138002', '北京市市辖区东城区帅哥', '电子产品', 5.00, 10.00, 'DISPATCHED', NULL, NULL, '2026-05-12 13:17:53', '2026-05-12 13:18:37');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色代码',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_code`(`role_code` ASC) USING BTREE,
  INDEX `idx_role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '管理员', '系统全部功能', '2026-04-24 05:34:07');
INSERT INTO `sys_role` VALUES (2, 'DISPATCHER', '调度员', '订单调度、车辆管理', '2026-04-24 05:34:07');
INSERT INTO `sys_role` VALUES (3, 'DRIVER', '司机', '执行运输任务', '2026-04-24 05:34:07');
INSERT INTO `sys_role` VALUES (4, 'CUSTOMER', '客户', '下单、查订单', '2026-04-24 05:34:07');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `headshot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$Pmbjiw5I6mT/n6SSbaoekufcAZuxsqS3S8D/flfZHWolCtMUvNG2.', '李四维', '13754658598', 1, 1, '2026-04-24 05:35:02', '2026-05-12 12:49:37', '/c1c7c6feff474bd5a4ae67ec4e2bd4ee.jpg');
INSERT INTO `sys_user` VALUES (2, 'dispatcher', '$2a$10$kvZbTMOtZFpudml4qDe2TO4J8CsuDQN1LEeC9gXmOzTUyxCOBktE.', '李调度', '13800138004', 2, 1, '2026-04-24 05:35:02', '2026-05-05 22:43:59', NULL);
INSERT INTO `sys_user` VALUES (3, 'driver1', '$2a$10$y0I3I9vZik0JaJNzJ0Ykje5j6Jw7qk5E3rAkAuwElWm/oSrjuMCc2', '李师傅', '13800139011', 3, 1, '2026-04-24 05:35:02', '2026-05-11 13:16:11', '/a7489ea47f01460a9bd3d7aff7f4112b.jpg');
INSERT INTO `sys_user` VALUES (4, 'driver2', '$2a$10$QGwQig4exuchrojmXucvbupm9NkdnzPawm2l5noRCkRoiDab/b/1.', '王师傅', '13800138003', 3, 1, '2026-04-24 05:35:02', '2026-04-25 08:53:14', NULL);
INSERT INTO `sys_user` VALUES (5, 'customer1', '$2a$10$Tuf6cyEj48Bp.vQIk1Cf6esjJ57QXd1U3QTxEOIx6STo6i0sdf.xa', '刘女士', '13800138004', 4, 1, '2026-04-24 05:35:02', '2026-05-12 07:28:12', '/05687eee7b0847b6b504a249ee8a0cf7.jpg');
INSERT INTO `sys_user` VALUES (6, 'customer2', '$2a$10$QGwQig4exuchrojmXucvbupm9NkdnzPawm2l5noRCkRoiDab/b/1.', '陈女士', '13800138005', 4, 0, '2026-04-24 05:35:02', '2026-05-12 11:43:24', NULL);
INSERT INTO `sys_user` VALUES (12, 'gadehao', '$2a$10$hQxdtPQi5zeW4yFMTjC.QuokshV.eImc4Ca1uMYFzBvJPYKdjBUXS', '干得好', '13099878873', 2, 1, '2026-05-05 22:45:31', '2026-05-05 22:45:31', NULL);
INSERT INTO `sys_user` VALUES (13, 'adminpjz', '$2a$10$06YNxvDqAqcRBCzxrSN0D.x.Zm70m4vvxs1Q/QLWdKs10cb.b/vUS', 'pjz', '13988765432', 1, 1, '2026-05-10 10:15:31', '2026-05-11 06:41:28', '/c3558b85796041d6b15ebe6fdd622e6a.jpg');

-- ----------------------------
-- Table structure for veh_vehicle
-- ----------------------------
DROP TABLE IF EXISTS `veh_vehicle`;
CREATE TABLE `veh_vehicle`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `plate_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车牌号',
  `vehicle_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'TRUCK' COMMENT '车辆类型：TRUCK/VAN/PICKUP',
  `load_capacity` decimal(10, 2) NULL DEFAULT NULL COMMENT '载重（吨）',
  `driver_id` bigint NULL DEFAULT NULL COMMENT '绑定司机ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'IDLE' COMMENT '状态：IDLE空闲/BUSY运输中/MAINTENANCE维修',
  `last_location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后位置',
  `last_update_time` datetime NULL DEFAULT NULL COMMENT '位置更新时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `volume` decimal(10, 2) NULL DEFAULT NULL COMMENT '体积（方）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `plate_number`(`plate_number` ASC) USING BTREE,
  INDEX `idx_plate_number`(`plate_number` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_driver_id`(`driver_id` ASC) USING BTREE,
  CONSTRAINT `fk_veh_driver` FOREIGN KEY (`driver_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '车辆表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of veh_vehicle
-- ----------------------------
INSERT INTO `veh_vehicle` VALUES (44, '京A88888', 'VAN', 100.00, 3, 'IDLE', '河北省 石家庄市', NULL, '2026-05-04 11:05:16', '2026-05-12 11:39:23', 200.00);
INSERT INTO `veh_vehicle` VALUES (45, '川A66666', 'TRUCK', 60.00, 4, 'BUSY', '云南省 昆明市', NULL, '2026-05-04 11:21:09', '2026-05-12 07:48:01', 100.00);
INSERT INTO `veh_vehicle` VALUES (46, '津A22222', 'TRUCK', 20.00, 3, 'IDLE', '新疆维吾尔自治区 巴音郭楞蒙古自治州', NULL, '2026-05-04 11:38:45', '2026-05-12 11:39:46', 20.00);
INSERT INTO `veh_vehicle` VALUES (47, '冀A12345', 'PICKUP', 3.00, 3, 'BUSY', NULL, NULL, '2026-05-04 13:16:30', '2026-05-04 14:15:49', 6.00);
INSERT INTO `veh_vehicle` VALUES (48, '京B88888', 'VAN', 100.00, 4, 'BUSY', '四川省 资阳市', NULL, '2026-05-06 11:55:18', '2026-05-11 08:25:06', 200.00);
INSERT INTO `veh_vehicle` VALUES (51, '京A88885', 'VAN', 1233.00, 3, 'BUSY', NULL, NULL, '2026-05-12 05:02:24', '2026-05-12 12:07:46', 100.00);
INSERT INTO `veh_vehicle` VALUES (52, '京A88886', 'VAN', 232.00, 3, 'IDLE', NULL, NULL, '2026-05-12 05:02:51', '2026-05-12 05:02:51', 100.00);
INSERT INTO `veh_vehicle` VALUES (56, '京A11111', 'TRUCK', 1000.00, 3, 'BUSY', NULL, NULL, '2026-05-12 08:10:14', '2026-05-12 13:09:28', 123.00);
INSERT INTO `veh_vehicle` VALUES (57, '京A11112', 'PICKUP', 1000.00, 3, 'IDLE', NULL, NULL, '2026-05-12 08:28:53', '2026-05-12 08:28:53', 123.00);
INSERT INTO `veh_vehicle` VALUES (58, '京A11113', 'VAN', 1000.00, 3, 'IDLE', NULL, NULL, '2026-05-12 08:29:19', '2026-05-12 08:29:42', 123.00);
INSERT INTO `veh_vehicle` VALUES (59, '京A21113', 'PICKUP', 1000.00, 3, 'BUSY', NULL, NULL, '2026-05-12 12:18:25', '2026-05-12 12:19:56', 123.00);
INSERT INTO `veh_vehicle` VALUES (60, '京A21123', 'PICKUP', 1000.00, 3, 'BUSY', NULL, NULL, '2026-05-12 13:17:44', '2026-05-12 13:18:37', 123.00);

SET FOREIGN_KEY_CHECKS = 1;
