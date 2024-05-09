package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.GtResponse;

@Repository
public interface GtResponseRepo extends JpaRepository<GtResponse, Long>{

}
