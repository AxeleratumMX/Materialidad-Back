package com.mx.axeleratum.americantower.contract.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KaleidoResponseDto {

    @JsonProperty(value = "headers")
    private KaleidoHeaderDto headers;
    private String blockHash;
    private String blockNumber;
    private String from;
    private String gasUsed;
    private String cumulativeGasUsed;
    private String nonce;
    private String status;
    private String to;
    private String transactionHash;
    private String transactionIndex;

}


