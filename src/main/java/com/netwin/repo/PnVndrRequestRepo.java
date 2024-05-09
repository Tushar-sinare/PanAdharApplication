package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.PnVndrRequest;

@Repository
public interface PnVndrRequestRepo extends JpaRepository<PnVndrRequest, Integer> {

}
