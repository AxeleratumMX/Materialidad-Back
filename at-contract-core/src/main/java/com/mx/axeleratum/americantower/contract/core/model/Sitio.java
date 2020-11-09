package com.mx.axeleratum.americantower.contract.core.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Sitio {
    @Id
    String id;
    Long idActivo;
    String nombreSitio;
    String direccionSitio;
    String tipoSitio;
    String latitudSitio;
    String longitudSitio;
    String tipoTorre;
    String alturaTorre;
}
