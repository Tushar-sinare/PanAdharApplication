package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.GtVndrResponse;

@Repository
public interface GtVndrResponseRepo extends JpaRepository<GtVndrResponse, Long> {

}
