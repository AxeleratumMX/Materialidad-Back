package com.mx.axeleratum.americantower.contract.core.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "contractsTower")
public class ContractTower extends ContractBase{

    private Integer idActivo;
    private String arrendadorId;
    private String tipoContrato;
    private String subTipoContrato;
    private String tipoDocumento;
    @DBRef
    private Contact contact;
    private String folSalesForce;
    private String direccionSitioDefinitiva;
    private String sitioAdquirido;
    private Date fechaAdquisicion;
    private BigDecimal rentaEquipoAdicional;
    private BigDecimal rentaTotalTorre;
    private Boolean condicionadoNtpCoc;
    private Boolean colocacionDas;
    private Boolean rentaAdicionalTerreno;
    private Date fechaInicioTerreno;
    private BigDecimal rentaTerreno;
    private BigDecimal rentaEspacioAdicional;
    private BigDecimal rentaTotalTerreno;
    private Boolean cancelable;
    private String tipoIncremento;
    private Integer porcentajeIncremento;
    private Date fechaIncremento;
    private Integer porcentajeMinimo;
    private Integer porcentajeMaximo;
    private String tipoIndice;
    private Integer cpi;
    private  Boolean aplicaColloCredit;
    private BigDecimal montoColloCredit;
    private Integer porcentajeGanancia;
    private BigDecimal montoReduccion;
    private BigDecimal totalRentaConReduccion;
    private List<Equipment> equipmentList;
    private String latitud;
    private String longitud;



}
