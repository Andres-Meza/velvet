package com.uniminuto.velvet.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.Tables;

import java.util.List;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {

    /**
     * Verifica si existe una mesa con el número y ubicación especificados
     */
    boolean existsByNumberAndLocationId(String number, Long locationId);

    /**
     * Verifica si existe una mesa con el número y ubicación especificados excluyendo un ID
     */
    boolean existsByNumberAndLocationIdAndIdNot(String number, Long locationId, Long id);

    /**
     * Busca todas las mesas por ubicación
     */
    List<Tables> findByLocationId(Long locationId);

    /**
     * Busca todas las mesas activas con capacidad mayor o igual a la especificada
     */
    List<Tables> findByActiveIsTrueAndCapacityGreaterThanEqual(Integer capacity);
}