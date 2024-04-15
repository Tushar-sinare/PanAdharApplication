package com.netwin.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.NetwinProductionDetails;

@Repository
public interface NetwinProductionDetailsRepo extends JpaRepository<NetwinProductionDetails, Integer>{

	Object findByNetwProdId(String netwProdId);


}
