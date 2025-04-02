package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    /**
     * Encuentra todas las subcategorías que pertenecen a una categoría específica
     * @param categoryId Id de la categoría
     * @return lista de subcategorías
     */
    List<SubCategory> findByCategoryId(Long categoryId);

    /**
     * Busca subcategorías cuyo nombre contiene el texto proporcionado (insensible a mayúsculas/minúsculas)
     * @param name texto a buscar
     * @param pageable configuración de paginación
     * @return página de subcategorías que coinciden
     */
    Page<SubCategory> findByNameContainingIgnoreCase(String name, Pageable pageable);
}