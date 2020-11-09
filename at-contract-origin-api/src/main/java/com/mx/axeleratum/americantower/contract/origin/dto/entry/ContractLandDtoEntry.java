package com.mx.axeleratum.americantower.contract.origin.dto.entry;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class ContractLandDtoEntry {
    // ISO-8601 format
    public final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private String templateName;
    private String abstractType;
    private String leaseType;
    private String customer;
    private String acuerdo;
    private String subAcuerdo;
    private int towerNumber;
    private String folOperations;
    private int idCliente;
    private String direccionSitioDefinitiva;
    private String tipoDocumento;
    private String idContrato;
    private boolean beneficiaryRegistrationContractRecord;
    private boolean onlyBeneficiaryRegistration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaInicioConstruccion;
    private String arrendadorId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaFirmaContrato;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaInicioVigencia;
    private int aniosVigencia;
    private int mesesVigencia;
    private int diasVigencia;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaTerminoVigencia;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaInicioRenta;
    private String terminosRenovacion;
    private int numeroRenovaciones;
    private int aniosRenovacion;
    private int mesesRenovacion;
    private int diasRenovacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaTerminoRenovacion;
    private String frecuenciaFacturacion;
    private String tipoMoneda;
    private BigDecimal renta;
    private BigDecimal rentaTerreno;
    private String tipoIncremento;
    private BigDecimal depositoGarantia;
    private int factorIncrementoDepositoGarantia;
    private Date fechaDepositoGarantia;
    private int numeroMesesApicacionGarantia;
    private BigDecimal costoApertura;
    private BigDecimal penalidad;
    private BigDecimal cargoMantenimiento;
    private BigDecimal cargoServicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaPrimerPagoServicio;
    private String frecuenciaPagoCargoServicio;
    private String lineaRecta;
    private String duenioSitio;
    private boolean ceded;
    private boolean das;
    private BigDecimal montoGRRIAF;
    private boolean sitioAdquirido;
    private String exitAgreement;
    private String penalidadContractual;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaPenalidad;
    private int diasNotificacionRenovar;
    private int diasNotificacionVencimiento;
    private String comentarios;
    private String status;
}