package com.mx.axeleratum.americantower.contract.client.repository;

import com.mx.axeleratum.americantower.contract.core.model.Client;
import com.mx.axeleratum.americantower.contract.core.model.Contact;
import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.client.ClientApplication.class)
@Slf4j
public class ClientRepositoryTest extends TestCase{
    @Autowired
    private ClientRepository repository;

    public String getStrId() {
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		String generatedString = new String(Base64.getEncoder().encode(array));
		return generatedString;
	}
	
	public Integer getRandomInt(int n) {
	  int i = new Random().nextInt(n);
	  return (i<0)?(i*-1):i;
	}
	
	public Long getRandomLong() {
		  Long i = new Random().nextLong();
		  return (i<0L)?(i*-1L):i;
	}

    @Test
    public void testAddClient() {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        Client client = new Client();
        client.setColonia("colonia "+getRandomLong());
        client.setApellidoMaterno("Apellido materno"+getRandomLong());
        client.setApellidoPaterno("Apellido paterno"+getRandomLong());
        client.setNombre("Nombre "+getRandomLong());
        client.setDescription("dewddewdweew");
        List<Contact> contactList = new ArrayList<>();
        Contact contact = null;
        contact = new Contact();
        contact.setId("5efe1920aa4bdc4db443acf0");
        contactList.add(contact);
        contact = new Contact();
        contact.setId("5efe1920aa4bdc4db443acf1");
        contactList.add(contact);
        client.setContactos(contactList);
        client = repository.save(client);
        Optional<Client> o  = repository.findById(client.getId());
        assertTrue(o.isPresent());
    }

    @Test
    public void testFindByIdClient() {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        Optional<Client> o = repository.findById("5f04cd9fdf96be0727c84c9f");
        assertTrue(o.isPresent());
    }
    
    @Test
    public void testFindByNameLike() {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
    	List<Client> clients = repository.findAllByNombreLike("312111");
    	log.info(clients+"");
    }
}
