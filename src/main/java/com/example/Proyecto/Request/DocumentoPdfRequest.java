package com.example.Proyecto.Request;

public class DocumentoPdfRequest {
    private int vehiculoDocumentoId;
    private String pdfBase64;

    public int getVehiculoDocumentoId() {
        return vehiculoDocumentoId;
    }

    public void setVehiculoDocumentoId(int vehiculoDocumentoId) {
        this.vehiculoDocumentoId = vehiculoDocumentoId;
    }

    public String getPdfBase64() {
        return pdfBase64;
    }

    public void setPdfBase64(String pdfBase64) {
        this.pdfBase64 = pdfBase64;
    }
}
