package com.mx.axeleratum.americantower.contract.core.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KaleidoHeaderDto {

    private String id;
    private String type;
    private String timeReceived;
    private BigDecimal timeElapsed;
    private String requestOffset;
    private String requestId;
}

