package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.AharRequest;

@Repository
public interface AharRequestRepo extends JpaRepository<AharRequest, Integer> {

}
