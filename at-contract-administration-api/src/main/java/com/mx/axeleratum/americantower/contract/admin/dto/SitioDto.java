package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class SitioDto {
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
