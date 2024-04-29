package com.netwin.repo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.NetwinCustomerDetails;

@Repository
public interface NetwinCustomerDetailsRepo extends JpaRepository<NetwinCustomerDetails, Integer> {


	NetwinCustomerDetails findByNetwCustId(String netwCustSrNo);



}
