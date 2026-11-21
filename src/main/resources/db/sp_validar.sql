-- Validacion: marca ERROR los registros del cargue que no cumplen las reglas
-- del trayecto. Los que siguen en CARGADO y sin observacion quedan VALIDADO.

DROP PROCEDURE IF EXISTS PROYECTO.VALIDAR_TRAYECTO_CARGUE;

DELIMITER $$
CREATE PROCEDURE PROYECTO.VALIDAR_TRAYECTO_CARGUE(IN p_id_cargue DOUBLE)
BEGIN
  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La identificacion no puede ser vacia.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (identificacion IS NULL OR TRIM(identificacion) = '');

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La placa no puede ser vacia.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (placa IS NULL OR TRIM(placa) = '');

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' El codigo de ruta no puede ser vacio.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (codigo_ruta IS NULL OR TRIM(codigo_ruta) = '');

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La ubicacion no puede ser vacia.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (ubicacion IS NULL OR TRIM(ubicacion) = '');

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' El orden de parada debe ser un entero entre 0 y 6.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (orden_parada IS NULL OR TRIM(orden_parada) NOT REGEXP '^[0-6]$');

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La latitud debe ser numerica.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND latitud IS NOT NULL AND TRIM(latitud) <> ''
    AND TRIM(latitud) NOT REGEXP '^-?[0-9]+([.][0-9]+)?$';

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La longitud debe ser numerica.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND longitud IS NOT NULL AND TRIM(longitud) <> ''
    AND TRIM(longitud) NOT REGEXP '^-?[0-9]+([.][0-9]+)?$';

  UPDATE PROYECTO.trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' El login de registro no puede ser vacio.')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (login_registro IS NULL OR TRIM(login_registro) = '');

  UPDATE PROYECTO.trayecto_tmp t
  LEFT JOIN PROYECTO.usuario u ON u.login = TRIM(t.login_registro)
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El login de registro no existe.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.login_registro IS NOT NULL AND TRIM(t.login_registro) <> ''
    AND u.login IS NULL;

  UPDATE PROYECTO.trayecto_tmp t
  LEFT JOIN PROYECTO.Persona p
    ON p.Identificacion = TRIM(t.identificacion) AND p.TipoPersona = 'C'
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La identificacion no corresponde a un conductor.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.identificacion IS NOT NULL AND TRIM(t.identificacion) <> ''
    AND p.ID IS NULL;

  UPDATE PROYECTO.trayecto_tmp t
  LEFT JOIN PROYECTO.Vehiculo v ON v.Placa = TRIM(t.placa)
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La placa no existe.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.placa IS NOT NULL AND TRIM(t.placa) <> ''
    AND v.ID IS NULL;

  UPDATE PROYECTO.trayecto_tmp t
  JOIN PROYECTO.Vehiculo v ON v.Placa = TRIM(t.placa)
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' Los documentos del vehiculo deben estar Habilitados.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND (
      NOT EXISTS (SELECT 1 FROM PROYECTO.VehiculoDocumento vd WHERE vd.VehiculoID = v.ID)
      OR EXISTS (
        SELECT 1 FROM PROYECTO.VehiculoDocumento vd
        WHERE vd.VehiculoID = v.ID AND vd.Estado <> 'Habilitado'
      )
    );

  UPDATE PROYECTO.trayecto_tmp t
  JOIN PROYECTO.Persona p
    ON p.Identificacion = TRIM(t.identificacion) AND p.TipoPersona = 'C'
  JOIN PROYECTO.Vehiculo v ON v.Placa = TRIM(t.placa)
  LEFT JOIN PROYECTO.VehiculoPersona vp
    ON vp.PersonaID = p.ID AND vp.VehiculoID = v.ID AND vp.Estado = 'PO'
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El conductor debe poder operar (PO) el vehiculo.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND vp.ID IS NULL;

  UPDATE PROYECTO.trayecto_tmp t
  JOIN (
    SELECT codigo_ruta, TRIM(orden_parada) AS orden_parada
    FROM PROYECTO.trayecto_tmp
    WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    GROUP BY codigo_ruta, TRIM(orden_parada)
    HAVING COUNT(*) > 1
  ) repetidos
    ON repetidos.codigo_ruta = t.codigo_ruta
   AND repetidos.orden_parada = TRIM(t.orden_parada)
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El orden de parada esta repetido en el archivo.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO';

  UPDATE PROYECTO.trayecto_tmp t
  JOIN PROYECTO.Trayecto tr
    ON tr.CodigoRuta = TRIM(t.codigo_ruta)
   AND tr.OrdenParada = CAST(TRIM(t.orden_parada) AS SIGNED)
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' Ya existe una parada con ese orden en la ruta.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND TRIM(t.orden_parada) REGEXP '^[0-6]$';

  UPDATE PROYECTO.trayecto_tmp t
  JOIN PROYECTO.Trayecto tr ON tr.CodigoRuta = TRIM(t.codigo_ruta)
  JOIN PROYECTO.Persona p ON p.ID = tr.PersonaID
  JOIN PROYECTO.Vehiculo v ON v.ID = tr.VehiculoID
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El codigo de ruta ya pertenece a otro conductor o vehiculo.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND (p.Identificacion <> TRIM(t.identificacion) OR v.Placa <> TRIM(t.placa));

  UPDATE PROYECTO.trayecto_tmp t
  JOIN (
    SELECT codigo_ruta
    FROM PROYECTO.trayecto_tmp
    WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    GROUP BY codigo_ruta
    HAVING COUNT(DISTINCT TRIM(identificacion)) > 1 OR COUNT(DISTINCT TRIM(placa)) > 1
  ) mezcla ON mezcla.codigo_ruta = t.codigo_ruta
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El codigo de ruta mezcla conductor o vehiculo en el archivo.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO';

  UPDATE PROYECTO.trayecto_tmp t
  LEFT JOIN (
    SELECT codigo_ruta
    FROM PROYECTO.trayecto_tmp
    WHERE id_cargue = p_id_cargue AND estado = 'CARGADO' AND TRIM(orden_parada) = '0'
  ) inicial_archivo ON inicial_archivo.codigo_ruta = t.codigo_ruta
  LEFT JOIN PROYECTO.Trayecto tr
    ON tr.CodigoRuta = TRIM(t.codigo_ruta) AND tr.OrdenParada = 0
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La ruta debe tener parada inicial con orden 0.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND inicial_archivo.codigo_ruta IS NULL
    AND tr.ID IS NULL;

  UPDATE PROYECTO.trayecto_tmp t
  JOIN (
    SELECT tmp.codigo_ruta,
           COUNT(*) + (
             SELECT COUNT(*)
             FROM PROYECTO.Trayecto tr
             WHERE tr.CodigoRuta = TRIM(tmp.codigo_ruta)
           ) AS total
    FROM PROYECTO.trayecto_tmp tmp
    WHERE tmp.id_cargue = p_id_cargue AND tmp.estado = 'CARGADO'
    GROUP BY tmp.codigo_ruta
    HAVING total > 7
  ) cupo ON cupo.codigo_ruta = t.codigo_ruta
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La ruta admite como maximo una parada inicial, 5 intermedias y una final.')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO';

  UPDATE PROYECTO.trayecto_tmp
  SET estado = 'ERROR'
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND observacion IS NOT NULL AND observacion <> '';

  UPDATE PROYECTO.trayecto_tmp
  SET estado = 'VALIDADO'
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (observacion IS NULL OR observacion = '');
END $$
DELIMITER ;
