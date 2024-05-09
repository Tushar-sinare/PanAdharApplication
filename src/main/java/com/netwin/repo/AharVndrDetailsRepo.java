package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entity.AharVndrDetails;

@Repository
public interface AharVndrDetailsRepo extends JpaRepository<AharVndrDetails, Integer> {

	AharVndrDetails findByAharVnDrSrNo(int netwVndrs);

}
