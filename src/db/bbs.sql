/*
MySQL Data Transfer
Source Host: localhost
Source Database: bbs
Target Host: localhost
Target Database: bbs
Date: 2017/11/7 8:42:17
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for b_message
-- ----------------------------
DROP TABLE IF EXISTS `b_message`;
CREATE TABLE `b_message` (
  `msgid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `title` varchar(40) NOT NULL,
  `msgcontent` varchar(400) NOT NULL,
  `state` int(11) NOT NULL,
  `sendto` varchar(20) NOT NULL,
  `msg_create_date` datetime NOT NULL,
  PRIMARY KEY (`msgid`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for b_user
-- ----------------------------
DROP TABLE IF EXISTS `b_user`;
CREATE TABLE `b_user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `b_message` VALUES ('8', 'liujie', 'li', '你好', '0', 'li', '2017-10-10 00:00:00');
INSERT INTO `b_message` VALUES ('9', 'admin', 'li', '你好', '0', 'li', '2017-10-10 00:00:00');
INSERT INTO `b_message` VALUES ('27', 'admin', 'dd', 'dd', '0', 'li', '2017-10-11 00:00:00');
INSERT INTO `b_message` VALUES ('37', 'liujie', 'test', 'test', '1', 'admin', '2017-10-12 00:00:00');
INSERT INTO `b_message` VALUES ('39', '刘杰', 'test', 'test', '1', 'admin', '2017-10-13 00:00:00');
INSERT INTO `b_message` VALUES ('40', '刘杰', 'test', 'test', '1', 'admin', '2017-10-13 00:00:00');
INSERT INTO `b_message` VALUES ('43', '刘杰', 'adc', 'adc', '1', 'admin', '2017-10-13 00:00:00');
INSERT INTO `b_message` VALUES ('46', 'admin', 'haha', 'haha', '1', 'liujie', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('47', 'liujie', 'dashabi', 'jiushini', '1', 'admin', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('48', 'admin', 'shabi', 'shabi', '1', 'liujie', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('50', 'admin', 'test', 'test', '1', 'liujie', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('51', 'liujie', 'test', 'test', '1', 'admin', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('52', 'admin', 'test', 'ts', '1', 'liujie', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('53', 'admin', 'test', 'test', '1', 'liujie', '2017-10-14 00:00:00');
INSERT INTO `b_message` VALUES ('55', 'admin', 'fuck', 'fuck', '0', 'addddddd', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('57', 'admin', 'fuck', 'fuck', '1', 'liujie', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('59', 'liujie', 'mani', 'nizhenshigeliujie', '1', 'admin', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('60', 'liujie', '傻比', '傻比', '1', 'admin', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('61', 'liujie', '大好人', '大好人', '1', 'admin', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('62', 'admin', 'o ', 'o ', '1', 'liujie', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('63', 'admin', '你大爷啊', '嘿嘿嘿', '0', 'addddddd', '2017-10-16 00:00:00');
INSERT INTO `b_message` VALUES ('64', 'liujie', '你大爷的', '地地道道', '1', 'lidalian', '2017-10-16 00:00:00');
INSERT INTO `b_user` VALUES ('addddddd', '12345678', '799106367@qq.com');
INSERT INTO `b_user` VALUES ('admin', 'admin', 'sddf@ll.com');
INSERT INTO `b_user` VALUES ('admin1', 'adminadmin', '799106367@qq.com');
INSERT INTO `b_user` VALUES ('li', '111', '111');
INSERT INTO `b_user` VALUES ('lidalian', 'lixiaolianbadabada', 'xiannv7788@qq.com');
INSERT INTO `b_user` VALUES ('liujie', 'liujie', 'dd');
INSERT INTO `b_user` VALUES ('test', 'test', 'test');
INSERT INTO `b_user` VALUES ('刘杰', 'liujie', '23');
