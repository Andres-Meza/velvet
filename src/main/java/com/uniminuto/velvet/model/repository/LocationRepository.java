package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByName(String name);
}