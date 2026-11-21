CREATE TABLE IF NOT EXISTS PROYECTO.trayecto_tmp (
  id bigint NOT NULL AUTO_INCREMENT,
  identificacion varchar(100) DEFAULT NULL,
  placa varchar(20) DEFAULT NULL,
  codigo_ruta varchar(30) DEFAULT NULL,
  ubicacion varchar(255) DEFAULT NULL,
  orden_parada varchar(10) DEFAULT NULL,
  latitud varchar(40) DEFAULT NULL,
  longitud varchar(40) DEFAULT NULL,
  login_registro varchar(100) DEFAULT NULL,
  estado ENUM('CARGADO', 'VALIDADO', 'PROCESADO', 'ERROR') NOT NULL,
  observacion varchar(1000) DEFAULT NULL,
  id_cargue double NOT NULL,
  PRIMARY KEY (id),
  KEY idx_trayecto_tmp_cargue (id_cargue)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
