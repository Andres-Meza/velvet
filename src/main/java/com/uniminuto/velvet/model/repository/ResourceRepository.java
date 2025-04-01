package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    boolean existsByName(String name);
}