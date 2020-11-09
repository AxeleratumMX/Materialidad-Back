package com.mx.axeleratum.americantower.contract.client.repository;

import com.mx.axeleratum.americantower.contract.core.model.Contact;
import com.mx.axeleratum.americantower.contract.core.model.User;
import com.mx.axeleratum.americantower.contract.core.repository.ContactRepository;
import com.mx.axeleratum.americantower.contract.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ContactRepositoryTest {

    @Autowired
    ContactRepository repository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testAddContract() {

        User userCreador =userRepository.findByUsername("userCreador");
        User userRevisor =userRepository.findByUsername("userRevisor");
        User userAbogadoRevisor =userRepository.findByUsername("userAbogadoRevisor");
        User userAbogadoFirma =userRepository.findByUsername("userAbogadoFirma");
        User userFirma =userRepository.findByUsername("userFirma");

        repository.deleteAll();
        Contact contact = new Contact();
        //contact 1
        contact.setEmail("juan.perez-1@gmail.com");
        contact.setName("Juan Creador");
        contact.setApellidoMaterno("Apellido Materno Creador");
        contact.setApellidoMaterno("Apellido Paterno Creador");
        contact.setTelefono("+5426411122234");
        contact.setUser(userCreador);
        repository.save(contact);
        log.info("Contact creado " + contact);

        //contact 1
        contact = new Contact();
        contact.setEmail("juan.perez-2@gmail.com");
        contact.setName("Juan Revisor");
        contact.setApellidoMaterno("Apellido Materno Revisor");
        contact.setApellidoMaterno("Apellido Paterno Revisor");
        contact.setTelefono("+542642223334");
        contact.setUser(userRevisor);
        repository.save(contact);
        log.info("Contact creado " + contact);

        //contact 1
        contact = new Contact();
        contact.setEmail("juan.perez-3@gmail.com");
        contact.setName("Juan Abogado Revisor");
        contact.setApellidoMaterno("Apellido Materno Abogado Revisor");
        contact.setApellidoMaterno("Apellido Paterno Abogado Revisor");
        contact.setTelefono("+542642223334");
        contact.setUser(userAbogadoRevisor);
        repository.save(contact);
        log.info("Contact creado " + contact);

        //contact 1
        contact = new Contact();
        contact.setName("Juan Abogado Firma");
        contact.setTelefono("+542642223334");
        contact.setApellidoMaterno("Apellido Materno Abogado Firma");
        contact.setApellidoMaterno("Apellido Paterno Abogado Firma");
        contact.setUser(userAbogadoFirma);
        repository.save(contact);
        log.info("Contact creado " + contact);

        //contact 1
        contact = new Contact();
        contact.setEmail("juan.perez-5@gmail.com");
        contact.setName("Juan Firma");
        contact.setApellidoMaterno("Apellido Materno Firma");
        contact.setApellidoMaterno("Apellido Paterno Firma");
        contact.setTelefono("+5426411122234");
        contact.setUser(userFirma);
        repository.save(contact);
        log.info("Contract creado " + contact);
    }

    @Test
    public void testFindContactByUser() {
        User user =userRepository.findByUsername("userCreador");
        Contact contact = repository.findByUserId(user.getId());
        log.info("Contract leido " + contact);
    }

    @Test
    public void testSetUserToContact() {
        User userCreador =userRepository.findByUsername("userCreador");
        User userRevisor =userRepository.findByUsername("userRevisor");
        User userAbogadoRevisor =userRepository.findByUsername("userAbogadoRevisor");
        User userAbogadoFirma =userRepository.findByUsername("userAbogadoFirma");
        User userFirma =userRepository.findByUsername("userFirma");

        Contact contactCcontact1 = repository.findById("5f166b855ed56d41a0225024").get();
        contactCcontact1.setUser(userRevisor);
        repository.save(contactCcontact1);

        Contact contactCcontact2 = repository.findById("5f166b855ed56d41a0225025").get();
        contactCcontact2.setUser(userAbogadoRevisor);
        repository.save(contactCcontact2);

        Contact contactFirmante = repository.findById("5f166b865ed56d41a0225026").get();
        contactFirmante.setUser(userAbogadoFirma);
        repository.save(contactFirmante);

        Contact contactFirmante2= repository.findById("5f166b865ed56d41a0225027").get();
        contactFirmante2.setUser(userFirma);
        repository.save(contactFirmante2);


    }

}