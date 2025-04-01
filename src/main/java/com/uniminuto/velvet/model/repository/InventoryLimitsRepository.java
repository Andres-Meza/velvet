package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.InventoryLimits;

@Repository
public interface InventoryLimitsRepository extends JpaRepository<InventoryLimits, Long> {
}