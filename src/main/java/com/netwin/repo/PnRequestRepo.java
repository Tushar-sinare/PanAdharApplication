package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.PnRequest;

@Repository
public interface PnRequestRepo extends JpaRepository<PnRequest, Integer>{

}
