package com.mx.axeleratum.americantower.contract.core.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Document(collection = "contractsLand")
public class ContractLand extends ContractBase{

    private Integer towerNumber;
    private String folOperation;
    private String direccionSitioDefinitiva;
    private Boolean beneficiaryRegistrationContractRecord;
    private Boolean onlyBeneficiaryRegistration;
    private Date fechaInicioConstruccion;
    private Date fechaInicioTerreno;
    private String arrendadorId;
    private BigDecimal rentaTerreno;
    private String tipoIncremento;
    private BigDecimal depositoGarantia;
    private Integer factorIncrementoDepositoGarantia;
    private Date fechaDepositoGarantia;
    private Integer numeroMesesApicacionGarantia;
    private BigDecimal costoApertura;
    private BigDecimal penalidad;
    private BigDecimal cargoMantenimiento;
    private BigDecimal cargoServicio;
    private Date fechaPrimerPagoServicio;
    private String frecuenciaPagoCargoServicio;
    private String lineaRecta;
    private String duenioSitio;
    private Boolean ceded;
    private Boolean das;
    private BigDecimal montoGRRIAF;
    private Boolean sitioAdquirido;
    private String exitAgreement;
    private String penalidadContractual;
    private Date fechaPenalidad;
    private Contact duenio;


}
