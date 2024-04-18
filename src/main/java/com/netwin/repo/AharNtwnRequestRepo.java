package com.netwin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netwin.entiry.AharNtwnRequest;
@Repository
public interface AharNtwnRequestRepo extends JpaRepository<AharNtwnRequest, Integer>  {

}
