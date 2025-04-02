package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Busca un usuario por su nombre de usuario
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su email
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca un usuario por su número de documento
     */
    Optional<User> findByDocument(String document);

    /**
     * Verifica si existe un usuario con el nombre de usuario indicado
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el email indicado
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el documento indicado
     */
    boolean existsByDocument(String document);

    /**
     * Encuentra usuarios por su estado de activación
     */
    Page<User> findByActive(Boolean active, Pageable pageable);
}