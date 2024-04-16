package com.netwin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.netwin.dto.PnNetwinRequestDto;
import com.netwin.entiry.PnNetwinRequest;


@Mapper
@Component
public interface PnNetwinRequestMapper {
    PnNetwinRequestMapper INSTANCE = Mappers.getMapper(PnNetwinRequestMapper.class);

  // @Mapping(target = "pnReMasSrNo", ignore = true)
    PnNetwinRequest toPnNetwinRequestEntity(PnNetwinRequestDto pnNetwinRequestDto);

    PnNetwinRequestDto toPnNetwinRequestDto(PnNetwinRequest pnNetwinRequest);
}
