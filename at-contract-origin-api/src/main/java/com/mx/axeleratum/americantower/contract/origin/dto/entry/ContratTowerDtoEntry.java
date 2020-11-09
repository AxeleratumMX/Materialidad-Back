package com.mx.axeleratum.americantower.contract.origin.dto.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContratTowerDtoEntry {
    // ISO-8601 format
    public final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private String templateName;
    private String leaseType;
    private String customer;
    private String agreement;
    private int assetNumber;
    private String arrendador;
    private String tipoContrato;
    private String subTipoContrato;
    private String acuerdo;
    private String subAcuerdo;
    private String tipoDocumento;
    private int idCliente;
    private String folSalesForce;
    private String folOperations;
    private String direccionSitioDefinitiva;
    private String contacto;
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
    private String sitioAdquirido;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaAdquisicion;
    private int numeroRenovaciones;
    private int aniosRenovacion;
    private int mesesRenovacion;
    private int diasRenovacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaTerminoRenovacion;
    private String frecuenciaFacturacion;
    private String tipoMoneda;
    private BigDecimal renta;
    private BigDecimal rentaEquipoAdicional;
    private BigDecimal rentaTotalTorre;
    private boolean condicionadoNtpCoc;
    private boolean colocacionDas;
    private boolean rentaAdicionalTerreno;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaInicioTerreno;
    private BigDecimal rentaTerreno;
    private BigDecimal rentaEspacioAdicional;
    private BigDecimal rentaTotalTerreno;
    private boolean cancelable;
    private String tipoIncremento;
    private int porcentajeIncremento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date fechaIncremento;
    private int porcentajeMinimo;
    private int porcentajeMaximo;
    private String tipoIndice;
    private int cpi;
    private int diasNotificacionRenovacion;
    private int diasNotificacionTerminacion;
    private  boolean aplicaColloCredit;
    private BigDecimal montoColloCredit;
    private int porcentajeGanancia;
    private BigDecimal montoReduccion;
    private BigDecimal totalRentaConReduccion;
    private String comentarios;
    private String status;

}
