DROP PROCEDURE IF EXISTS PROCESAR_TRAYECTO_EXCEL;

DELIMITER $$
CREATE PROCEDURE PROCESAR_TRAYECTO_EXCEL(IN p_id_cargue DOUBLE)
BEGIN
  INSERT INTO trayecto (
    personaid, vehiculoid, codigo_ruta, ubicacion, orden_parada, latitud, longitud, login_registro
  )
  SELECT
    p.id,
    v.id,
    t.codigo_ruta,
    t.ubicacion,
    CAST(t.orden_parada AS SIGNED),
    CASE WHEN t.latitud IS NULL OR TRIM(t.latitud) = '' THEN NULL ELSE CAST(t.latitud AS DOUBLE) END,
    CASE WHEN t.longitud IS NULL OR TRIM(t.longitud) = '' THEN NULL ELSE CAST(t.longitud AS DOUBLE) END,
    t.login_registro
  FROM trayecto_tmp t
  JOIN persona p ON p.identificacion = t.identificacion
  JOIN vehiculo v ON v.placa = t.placa
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'VALIDADO';

  UPDATE trayecto_tmp
  SET estado = 'PROCESADO'
  WHERE id_cargue = p_id_cargue AND estado = 'VALIDADO';
END $$
DELIMITER ;
