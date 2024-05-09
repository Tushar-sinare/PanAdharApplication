package com.netwin.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.entity.AharNtwnRequest;
@Repository
public interface AharNtwnRequestRepo extends JpaRepository<AharNtwnRequest, Long>  {
	
CustomerVendorDetailsDto findByAhaReMasSrNo(long ahaReMasSrNo);


}

