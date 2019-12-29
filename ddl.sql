-- 数据导出被取消选择。
-- 导出  請假申請表 it_leave 结构
CREATE TABLE IF NOT EXISTS `it_leave` (
  `id` int(10) NOT NULL,
  `process_instance_id` varchar(64) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `user_id` varchar(40) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `days` int(10) DEFAULT NULL,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,
  `leave_type` varchar(40) DEFAULT NULL,
  `reason` varchar(1000) DEFAULT NULL,
  `status` tinyint(3) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `submit_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;