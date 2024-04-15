package com.netwin.service;

import org.springframework.stereotype.Service;

import com.netwin.entiry.NetwinCustomerDetails;

@Service
public interface NetwinCustomerDetailsService {

	NetwinCustomerDetails fetchNetwinCustomerDetails(String netwCustSrNo);

}
