package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.GtVndrRequest;

@Repository
public interface GtVndrRequestRepo extends JpaRepository<GtVndrRequest, Long> {

}
