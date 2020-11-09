package com.mx.axeleratum.americantower.contract.origin.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.core.model.ContractLand;

@RunWith(SpringRunner.class)
@SpringBootTest
//@DataMongoTest
@Slf4j
public class ContractLandRepositoryTest {

    @Autowired
    ContractLandRepository repository;

    @Test
    public void testAddContract() {
        repository.deleteAll();
        ContractLand contractLand = new ContractLand();
        contractLand.setArrendadorId("1234");
        contractLand.setCeded(Boolean.TRUE);
        contractLand.setDireccionSitioDefinitiva("direccion xxx");
        contractLand = repository.save(contractLand);
        log.info("Contract creado " + contractLand);
    }
}
