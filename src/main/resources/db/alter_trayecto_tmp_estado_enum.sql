-- Cambia trayecto_tmp.estado de varchar + CHECK al ENUM del laboratorio.
-- Ejecutar en MySQL sobre la base PROYECTO.

USE PROYECTO;

SET @check_nombre = (
  SELECT CONSTRAINT_NAME
  FROM information_schema.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA = 'PROYECTO'
    AND TABLE_NAME = 'trayecto_tmp'
    AND CONSTRAINT_TYPE = 'CHECK'
  LIMIT 1
);

SET @drop_check = IF(
  @check_nombre IS NULL,
  'SELECT 1',
  CONCAT('ALTER TABLE PROYECTO.trayecto_tmp DROP CHECK `', @check_nombre, '`')
);

PREPARE stmt_drop_check FROM @drop_check;
EXECUTE stmt_drop_check;
DEALLOCATE PREPARE stmt_drop_check;

ALTER TABLE PROYECTO.trayecto_tmp
  MODIFY estado ENUM('CARGADO', 'VALIDADO', 'PROCESADO', 'ERROR') NOT NULL;
