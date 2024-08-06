CREATE TABLE `tb_parking_history` (
  `parking_history_idx` int NOT NULL AUTO_INCREMENT,
  `parking_location` int NOT NULL,
  `parking_car_number` varchar(8) NOT NULL,
  `parking_type` char(1) NOT NULL DEFAULT 'I',
  `parking_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`parking_history_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
