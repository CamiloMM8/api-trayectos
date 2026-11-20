DROP PROCEDURE IF EXISTS CARGAR_TRAYECTO_EXCEL;

DELIMITER $$
CREATE PROCEDURE CARGAR_TRAYECTO_EXCEL(
  IN p_identificacion VARCHAR(100),
  IN p_placa VARCHAR(20),
  IN p_codigo_ruta VARCHAR(30),
  IN p_ubicacion VARCHAR(255),
  IN p_orden_parada VARCHAR(10),
  IN p_latitud VARCHAR(40),
  IN p_longitud VARCHAR(40),
  IN p_login_registro VARCHAR(100),
  IN p_id_cargue DOUBLE
)
BEGIN
  INSERT INTO trayecto_tmp (
    identificacion, placa, codigo_ruta, ubicacion, orden_parada,
    latitud, longitud, login_registro, estado, observacion, id_cargue
  ) VALUES (
    p_identificacion, p_placa, p_codigo_ruta, p_ubicacion, p_orden_parada,
    p_latitud, p_longitud, p_login_registro, 'CARGADO', '', p_id_cargue
  );
END $$
DELIMITER ;
