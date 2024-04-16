package com.netwin.service.impl;


import org.springframework.stereotype.Service;

import com.netwin.entiry.NetwinCustomerDetails;
import com.netwin.repo.NetwinCustomerDetailsRepo;
import com.netwin.service.NetwinCustomerDetailsService;
@Service
public class NetwinCustomerDetailsServiceImpl implements NetwinCustomerDetailsService{

private NetwinCustomerDetailsRepo netwinCustomerDetailsRepo;

@Override
public NetwinCustomerDetails fetchNetwinCustomerDetails(String netwCustSrNo) {
	return netwinCustomerDetailsRepo.findByNetwCustId(netwCustSrNo);
	
}
}
