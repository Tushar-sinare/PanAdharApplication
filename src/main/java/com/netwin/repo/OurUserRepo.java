package com.netwin.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.netwin.entity.OurUser;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<OurUser, Integer> {
    @Query(value = "select * from users where username = ?1", nativeQuery = true)
   	Optional<OurUser> findByUserName(String userName);
}

