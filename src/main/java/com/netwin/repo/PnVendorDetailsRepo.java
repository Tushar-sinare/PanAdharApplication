package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.PnVendorDetails;

@Repository
public interface PnVendorDetailsRepo extends JpaRepository<PnVendorDetails, Integer>{

	PnVendorDetails findByPnVnDrSrNo(int vendorId);

}
