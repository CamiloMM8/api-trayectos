-- Procesado: traslada a Trayecto solo los registros VALIDADO y los marca PROCESADO.
-- La conversion de tipos ocurre aqui, no en el cargue.

DROP PROCEDURE IF EXISTS PROYECTO.PROCESAR_TRAYECTO_EXCEL;

DELIMITER $$
CREATE PROCEDURE PROYECTO.PROCESAR_TRAYECTO_EXCEL(IN p_id_cargue DOUBLE)
BEGIN
  INSERT INTO PROYECTO.Trayecto (
    PersonaID, VehiculoID, CodigoRuta, Ubicacion, OrdenParada, Latitud, Longitud, LoginRegistro
  )
  SELECT
    p.ID,
    v.ID,
    TRIM(t.codigo_ruta),
    TRIM(t.ubicacion),
    CAST(TRIM(t.orden_parada) AS SIGNED),
    CASE
      WHEN t.latitud IS NULL OR TRIM(t.latitud) = '' THEN NULL
      ELSE CAST(TRIM(t.latitud) AS DOUBLE)
    END,
    CASE
      WHEN t.longitud IS NULL OR TRIM(t.longitud) = '' THEN NULL
      ELSE CAST(TRIM(t.longitud) AS DOUBLE)
    END,
    TRIM(t.login_registro)
  FROM PROYECTO.trayecto_tmp t
  JOIN PROYECTO.Persona p ON p.Identificacion = TRIM(t.identificacion)
  JOIN PROYECTO.Vehiculo v ON v.Placa = TRIM(t.placa)
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'VALIDADO';

  UPDATE PROYECTO.trayecto_tmp
  SET estado = 'PROCESADO'
  WHERE id_cargue = p_id_cargue AND estado = 'VALIDADO';
END $$
DELIMITER ;
