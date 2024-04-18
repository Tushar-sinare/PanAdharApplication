package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.AharVendorDetails;

@Repository
public interface AharVendorDetailsRepo extends JpaRepository<AharVendorDetails, Integer> {

}
