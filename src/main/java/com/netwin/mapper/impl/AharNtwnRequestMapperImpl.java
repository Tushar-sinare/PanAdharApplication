
package com.netwin.mapper.impl;

import org.springframework.stereotype.Service;

import com.netwin.dto.AharNtwnReqDto;
import com.netwin.entiry.AharNtwnRequest;
import com.netwin.mapper.AharNtwnRequestMapper;
@Service
public class AharNtwnRequestMapperImpl implements AharNtwnRequestMapper{

	@Override
	public AharNtwnRequest toAharNtwnRequestEntity(AharNtwnReqDto aharNtwnReqDto) {
		if(aharNtwnReqDto==null) {
		return null;
		}
		AharNtwnRequest aharNtwnRequest = new AharNtwnRequest();
		aharNtwnRequest.setCallingIpAdr(aharNtwnReqDto.getCallingIpAdr());
		aharNtwnRequest.setEntryDate(aharNtwnReqDto.getEntryDate());
		aharNtwnRequest.setReqDecrypt(aharNtwnReqDto.getReqDecrypt());
		aharNtwnRequest.setReqEncrypt(aharNtwnReqDto.getReqEncrypt());
		return aharNtwnRequest;
	}

	@Override
	public AharNtwnReqDto toAharNtwnReqDto(AharNtwnRequest aharNtwnRequest) {
		if(aharNtwnRequest ==null) {
			return null;
		}
		AharNtwnReqDto aharNtwnReqDto = new AharNtwnReqDto();
		aharNtwnReqDto.setAharReMasSrNo(aharNtwnRequest.getAdhReMasSrNo());
		aharNtwnReqDto.setCallingIpAdr(aharNtwnRequest.getCallingIpAdr());
		aharNtwnReqDto.setEntryDate(aharNtwnRequest.getEntryDate());
		aharNtwnReqDto.setReqDecrypt(aharNtwnRequest.getReqDecrypt());
		aharNtwnReqDto.setReqEncrypt(aharNtwnRequest.getReqEncrypt());
		return aharNtwnReqDto;
	
	}


}
