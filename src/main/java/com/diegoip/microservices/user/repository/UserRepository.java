package com.diegoip.microservices.user.repository;

import com.diegoip.microservices.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
   Optional<UserEntity> findByEmail(String email);
}
