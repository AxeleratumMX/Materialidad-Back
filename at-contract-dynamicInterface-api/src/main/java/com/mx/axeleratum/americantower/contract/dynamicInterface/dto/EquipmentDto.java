package com.mx.axeleratum.americantower.contract.dynamicInterface.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentDto {
    private String id;
    private String tipoAntena;
    private String tipoLinea;
    private String orientacion;
    private String modelo;
    private Integer azimut;
    private String tipoMontaje;
    private String marca;
    private Integer banda;
    private Integer diametro;
    private Integer frecuencia;
    private Integer peso;
    private Integer altura;
    private BigDecimal rentaEquipo;
    private Boolean equipoAdicional;
    @JsonIgnore
    private String idContrato;
    @JsonIgnore
    private String folOperations;
}
