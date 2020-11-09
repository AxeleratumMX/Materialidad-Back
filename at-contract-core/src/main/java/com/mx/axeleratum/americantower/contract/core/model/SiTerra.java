package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "siterras")
public class SiTerra {
    @Id
    private String id;
    private String idCliente;
    private Integer idContrato;
    private Integer siteNumber;
    private Integer towerNumber;
    private Integer idSiterra;
    private String siteName;
    private String BTSProjectName;
    private String portfolioCountry;
    private String groundLeaseFeasibility;
    private String groundLeaseComments;
    private String groundLeaseRisk;
    private String contractDurationYears;
    private String currentNegotiatedGroundRentalAmount;
    private String currentRentalAmountUOM;
    private String specialClauses;
    private String paymentFrequency;
    private String renewal;
    private String siteAddress;
    private String siteState;
    private String sitePostalCode;
    private String siteTown;
    private String siteCity;

    //datos equipos
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
    private BigDecimal renta;
    private Boolean equipoAdicional;


    private Date fechaInicioConstruccion;
    private String arrendadorId;
    private Date fechaFirmaContrato;
    private Date fechaInicioVigencia;
    private Integer aniosVigencia;
    private Integer mesesVigencia;
    private Integer diasVigencia;
    private Date fechaTerminoVigencia;
    private Date fechaInicioRenta;
    private Date fechaInicioTerreno;
    private String folSalesForce;
    private String folOperation;



}
