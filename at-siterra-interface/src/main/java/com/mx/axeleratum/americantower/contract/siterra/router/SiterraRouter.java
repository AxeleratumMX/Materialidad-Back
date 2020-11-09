package com.mx.axeleratum.americantower.contract.siterra.router;

import com.mx.axeleratum.americantower.contract.siterra.service.SiterraService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SiterraRouter extends RouteBuilder {

    @Autowired
    private SiterraService siterraService;

    @Bean(name = "csv")
    public CsvDataFormat csv() {
        CsvDataFormat csv = new CsvDataFormat();
        csv.setDelimiter(',');
        csv.setUseMaps(true);
        return csv;
    }


    @Override
    public void configure() throws Exception {

        from("ftp://{{ftp.from.host}}:{{ftp.from.port}}/{{ftp.from.path}}?username={{ftp.from.username}}&password={{ftp.from.password}}")
                .id("ftpSiterra")
                .unmarshal("csv")
                .bean(siterraService,"convertToSiterra")
                .bean(siterraService,"addSiterra")
                .bean(siterraService,"debug");

    }
}
