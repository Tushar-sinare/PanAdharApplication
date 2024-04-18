package com.netwin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.netwin.dto.AharNtwnReqDto;
import com.netwin.entiry.AharNtwnRequest;


@Mapper
@Component
public interface AharNtwnRequestMapper {
	AharNtwnRequestMapper INSTANCE = Mappers.getMapper(AharNtwnRequestMapper.class);
	
	 AharNtwnRequest toAharNtwnRequestEntity(AharNtwnReqDto aharNtwnReqDto);
	 AharNtwnReqDto toAharNtwnReqDto(AharNtwnRequest aharNtwnReq);
}
