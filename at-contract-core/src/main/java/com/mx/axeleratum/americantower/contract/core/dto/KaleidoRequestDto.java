package com.mx.axeleratum.americantower.contract.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KaleidoRequestDto {

    @JsonProperty(value="comment")
    private String comment;
    @JsonProperty(value="fecha")
    private String fecha;
    @JsonProperty(value="id")
    private String id;
    @JsonProperty(value="keyStatus")
    private String keyStatus;
    @JsonProperty(value="status")
    private String status;
    @JsonProperty(value="user")
    private String user;
}



