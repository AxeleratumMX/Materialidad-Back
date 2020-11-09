package com.mx.axeleratum.americantower.contract.origin.mapper;

import com.mx.axeleratum.americantower.contract.core.model.ContractLand;
import com.mx.axeleratum.americantower.contract.core.model.ContractTower;
import com.mx.axeleratum.americantower.contract.origin.dto.ContractLandDto;
import com.mx.axeleratum.americantower.contract.origin.dto.ContratTowerDto;
import com.mx.axeleratum.americantower.contract.origin.dto.entry.ContractLandDtoEntry;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel="spring")
public interface ContratMapper {
//    NotificationMapper INSTANCE = Mappers.getMapper( NotificationMapper.class );

    ContratTowerDto toContractTowerDto(ContractTower contrat);
    
    @InheritInverseConfiguration
    ContractTower toContractTower(ContratTowerDto contratDto);
    
    List<ContratTowerDto> toListContractTowerDto(List<ContractTower>  contrats);
	
    List<ContratTowerDto> toContractDtoList(Collection<ContractTower> contratTowers);
    
     List<ContractTower> toContractList(Collection<ContratTowerDto> contratTowerDtos);
   
    ContractLandDto toContractLandDto(ContractLand contratLand);

    @InheritInverseConfiguration
    ContractLand toContractLand(ContractLandDto contratLandDto);

    List<ContractLandDto> toContractLandDtoList(Collection<ContractLand> contratLands);

    List<ContractLand> toContractLandList(Collection<ContractLandDto> contratLandDtos);

	ContractLand toContractLandFromEntry(ContractLandDtoEntry contractLandDto);

}
