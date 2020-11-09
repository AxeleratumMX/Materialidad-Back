package com.mx.axeleratum.americantower.contract.dynamicInterface.mapper;

import com.mx.axeleratum.americantower.contract.core.model.Client;
import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class ClientIdToClientName {

    private Map<String,String> cacheNameClientes = new HashMap<>();

    @Autowired
    ClientRepository clientRepository;

    @Named("clientIdToClientName")
    public String toName(String id) {
        String name = "";
        if (Objects.nonNull(id)) {
            if (Objects.nonNull(cacheNameClientes.get(id))) {
                name = cacheNameClientes.get(id);
            } else
            {
                Optional<Client> oClient = clientRepository.findById(id);
                if ( oClient.isPresent() ) {
                    name = oClient.get().getNombre();
                    cacheNameClientes.put(oClient.get().getId(),oClient.get().getNombre());
                }

            }
        }
        return name;
    }
}
