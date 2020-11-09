package com.mx.axeleratum.americantower.contract.client.service;

import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import com.mx.axeleratum.americantower.contract.core.repository.ContactRepository;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Client;
import com.mx.axeleratum.americantower.contract.core.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ContactRepository contactRepository;

	public Client findById(String id) {
		Optional<Client> opClient = clientRepository.findById(id);
		if (opClient.isPresent()) {
			return opClient.get();
		} else {
			throw new BussinessServiceException("Client no found");
		}
	}

	public Page<Client> findAll(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}

	public Client addClient(Client client) {
		List<Contact> contacts = client.getContactos();
		client.setContactos(null);
		final Client client1 = clientRepository.save(client);
		if(Objects.nonNull(contacts)) {
			contacts.stream().forEach(
					t -> {
						contactRepository.save(t);
					}
			);
		}
		client.setContactos(contacts);
		client = clientRepository.save(client);
		log.info("client created " + client);
		return client;
	}

	public void addContacts(Client client) {
		List<Contact> contacts = client.getContactos();
		client.setContactos(null);
		List<Contact> contacts1 = new ArrayList<>();

		if(Objects.nonNull(contacts)) {
			contacts.stream().forEach(
					contact -> {
						Optional<Contact> oContact = contactRepository.findById(contact.getId());
						oContact.ifPresent( (t) ->
								contacts1.add(t)
						);}
					);

			};
		Optional<Client> oClient1 = clientRepository.findById(client.getId());
		oClient1.ifPresent( c -> {
					c.setContactos(contacts1);
					clientRepository.save(c);
				}
		);

	}



	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	public Page<Client> addAllClients(List<Client> clients) {
		clients = clientRepository.saveAll(clients);
		log.info("Clients created " + clients);
		Page<Client> page = new PageImpl<>(clients);
		return page;
	}

	public void deleteById(String id) {
		clientRepository.deleteById(id);
	}

	public void deleteAll() {
		clientRepository.deleteAll();
	}

	public List<Client> findAllByNombreLike(String search) {
		return clientRepository.findAllByNombreLike(search);
	}
}
