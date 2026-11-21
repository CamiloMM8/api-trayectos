package com.example.Proyecto.Services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Proyecto.Entities.TrayectoTmp;
import com.example.Proyecto.Repository.TrayectoTmpRepository;
import com.example.Proyecto.Services.Interfaces.IExcelService;

@Service("ExcelService")
public class ExcelService implements IExcelService {

    @Autowired
    @Qualifier("ITrayectoTmpRepo")
    private TrayectoTmpRepository trayectoTmpRepository;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(ExcelService.class);

    @Override
    public Double processExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de Excel es obligatorio");
        }
        try (InputStream inputStream = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            double idCargue = Double.parseDouble(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
            int filas = 0;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String identificacion = texto(formatter, row, 0);
                String placa = texto(formatter, row, 1);
                String codigoRuta = texto(formatter, row, 2);
                String ubicacion = texto(formatter, row, 3);
                String ordenParada = texto(formatter, row, 4);
                String latitud = texto(formatter, row, 5);
                String longitud = texto(formatter, row, 6);
                String loginRegistro = texto(formatter, row, 7);
                if (filaVacia(identificacion, placa, codigoRuta, ubicacion, ordenParada, latitud, longitud, loginRegistro)) {
                    continue;
                }
                if (!cargarDatosExcel(identificacion, placa, codigoRuta, ubicacion, ordenParada, latitud, longitud,
                        loginRegistro, idCargue)) {
                    throw new IllegalStateException("No se pudo cargar una fila del archivo");
                }
                filas++;
            }
            if (filas == 0) {
                throw new IllegalArgumentException("El archivo no contiene filas para cargar");
            }
            if (!validarDatosExcel(idCargue) || !procesarDatosExcel(idCargue)) {
                throw new IllegalStateException("No se pudo validar o procesar el cargue " + (long) idCargue);
            }
            return idCargue;
        } catch (IOException e) {
            logger.error("ERROR LEYENDO EL ARCHIVO EXCEL");
            throw new IllegalArgumentException("No se pudo leer el archivo de Excel");
        }
    }

    @Override
    public boolean cargarDatosExcel(String identificacion, String placa, String codigoRuta, String ubicacion,
            String ordenParada, String latitud, String longitud, String loginRegistro, Double idCargue) {
        try {
            trayectoTmpRepository.CARGAR_TRAYECTO_EXCEL(identificacion, placa, codigoRuta, ubicacion, ordenParada,
                    latitud, longitud, loginRegistro, idCargue);
            logger.info("Se carga registro de excel a tabla temporal de forma exitosa");
            return true;
        } catch (Exception e) {
            logger.error("ERROR CARGAR DATOS DE ARCHIVO EXCEL: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validarDatosExcel(Double idCargue) {
        try {
            trayectoTmpRepository.VALIDAR_TRAYECTO_CARGUE(idCargue);
            logger.info("Validacion de datos cargados exitosa");
            return true;
        } catch (Exception e) {
            logger.error("ERROR VALIDANDO DATOS DE ARCHIVO EXCEL: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean procesarDatosExcel(Double idCargue) {
        try {
            trayectoTmpRepository.PROCESAR_TRAYECTO_EXCEL(idCargue);
            logger.info("Procesado de datos validados exitoso");
            return true;
        } catch (Exception e) {
            logger.error("ERROR PROCESAR DATOS DE ARCHIVO EXCEL: {}", e.getMessage());
            return false;
        }
    }

    public List<TrayectoTmp> consultarCargue(Double idCargue) {
        return trayectoTmpRepository.findByIdCargueOrderByIdAsc(idCargue);
    }

    private String texto(DataFormatter formatter, Row row, int columna) {
        Cell cell = row.getCell(columna);
        if (cell == null) {
            return null;
        }
        return formatter.formatCellValue(cell);
    }

    private boolean filaVacia(String... valores) {
        for (String valor : valores) {
            if (valor != null && !valor.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
