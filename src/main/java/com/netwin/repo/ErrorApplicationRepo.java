package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.ErrorApplication;
@Repository
public interface ErrorApplicationRepo extends JpaRepository<ErrorApplication, Integer>{

}
