package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.GtVendorDetails;

@Repository
public interface GtVndrDetailsRepo extends JpaRepository<GtVendorDetails, Long> {

	GtVendorDetails findByGtVnDrSrNo(int vendorId);

}
