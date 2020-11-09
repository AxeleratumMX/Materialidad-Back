package com.mx.axeleratum.americantower.contract.core.model;

public enum Domains {
    STATUS("Status","Estados del contrato"),
    ABSTRACT_TYPE("AbstractType","Tipos de contratos"),
    TIPO_DOCUMENTO("TipoDocumento","Tipo de Documento"),
    TERMINO_RENOVACION("TerminoRenovacion","Termino de Renovacion"),
    FRECUENCIA_FACTURACION("FrecuenciaFacturacion","Frecuencia de Facturacion"),
    TIPO_MONEDA("TipoMoneda","Tipo de Moneda"),
    TIPO_INCREMENTO("TipoIncremento","Tipo de Incremento"),
    TIPO_INDICE("TipoIndice","Tipo de Indice"),
    TIPO_CLIENTE("TipoCliente","Tipo de Cliente"),
    TIPO_PERSONA("TipoPersona","Tipo de Persona"),
    TIPO_ACTIVO("TipoActivo","Tipo de Activo"),
    CONTRATO("TipoContrato","Tipo de Contrato"),
    SUB_CONTRATO("SubTipoContrato","Subtipo de Contrato"),
    ACUERDO("TipoAcuerdo","Tipo de Acuerdo"),
    LEASE_TYPE("LeaseType","Lease Type"),
    SUB_ACUERDO("SubTipoAcuerdo","Subtipo de Acuerdo"),
    TIME_PERIOD("TimePeriod","Periodo de tiempo"),
    TIPO_SITIO("TipoSitio","Tipos de sitios"),
    TIPO_TORRE("TipoTorre","Tipo de Torres"),
    TIPO_ANTENA_EQUIPO("TipoAntenaEquipo","Tipos de antenas de los equipos");

    private final String value;
    private final String description;

    Domains(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}



