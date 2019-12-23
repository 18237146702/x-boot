-- 数据导出被取消选择。
-- 导出  請假申請表 it_leave 结构
CREATE TABLE IF NOT EXISTS `IT_LEAVE` (
  `id` int(10) NOT NULL,
  `process_Instance_Id` varchar(64) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `user_Id` varchar(40) DEFAULT NULL,
  `user_Name` varchar(50) DEFAULT NULL,
  `days` int(10) DEFAULT NULL,
  `start_Time` timestamp NOT NULL,
  `end_Time` timestamp NOT NULL,
  `leave_Type` varchar(40) DEFAULT NULL,
  `reason` varchar(1000) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `caeate_Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_Time` timestamp NULL DEFAULT NULL,
  `submit_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;