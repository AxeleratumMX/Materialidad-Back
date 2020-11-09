package com.mx.axeleratum.americantower.contract.admin.service;

import com.mx.axeleratum.americantower.contract.core.repository.DomainRepository;
import com.mx.axeleratum.americantower.contract.core.repository.DomainValueRepository;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Domain;
import com.mx.axeleratum.americantower.contract.core.model.DomainValue;
import com.mx.axeleratum.americantower.contract.core.model.Domains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private DomainValueRepository domainValueRepository;


    public Domain findDomain(String domainKey) {
        checkDomainExist(domainKey);
        return domainRepository.findByKey(Domains.valueOf(domainKey));
    }

    public List<DomainValue> findSubDomain(String domainKey, String subDomainKey) {
        checkDomainExist(domainKey);
        Domain domain = domainRepository.findByKey(Domains.valueOf(domainKey));
        if (Objects.nonNull(domain)) {
            return  domainValueRepository.findByDomainIdAndSubDomainValue(domain.getId(),subDomainKey);
        } else throw new BussinessServiceException("No se encontro el dominio " + domainKey );

    }


    public DomainValue findDomainValueByKey(String domainKey,String key) {
        checkDomainExist(domainKey);
        Domain domain = domainRepository.findByKey(Domains.valueOf(domainKey));
        if (Objects.nonNull(domain) ) {
            Optional<DomainValue> optionalDomainValue = domainValueRepository.findByDomainIdAndKey(domain.getId(), key);
            if (optionalDomainValue.isPresent()) {
                return optionalDomainValue.get();
            } else {
                throw new BussinessServiceException("No se encontro el value " + key );
            }

        } else throw new BussinessServiceException("No se encontro el domain  para la domainKey" +  domainKey);

    }

    public Domain addDomain(Domain domain) {
        List<DomainValue> domainValues = domain.getDomainValues();
        //borramos los valores de dominio para evitar el error
        //Cannot create a reference to an object with a NULL id
        //en los domainValues
        domain.setDomainValues(null);
        final Domain domain2 =  domainRepository.save(domain);

        if (Objects.nonNull(domainValues)) {
                domainValues.stream().forEach((t) -> {
                    t.setDomainId(domain2.getId());
                    domainValueRepository.save(t);
                });
        }
        domain.setDomainValues(domainValues);
        domainRepository.save(domain);
        return domain2;

    }


    public void deleteDomain(String domainKey) {
        checkDomainExist(domainKey);
        Domain domain = domainRepository.findByKey(Domains.valueOf(domainKey));

        if (Objects.nonNull(domain) ) {
            domainRepository.delete(domain);
        } else throw new BussinessServiceException("No se encontro el dominio " + domainKey + " a ser borrado.");

        if (Objects.nonNull(domain.getDomainValues())) {
            domain.getDomainValues().stream().forEach(t -> domainValueRepository.deleteById(t.getId()));
            domainRepository.delete(domain);
        }


    }


    public DomainValue addDomainValue(String domainKey, DomainValue domainValue) {
        checkDomainExist(domainKey);
        Domain domain = domainRepository.findByKey(Domains.valueOf(domainKey));
        if (Objects.nonNull(domain) ) {
            domainValue.setDomainId(domain.getId());
            DomainValue domainValueCreated = domainValueRepository.save(domainValue);
            if (Objects.isNull(domain.getDomainValues())) {
                domain.setDomainValues(new ArrayList<>());
            };
            domain.getDomainValues().add(domainValueCreated);
            domainRepository.save(domain);
            return  domainValueCreated;

        } else throw new BussinessServiceException("No se encontro el dominio " + domainKey );


    }

    public void deleteDomainValue(String domainKey,String key) {
        checkDomainExist(domainKey);
        Domain domain = domainRepository.findByKey(Domains.valueOf(domainKey));
        if (Objects.nonNull(domain) ) {
            Optional<DomainValue>  optionalDomainValue = domainValueRepository.findByDomainIdAndKey(domain.getId(),key);
            if (optionalDomainValue.isPresent()) {
                final DomainValue domainValue = optionalDomainValue.get();
                if (Objects.nonNull(domain.getDomainValues())) {

                    List<DomainValue> newList = domain.getDomainValues()
                                                .stream()
                                                .filter(c -> !c.getId().equals(domainValue.getId()))
                                                .collect(Collectors.toList());
                    domain.setDomainValues(newList);
                    domainRepository.save(domain);

                }

                domainValueRepository.deleteById(domainValue.getId());
            } else {
                throw new BussinessServiceException("No se encontro el value " + key );
            }


        } else throw new BussinessServiceException("No se encontro el dominio " + domainKey );
    }


    private void checkDomainExist(String domain) {
        try {
            Domains.valueOf(domain);
        } catch (Exception ex) {
            throw new BussinessServiceException("Dominio " + domain + " no especificado en la lista de dominios para la aplicacion." );
        }

    }
    
	public Domain addPerioodo() {
		Domain domain =  domainRepository.findByKey(Domains.TIME_PERIOD);
		if (domain == null) {
			domain = new Domain();
			domain.setKey(Domains.TIME_PERIOD);
			domain.setDescription("Periodo");
			List<DomainValue> domainDtoList = new ArrayList<>();
			DomainValue domainValueDto = null;

			domainValueDto = new DomainValue();
			domainValueDto.setKey("ANIO");
			domainValueDto.setValue("año");
			domainValueDto.setOrder(1);
			domainDtoList.add(domainValueDto);

			domainValueDto = new DomainValue();
			domainValueDto.setKey("MES");
			domainValueDto.setValue("Mes");
			domainValueDto.setOrder(2);
			domainDtoList.add(domainValueDto);

			domainValueDto = new DomainValue();
			domainValueDto.setKey("DIA");
			domainValueDto.setValue("dia");
			domainValueDto.setOrder(3);
			domainDtoList.add(domainValueDto);

			domain.setDomainValues(domainDtoList);
			addDomain(domain);
		}
		return domain;
	}

}
