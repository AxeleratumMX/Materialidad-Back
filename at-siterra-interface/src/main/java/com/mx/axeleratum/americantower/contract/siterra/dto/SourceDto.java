package com.mx.axeleratum.americantower.contract.siterra.dto;

import java.util.Map;

public class SourceDto {
    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public SourceDto(Map<String, String> map) {
        this.map = map;
    }
}
