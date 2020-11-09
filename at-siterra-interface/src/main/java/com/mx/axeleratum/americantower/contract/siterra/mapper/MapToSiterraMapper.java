package com.mx.axeleratum.americantower.contract.siterra.mapper;

import com.mx.axeleratum.americantower.contract.core.model.SiTerra;
import com.mx.axeleratum.americantower.contract.siterra.dto.SourceDto;
import com.mx.axeleratum.americantower.contract.siterra.mapper.util.MappingUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( uses = MappingUtil.class )
public interface MapToSiterraMapper {

    MapToSiterraMapper MAPPER = Mappers.getMapper( MapToSiterraMapper.class );

    @Mapping(source = "map", target = "siteNumber", qualifiedBy = MappingUtil.SiteNumber.class )
    @Mapping(source = "map", target = "currentRentalAmountUOM", qualifiedBy = MappingUtil.CurrentRentalAmountUOM.class )
    SiTerra toTarget(SourceDto source);

}

