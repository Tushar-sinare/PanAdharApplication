package com.netwin.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netwin.entiry.NetwinProductionDetails;
import com.netwin.repo.NetwinProductionDetailsRepo;
import com.netwin.service.ErrorApplicationService;
import com.netwin.service.NetwinProductionDetailsService;

@Service
public class NetwinProductionDetailsServiceImpl implements NetwinProductionDetailsService{

private NetwinProductionDetailsRepo netwinProductionDetailsRepo;

ErrorApplicationService errorApplicationService;
private static final Logger logger = LoggerFactory.getLogger(NetwinProductionDetailsServiceImpl.class);

	@Override
	public NetwinProductionDetails fetchNetwinProductionDetails(String netwProdId) {
		NetwinProductionDetails netwinProductionDetails = null ;
	
		try {
			//find product details
		netwinProductionDetails = (NetwinProductionDetails) netwinProductionDetailsRepo.findByNetwProdId(netwProdId);
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			
			errorApplicationService.storeError(401, ex.getMessage());
				}
		return netwinProductionDetails;
	}

}
