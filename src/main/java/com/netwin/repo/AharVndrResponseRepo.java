package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.AharVndrResponse;

@Repository
public interface AharVndrResponseRepo extends JpaRepository<AharVndrResponse, Integer> {

}
