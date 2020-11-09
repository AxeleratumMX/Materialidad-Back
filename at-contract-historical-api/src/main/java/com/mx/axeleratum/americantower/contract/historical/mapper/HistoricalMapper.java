package com.mx.axeleratum.americantower.contract.historical.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.historical.dto.HistoryDto;
import com.mx.axeleratum.americantower.contract.historical.dto.entry.HistoryDtoEntry;
import com.mx.axeleratum.americantower.contract.historical.model.History;

@Mapper(componentModel="spring")
public interface HistoricalMapper {
    HistoryDto toHistoryDto(History history);

    @InheritInverseConfiguration
    History toHistory(HistoryDto historyDto);

    List<HistoryDto> toHistoryDtoList(Collection<History> histories);

    List<History> toHistoryList(Collection<HistoryDto> historiesDtos);

	History toHistoryFromEntry(HistoryDtoEntry historyDtoEntry);

	List<History> fromEntryListToHistoryList(List<HistoryDtoEntry> historyDtoEntry);
}
