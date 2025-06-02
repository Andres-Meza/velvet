package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.MovementType;

import java.util.Optional;

@Repository
public interface MovementTypeRepository extends JpaRepository<MovementType, Long> {
    boolean existsByName(String name);

    Optional<MovementType> findByName(String name);
}