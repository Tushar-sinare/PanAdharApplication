package com.netwin.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.dto.NetwnCustomerDto;
import com.netwin.entity.NetwinCustomerDetails;

@Repository
public interface NetwinCustomerDetailsRepo extends JpaRepository<NetwinCustomerDetails, Integer> {

	

	

	NetwnCustomerDto findByNetwCustId(String netwCustId);







}
