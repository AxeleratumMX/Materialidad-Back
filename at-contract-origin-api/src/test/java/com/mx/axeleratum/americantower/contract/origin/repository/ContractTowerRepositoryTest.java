package com.mx.axeleratum.americantower.contract.origin.repository;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@DataMongoTest
@Slf4j
public class ContractTowerRepositoryTest {

    @Autowired
    ContractTowerRepository contractTowerRepository;

    @Test
    public void testLogger() {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
    }
    
/*
    @Test
    public void testAddContract() {
        contractTowerRepository.deleteAll();
        ContractTower contractTower = new ContractTower();
        contractTower.setIdCliente(1233);
        contractTower.setFechaIncremento(new Date(System.currentTimeMillis()));
        List contacts = contactRepository.findByName("Juan Perez 2");
        if (contacts != null ) {
            contractTower.setContact((Contact) contacts.get(0));
        }
        Equipment equipment1 = new Equipment();
        equipment1.setAltura(1);
        equipment1.setMarca("Sony");
        equipment1.setModelo("xx21");

        Equipment equipment2 = new Equipment();
        equipment2.setAltura(2);
        equipment2.setMarca("Apple");
        equipment2.setModelo("iphone");
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment1);
        equipmentList.add(equipment2);
        contractTower.setEquipmentList(equipmentList);
        contractTower = contractTowerRepository.save(contractTower);


        java.util.Optional<ContractTower> aOptional = contractTowerRepository.findById(contractTower.getId());
        if (aOptional.isPresent() ) {
            contractTower = aOptional.get();
            log.info(aOptional.get().toString());
        }

        contractTower.getEquipmentList().remove(contractTower.getEquipmentList().get(1));
        contractTower.setTipoContrato("Custom GAF");
        contractTowerRepository.save(contractTower);

        log.info("Contract creado " + contractTower);
    }*/
}
