package com.netwin.mapper.impl;

import org.springframework.stereotype.Component;

import com.netwin.dto.PnNetwinRequestDto;
import com.netwin.entiry.PnNetwinRequest;
import com.netwin.mapper.PnNetwinRequestMapper;

@Component
public class PnNetwinRequestMapperImpl implements PnNetwinRequestMapper {

	@Override
	public PnNetwinRequest toPnNetwinRequestEntity(PnNetwinRequestDto pnNtRequestDto) {
		if (pnNtRequestDto == null) {
			return null;
		}

		PnNetwinRequest pnNetwinRequest = new PnNetwinRequest();
		pnNetwinRequest.setReqEncrypt(pnNtRequestDto.getReqEncrypt());
		pnNetwinRequest.setReqDecrypt(pnNtRequestDto.getReqDecrypt());
		pnNetwinRequest.setEntryDate(pnNtRequestDto.getEntryDate());
		pnNetwinRequest.setCallingIpAdr(pnNtRequestDto.getCallingIpAdr());
		// Set any other mappings if needed

		return pnNetwinRequest;
	}

	@Override
	public PnNetwinRequestDto toPnNetwinRequestDto(PnNetwinRequest pnNetwinRequest) {
		if (pnNetwinRequest == null) {
			return null;
		}

		PnNetwinRequestDto pnNetwinRequestDto = new PnNetwinRequestDto();
		pnNetwinRequestDto.setPnReMasSrNo(pnNetwinRequest.getPnReMasSrNo());
		pnNetwinRequestDto.setReqEncrypt(pnNetwinRequest.getReqEncrypt());
		pnNetwinRequestDto.setReqDecrypt(pnNetwinRequest.getReqDecrypt());
		pnNetwinRequestDto.setEntryDate(pnNetwinRequest.getEntryDate());
		pnNetwinRequestDto.setCallingIpAdr(pnNetwinRequest.getCallingIpAdr());
		// Set any other mappings if needed

		return pnNetwinRequestDto;
	}
}
