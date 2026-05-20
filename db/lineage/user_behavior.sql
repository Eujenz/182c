-- 導出  表 lineage.user_behavior 結構
CREATE TABLE IF NOT EXISTS `user_behavior` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `ip` char(15) DEFAULT NULL COMMENT '操作者IP',
  `account` char(50) DEFAULT NULL COMMENT '賬號',
  `operID` int(11) DEFAULT NULL COMMENT '角色ID',
  `operType` char(50) DEFAULT NULL COMMENT '操作類型',
  `targetID` char(12) DEFAULT NULL COMMENT '目標ID',
  `locX` int(11) DEFAULT NULL COMMENT '角色坐標X',
  `locY` int(11) DEFAULT NULL COMMENT '角色坐標Y',
  `locMap` int(11) DEFAULT NULL COMMENT '角色坐標地圖編號',
  `operTime` datetime DEFAULT NULL COMMENT '操作時間',
  `remark` char(200) DEFAULT NULL COMMENT '其他備註',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用戶行為記錄';
