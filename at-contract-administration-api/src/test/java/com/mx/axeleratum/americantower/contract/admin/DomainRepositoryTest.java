package com.mx.axeleratum.americantower.contract.admin;

import com.mx.axeleratum.americantower.contract.admin.dto.DomainDto;
import com.mx.axeleratum.americantower.contract.admin.dto.DomainValueDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.DomainMapper;
import com.mx.axeleratum.americantower.contract.core.repository.DomainRepository;
import com.mx.axeleratum.americantower.contract.core.repository.DomainValueRepository;
import com.mx.axeleratum.americantower.contract.admin.service.DomainService;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Domain;
import com.mx.axeleratum.americantower.contract.core.model.DomainValue;
import com.mx.axeleratum.americantower.contract.core.model.Domains;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.admin.AdministrationApplication.class)
@Slf4j
public class DomainRepositoryTest {

    @Autowired
    DomainRepository domainRepository;


    @Autowired
    DomainValueRepository domainValueRepository;

    @Autowired
    DomainService domainService;

    @Autowired
    DomainMapper domainMapper;


    @Test
    public void testCreateStatusDomain() {
        domainRepository.deleteAll();
        domainValueRepository.deleteAll();
        Domain domain = new Domain();
        domain.setKey(Domains.STATUS);
        domain.setDescription(Domains.STATUS.getDescription());
        domain = domainService.addDomain(domain);

        List<DomainValue> listDomainValues = new ArrayList<>();

        DomainValue domainValue = null;
        domainValue = new DomainValue();
        domainValue.setKey("BORRADOR");
        domainValue.setValue("Borrador");
        domainValue.setOrder(1);
        domainValue.setActive(Boolean.TRUE);
        domainValue.setDomainId(domain.getId());
        domainService.addDomainValue(domain.getId(),domainValue);
        listDomainValues.add(domainValue);

        domainValue = new DomainValue();
        domainValue.setKey("REVISION");
        domainValue.setValue("En Revision");
        domainValue.setOrder(2);
        domainValue.setActive(Boolean.TRUE);
        domainValue.setDomainId(domain.getId());
        domainService.addDomainValue(domain.getId(),domainValue);
        listDomainValues.add(domainValue);

        domainValue = new DomainValue();
        domainValue.setKey("FIRMAR");
        domainValue.setValue("En Firma");
        domainValue.setOrder(3);
        domainValue.setActive(Boolean.TRUE);
        domainValue.setDomainId(domain.getId());
        domainService.addDomainValue(domain.getId(),domainValue);
        listDomainValues.add(domainValue);

        domainValue = new DomainValue();
        domainValue.setKey("ACTIVO");
        domainValue.setValue("Activo");
        domainValue.setOrder(4);
        domainValue.setActive(Boolean.TRUE);
        domainValue.setDomainId(domain.getId());
        domainService.addDomainValue(domain.getId(),domainValue);
        listDomainValues.add(domainValue);

        domainValue = new DomainValue();
        domainValue.setKey("CANCELADO");
        domainValue.setValue("Cancelado");
        domainValue.setOrder(5);
        domainValue.setActive(Boolean.TRUE);
        domainValue.setDomainId(domain.getId());
        domainService.addDomainValue(domain.getId(),domainValue);
        listDomainValues.add(domainValue);

        domain.setDomainValues(listDomainValues);

        domainRepository.save(domain);
        log.info("Termine");

    }

    @Test
    public void testFindStatusDomain() {
        Domain domain = domainService.findDomain("STATUS");
        DomainDto domainDto = domainMapper.toDomainDto(domain);
        log.info("dominio leido "+ domainDto);
    }

    @Test
    public void testDeleteDomain() {
        domainService.deleteDomain(Domains.STATUS.getValue());
        log.info("Finish");
    }

    @Test
    public void testDomainDtoToDomain() {
        DomainDto domainDto = new DomainDto();
        domainDto.setName("STATUS");
        domainDto.setDescription("ddweddwedwedew");
        List<DomainValueDto> domainDtoList = new ArrayList<>();
        DomainValueDto domainValueDto = null;

        domainValueDto = new DomainValueDto();
        domainValueDto.setKey("BORRADOR");
        domainValueDto.setValue("Borrador");
        domainValueDto.setOrder(1);
        domainDtoList.add(domainValueDto);

        domainValueDto = new DomainValueDto();
        domainValueDto.setKey("REVISION");
        domainValueDto.setValue("En Revision");
        domainValueDto.setOrder(2);
        domainDtoList.add(domainValueDto);

        domainValueDto = new DomainValueDto();
        domainValueDto.setKey("FIRMA");
        domainValueDto.setValue("En Firma");
        domainValueDto.setOrder(3);
        domainDtoList.add(domainValueDto);

        domainDto.setDomainValues(domainDtoList);

        @SuppressWarnings("unused")
		Domain domain = domainMapper.toDomain(domainDto);
        log.info("finish");

    }

    @Test
    public void  testAddDomainFromDomainDTO() {
        try {
            domainService.deleteDomain(Domains.STATUS.getValue());
        } catch (BussinessServiceException ex) {
            //caso en que estamos borrando un dominio que ya no esta
            log.info("Error " + ex.getMessage());
        }

        DomainDto domainDto = new DomainDto();
        domainDto.setName("STATUS");
        domainDto.setDescription("ddweddwedwedew");
        List<DomainValueDto> domainDtoList = new ArrayList<>();
        DomainValueDto domainValueDto = null;

        domainValueDto = new DomainValueDto();
        domainValueDto.setKey("BORRADOR");
        domainValueDto.setValue("Borrador");
        domainValueDto.setOrder(1);
        domainDtoList.add(domainValueDto);

        domainValueDto = new DomainValueDto();
        domainValueDto.setKey("REVISION");
        domainValueDto.setValue("En Revision");
        domainValueDto.setOrder(2);
        domainDtoList.add(domainValueDto);

        domainValueDto = new DomainValueDto();
        domainValueDto.setKey("FIRMA");
        domainValueDto.setValue("En Firma");
        domainValueDto.setOrder(3);
        domainDtoList.add(domainValueDto);

        domainDto.setDomainValues(domainDtoList);

        Domain domain = domainMapper.toDomain(domainDto);
        domainService.addDomain(domain);
    }


}