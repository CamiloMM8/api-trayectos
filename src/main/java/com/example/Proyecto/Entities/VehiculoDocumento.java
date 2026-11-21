package com.example.Proyecto.Entities;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import com.example.Proyecto.Enums.EstadoVehiculoDocumento;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VehiculoDocumento", schema = "PROYECTO")
public class VehiculoDocumento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @JsonBackReference("vehiculo-documentos")
    @ManyToOne
    @JoinColumn(name = "VehiculoID", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "DocumentoID", nullable = false)
    private Documento documento;

    @JsonProperty("fechaExpedicion")
    @JsonAlias("FechaExpedicion")
    @Column(name = "FechaExpedicion", nullable = false)
    private LocalDate fechaExpedicion;

    @JsonProperty("fechaVencimiento")
    @JsonAlias("FechaVencimiento")
    @Column(name = "FechaVencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @JsonProperty("estado")
    @JsonAlias("Estado")
    @Column(name = "Estado", nullable = false, length = 20)
    private EstadoVehiculoDocumento estado;

    @Column(name = "DocumentoPdfBase64", columnDefinition = "LONGBLOB")
    private byte[] documentoPdfBase64;

    public VehiculoDocumento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoVehiculoDocumento getEstado() {
        return estado;
    }

    public void setEstado(EstadoVehiculoDocumento estado) {
        this.estado = estado;
    }

    @JsonProperty("documentoPdfBase64")
    @JsonAlias("DocumentoPdfBase64")
    public String getDocumentoPdfBase64() {
        if (documentoPdfBase64 == null) {
            return null;
        }
        return new String(documentoPdfBase64, StandardCharsets.US_ASCII);
    }

    @JsonProperty("documentoPdfBase64")
    @JsonAlias("DocumentoPdfBase64")
    public void setDocumentoPdfBase64(String documentoPdfBase64) {
        this.documentoPdfBase64 = documentoPdfBase64 == null
                ? null
                : documentoPdfBase64.getBytes(StandardCharsets.US_ASCII);
    }
}
