DROP PROCEDURE IF EXISTS VALIDAR_TRAYECTO_CARGUE;

DELIMITER $$
CREATE PROCEDURE VALIDAR_TRAYECTO_CARGUE(IN p_id_cargue DOUBLE)
BEGIN
  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La identificacion no puede ser vacia. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (identificacion IS NULL OR TRIM(identificacion) = '');

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La placa no puede ser vacia. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (placa IS NULL OR TRIM(placa) = '');

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' El codigo de ruta no puede ser vacio. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (codigo_ruta IS NULL OR TRIM(codigo_ruta) = '');

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La ubicacion no puede ser vacia. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (ubicacion IS NULL OR TRIM(ubicacion) = '');

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' El orden de parada debe ser un entero entre 0 y 6. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (orden_parada IS NULL OR TRIM(orden_parada) NOT REGEXP '^[0-6]$');

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La latitud debe ser numerica. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND latitud IS NOT NULL AND TRIM(latitud) <> ''
    AND TRIM(latitud) NOT REGEXP '^-?[0-9]+([.][0-9]+)?$';

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' La longitud debe ser numerica. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND longitud IS NOT NULL AND TRIM(longitud) <> ''
    AND TRIM(longitud) NOT REGEXP '^-?[0-9]+([.][0-9]+)?$';

  UPDATE trayecto_tmp
  SET observacion = CONCAT(IFNULL(observacion, ''), ' El login de registro no puede ser vacio. ')
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (login_registro IS NULL OR TRIM(login_registro) = '');

  UPDATE trayecto_tmp t
  LEFT JOIN usuario u ON u.login = t.login_registro
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El login de registro no existe. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.login_registro IS NOT NULL AND TRIM(t.login_registro) <> ''
    AND u.login IS NULL;

  UPDATE trayecto_tmp t
  LEFT JOIN persona p ON p.identificacion = t.identificacion AND p.tipo_persona = 'C'
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La identificacion no corresponde a un conductor. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.identificacion IS NOT NULL AND TRIM(t.identificacion) <> ''
    AND p.id IS NULL;

  UPDATE trayecto_tmp t
  LEFT JOIN vehiculo v ON v.placa = t.placa
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La placa no existe. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.placa IS NOT NULL AND TRIM(t.placa) <> ''
    AND v.id IS NULL;

  UPDATE trayecto_tmp t
  JOIN vehiculo v ON v.placa = t.placa
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' Los documentos del vehiculo deben estar Habilitados. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND (
      NOT EXISTS (SELECT 1 FROM vehiculo_documento vd WHERE vd.vehiculoid = v.id)
      OR EXISTS (SELECT 1 FROM vehiculo_documento vd WHERE vd.vehiculoid = v.id AND vd.estado <> 'Habilitado')
    );

  UPDATE trayecto_tmp t
  JOIN persona p ON p.identificacion = t.identificacion AND p.tipo_persona = 'C'
  JOIN vehiculo v ON v.placa = t.placa
  LEFT JOIN vehiculo_persona vp
    ON vp.personaid = p.id AND vp.vehiculoid = v.id AND vp.estado = 'PO'
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El conductor debe poder operar (PO) el vehiculo. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND vp.id IS NULL;

  UPDATE trayecto_tmp t
  JOIN (
    SELECT codigo_ruta, orden_parada
    FROM trayecto_tmp
    WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    GROUP BY codigo_ruta, orden_parada
    HAVING COUNT(*) > 1
  ) repetidos ON repetidos.codigo_ruta = t.codigo_ruta AND repetidos.orden_parada = t.orden_parada
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El orden de parada esta repetido en el archivo. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO';

  UPDATE trayecto_tmp t
  JOIN trayecto tr ON tr.codigo_ruta = t.codigo_ruta AND tr.orden_parada = CAST(t.orden_parada AS SIGNED)
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' Ya existe una parada con ese orden en la ruta. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND t.orden_parada REGEXP '^[0-6]$';

  UPDATE trayecto_tmp t
  JOIN trayecto tr ON tr.codigo_ruta = t.codigo_ruta
  JOIN persona p ON p.id = tr.personaid
  JOIN vehiculo v ON v.id = tr.vehiculoid
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El codigo de ruta ya pertenece a otro conductor o vehiculo. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND (p.identificacion <> t.identificacion OR v.placa <> t.placa);

  UPDATE trayecto_tmp t
  JOIN (
    SELECT codigo_ruta
    FROM trayecto_tmp
    WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    GROUP BY codigo_ruta
    HAVING COUNT(DISTINCT identificacion) > 1 OR COUNT(DISTINCT placa) > 1
  ) mezcla ON mezcla.codigo_ruta = t.codigo_ruta
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' El codigo de ruta mezcla conductor o vehiculo en el archivo. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO';

  UPDATE trayecto_tmp t
  LEFT JOIN (
    SELECT codigo_ruta
    FROM trayecto_tmp
    WHERE id_cargue = p_id_cargue AND estado = 'CARGADO' AND orden_parada = '0'
  ) inicial_archivo ON inicial_archivo.codigo_ruta = t.codigo_ruta
  LEFT JOIN trayecto tr ON tr.codigo_ruta = t.codigo_ruta AND tr.orden_parada = 0
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La ruta debe tener parada inicial con orden 0. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO'
    AND inicial_archivo.codigo_ruta IS NULL
    AND tr.id IS NULL;

  UPDATE trayecto_tmp t
  JOIN (
    SELECT tmp.codigo_ruta,
           COUNT(*) + (
             SELECT COUNT(*) FROM trayecto tr WHERE tr.codigo_ruta = tmp.codigo_ruta
           ) AS total
    FROM trayecto_tmp tmp
    WHERE tmp.id_cargue = p_id_cargue AND tmp.estado = 'CARGADO'
    GROUP BY tmp.codigo_ruta
    HAVING total > 7
  ) cupo ON cupo.codigo_ruta = t.codigo_ruta
  SET t.observacion = CONCAT(IFNULL(t.observacion, ''), ' La ruta admite como maximo una parada inicial, 5 intermedias y una final. ')
  WHERE t.id_cargue = p_id_cargue AND t.estado = 'CARGADO';

  UPDATE trayecto_tmp
  SET estado = 'ERROR'
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND observacion IS NOT NULL AND observacion <> '';

  UPDATE trayecto_tmp
  SET estado = 'VALIDADO'
  WHERE id_cargue = p_id_cargue AND estado = 'CARGADO'
    AND (observacion IS NULL OR observacion = '');
END $$
DELIMITER ;
