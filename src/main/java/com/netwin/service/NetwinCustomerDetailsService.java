package com.netwin.service;

import org.springframework.stereotype.Service;

import com.netwin.entity.NetwinCustomerDetails;

@Service
public interface NetwinCustomerDetailsService {

	NetwinCustomerDetails fetchNetwinCustomerDetails(String netwCustSrNo);

}
