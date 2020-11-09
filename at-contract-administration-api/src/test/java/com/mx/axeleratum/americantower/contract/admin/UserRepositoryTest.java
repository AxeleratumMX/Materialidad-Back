package com.mx.axeleratum.americantower.contract.admin;

import com.mx.axeleratum.americantower.contract.core.model.User;
import com.mx.axeleratum.americantower.contract.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void testAddUsers() {
//        repository.deleteAll();
        User user = null;
        user = new User();
        user.setUsername("userCreador");
        user.setRole("ContractRole");
        repository.save(user);
        user = new User();
        user.setUsername("userRevisor");
        user.setRole("ContractRole");
        repository.save(user);
        user = new User();
        user.setUsername("userAbogadoRevisor");
        user.setRole("ContractRole");
        repository.save(user);
        user = new User();
        user.setUsername("userAbogadoFirma");
        user.setRole("ContractRole");
        repository.save(user);
        user = new User();
        user.setUsername("userFirma");
        user.setRole("ContractRole");
        repository.save(user);

        log.info("Contract creados " );
    }

    @Test
    public void addUsers() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();
/*
        user.setUsername("gforrade");
        user.setPassword(bCryptPasswordEncoder.encode("welcome1"));
        user = repository.insert(user);
*/
        log.info("User creado " + user);
        user = new User();
        user.setUsername("userRevisor");
        user.setPassword(bCryptPasswordEncoder.encode("welcome1"));
        user.setName("User Revisor");
        user.setRole("RevisorRole");
        user = repository.insert(user);
        log.info("User creado " + user);

        user = new User();
        user.setUsername("userRevisor2");
        user.setPassword(bCryptPasswordEncoder.encode("welcome1"));
        user.setName("User Revisor2");
        user.setRole("RevisorRole");
        user = repository.insert(user);
        log.info("User creado " + user);

        user = new User();
        user.setUsername("userAbogadoCreador");
        user.setPassword(bCryptPasswordEncoder.encode("welcome1"));
        user.setRole("CreadorRole");
        user.setName("User Abogado Creador");
        user = repository.insert(user);
        log.info("User creado " + user);

        user = new User();
        user.setUsername("userFirmante");
        user.setPassword(bCryptPasswordEncoder.encode("welcome1"));
        user.setRole("FirmanteRole");
        user.setName("User Firmante");
        user = repository.insert(user);
        log.info("User creado " + user);

        user = new User();
        user.setUsername("userFirmante2");
        user.setRole("FirmanteRole");
        user.setPassword(bCryptPasswordEncoder.encode("welcome1"));
        user.setName("User Firmante2");
        user = repository.insert(user);
        log.info("User creado " + user);


    }
}