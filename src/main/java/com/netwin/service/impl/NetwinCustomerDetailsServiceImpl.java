package com.netwin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.NetwinCustomerDetails;
import com.netwin.repo.NetwinCustomerDetailsRepo;
import com.netwin.service.NetwinCustomerDetailsService;
@Service
public class NetwinCustomerDetailsServiceImpl implements NetwinCustomerDetailsService{
@Autowired
NetwinCustomerDetailsRepo netwinCustomerDetailsRepo;

@Override
public NetwinCustomerDetails fetchNetwinCustomerDetails(String netwCustSrNo) {
	NetwinCustomerDetails netwinCustomerDetails =  netwinCustomerDetailsRepo.findByNetwCustId(netwCustSrNo);
	return netwinCustomerDetails;
}
}
