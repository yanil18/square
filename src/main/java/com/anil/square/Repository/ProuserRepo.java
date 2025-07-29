package com.anil.square.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.square.Entities.model.Prousers;

@Repository
public interface ProuserRepo extends JpaRepository<Prousers, Long> {

    Optional<Prousers> findByMobileno(String mobileno);

    Prousers findByEmail(String email);
    
}
