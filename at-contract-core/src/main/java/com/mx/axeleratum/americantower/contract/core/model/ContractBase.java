package com.mx.axeleratum.americantower.contract.core.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class ContractBase {

    @Id
    private String id;
    private Integer version;
    private String templateName;
    private String abstractType;
    private String leaseType;
    private String customer;
    private String acuerdo;
    private String subAcuerdo;
    private String idContrato;
    private Integer idCliente;
    private Date fechaFirmaContrato;
    private Date fechaInicioVigencia;
    private Integer aniosVigencia;
    private Integer mesesVigencia;
    private Integer diasVigencia;
    private Date fechaTerminoVigencia;
    private Date fechaInicioRenta;
    private String terminosRenovacion;
    private Integer numeroRenovaciones;
    private Integer aniosRenovacion;
    private Integer mesesRenovacion;
    private Integer diasRenovacion;
    private Date fechaTerminoRenovacion;
    private String frecuenciaFacturacion;
    private String tipoMoneda;
    private BigDecimal renta;
    private String folOperations;
    private Integer diasNotificacionRenovar;
    private Integer diasNotificacionVencimiento;
    private String comentarios;
    private String status;
    private List<Contact> contactos;
    private List<Contact> firmantes;
    @CreatedDate
    private Date createdDate;
    private String documentSource;

}
