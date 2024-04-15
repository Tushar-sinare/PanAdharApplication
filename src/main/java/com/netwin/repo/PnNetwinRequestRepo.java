package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.PnNetwinRequest;
@Repository
public interface PnNetwinRequestRepo extends JpaRepository<PnNetwinRequest, Long>{

}
