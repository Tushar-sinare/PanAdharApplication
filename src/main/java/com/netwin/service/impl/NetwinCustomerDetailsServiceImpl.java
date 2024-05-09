package com.netwin.service.impl;





import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.dto.NetwnCustomerDto;
import com.netwin.entity.NetwinCustomerDetails;
import com.netwin.repo.NetwinCustomerDetailsRepo;
import com.netwin.service.NetwinCustomerDetailsService;
@Service
public class NetwinCustomerDetailsServiceImpl implements NetwinCustomerDetailsService{

private NetwinCustomerDetailsRepo netwinCustomerDetailsRepo;
private Mapper mapper;
@Autowired
public NetwinCustomerDetailsServiceImpl(NetwinCustomerDetailsRepo netwinCustomerDetailsRepo,Mapper mapper) {
	this.netwinCustomerDetailsRepo = netwinCustomerDetailsRepo;
	this.mapper = mapper;
}
public NetwinCustomerDetailsServiceImpl() {
	 
}
@Override
public NetwinCustomerDetails fetchNetwinCustomerDetails(String netwCustId) {
	
	NetwnCustomerDto netwnCustomerDto=  netwinCustomerDetailsRepo.findByNetwCustId(netwCustId);
	 
	
	return mapper.map(netwnCustomerDto, NetwinCustomerDetails.class);
}

}

