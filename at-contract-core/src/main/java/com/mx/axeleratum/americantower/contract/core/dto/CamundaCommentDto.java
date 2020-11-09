package com.mx.axeleratum.americantower.contract.core.dto;

import lombok.Data;

@Data
public class CamundaCommentDto {
    private String message;

    public CamundaCommentDto(String message) {
        this.message = message;
    }
}




