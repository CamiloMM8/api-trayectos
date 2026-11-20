package com.example.Proyecto.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IExcelService {

    Double processExcel(MultipartFile file);

    boolean cargarDatosExcel(String identificacion, String placa, String codigoRuta, String ubicacion,
            String ordenParada, String latitud, String longitud, String loginRegistro, Double idCargue);

    boolean validarDatosExcel(Double idCargue);

    boolean procesarDatosExcel(Double idCargue);
}
