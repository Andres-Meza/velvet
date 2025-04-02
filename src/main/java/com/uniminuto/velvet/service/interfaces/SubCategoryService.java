package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.SubCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {
	/**
	 * Crea una nueva subcategoría
	 * @param createDTO datos para crear la subcategoría
	 * @return detalles de la subcategoría creada
	 */
	SubCategoryDTO.DetailsSubCategory create(SubCategoryDTO.CreateSubCategory createDTO);

	/**
	 * Actualiza una subcategoría existente
	 * @param updateDTO datos para actualizar la subcategoría
	 * @return detalles de la subcategoría actualizada
	 */
	SubCategoryDTO.DetailsSubCategory update(SubCategoryDTO.UpdateSubCategory updateDTO);

	/**
	 * Elimina una subcategoría por su ID
	 * @param id identificador de la subcategoría
	 * @return true si se eliminó correctamente
	 */
	boolean deleteById(Long id);

	/**
	 * Busca una subcategoría por su ID
	 * @param id identificador de la subcategoría
	 * @return detalles de la subcategoría o vacío si no existe
	 */
	Optional<SubCategoryDTO.DetailsSubCategory> findById(Long id);

	/**
	 * Obtiene todas las subcategorías con paginación
	 * @param pageable configuración de paginación
	 * @return página de subcategorías
	 */
	Page<SubCategoryDTO.DetailsSubCategory> findAll(Pageable pageable);

	/**
	 * Obtiene todas las subcategorías de una categoría específica
	 * @param categoryId identificador de la categoría
	 * @return lista de subcategorías
	 */
	List<SubCategoryDTO.SimpleSubCategory> findAllByCategoryId(Long categoryId);

	/**
	 * Busca subcategorías por nombre (contiene)
	 * @param name texto a buscar
	 * @param pageable configuración de paginación
	 * @return página de subcategorías que coinciden
	 */
	Page<SubCategoryDTO.DetailsSubCategory> findByNameContaining(String name, Pageable pageable);
}