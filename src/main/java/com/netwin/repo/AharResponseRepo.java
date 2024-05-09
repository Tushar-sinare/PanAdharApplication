package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.AharResponse;

@Repository
public interface AharResponseRepo extends JpaRepository<AharResponse, Long>{

}
