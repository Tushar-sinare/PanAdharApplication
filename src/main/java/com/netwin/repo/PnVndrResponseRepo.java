package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.PnVndrResponse;

@Repository
public interface PnVndrResponseRepo extends JpaRepository<PnVndrResponse, Integer> {

}
