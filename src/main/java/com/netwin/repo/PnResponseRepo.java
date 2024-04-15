package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.PnResponse;

@Repository
public interface PnResponseRepo extends JpaRepository<PnResponse, Integer> {

}
