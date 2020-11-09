package com.mx.axeleratum.americantower.contract.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseObject {
    public static final String STATUS_RESPONSE_OK = "success";
    public static final String STATUS_RESPONSE_ERROR = "failure";


    @ApiModelProperty(
            notes = "Reponse Object",
            example = "JSON Object"
    )
    @JsonProperty("data")
    Object data;
    @ApiModelProperty(
            notes = "Response Status",
            example = "success"
    )
    @JsonProperty("status")
    String status;

    public ResponseObject() {
        this.status = STATUS_RESPONSE_OK;
    }

    public ResponseObject(Object object, String responseStatus) {
        this.data = object;
        this.status = responseStatus;
    }

}