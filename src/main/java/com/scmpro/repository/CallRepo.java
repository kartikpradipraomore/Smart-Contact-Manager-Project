package com.scmpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scmpro.entities.Call;

@Repository
public interface CallRepo extends JpaRepository<Call,Integer> {

}
