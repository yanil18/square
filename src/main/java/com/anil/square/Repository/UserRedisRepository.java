package com.anil.square.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.JKSv2.Model.UserRedis;

@Repository
public interface UserRedisRepository extends CrudRepository<UserRedis, String> {
   // UserRedis findByUsername(String username);

    Optional<UserRedis> findByUsername(String username);
    
}
