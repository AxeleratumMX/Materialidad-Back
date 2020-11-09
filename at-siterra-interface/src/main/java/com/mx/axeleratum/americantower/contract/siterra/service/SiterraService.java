package com.mx.axeleratum.americantower.contract.siterra.service;

import com.mx.axeleratum.americantower.contract.core.model.SiTerra;
import com.mx.axeleratum.americantower.contract.siterra.dto.SourceDto;
import com.mx.axeleratum.americantower.contract.siterra.mapper.MapToSiterraMapper;
import com.mx.axeleratum.americantower.contract.siterra.repository.SiterraRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SiterraService {

    @Autowired
    SiterraRepository siterraRepository;

    public void debug(Exchange exchange) {
        log.info("debug");
        log.info(exchange.getIn().getBody().toString());
    }
    public void convertToSiterra(Exchange exchange) {
        @SuppressWarnings("unchecked")
		List<Map<String,String>> list = exchange.getIn().getBody(List.class);
        SourceDto sourceDto = new SourceDto(list.get(0));
        SiTerra siterra = MapToSiterraMapper.MAPPER.toTarget(sourceDto);
        exchange.getIn().setBody(siterra);
    }

    public void addSiterra(SiTerra siterra) {
        siterraRepository.save(siterra);
    }
}
