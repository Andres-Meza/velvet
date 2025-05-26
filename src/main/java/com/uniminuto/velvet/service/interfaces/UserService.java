package com.uniminuto.velvet.service.interfaces;

import com.uniminuto.velvet.model.dto.UserDTO;
import com.uniminuto.velvet.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    /**
     * Crea un nuevo usuario en el sistema
     * @param createUserDto DTO con los datos para crear el usuario
     * @return el usuario creado con sus detalles
     */
    UserDTO.UserDetails createUser(UserDTO.CreateUser createUserDto);

    /**
     * Actualiza un usuario existente
     * @param updateUserDto DTO con los datos para actualizar
     * @return el usuario actualizado con sus detalles
     */
    UserDTO.UserDetails updateUser(UserDTO.UpdateUser updateUserDto);

    /**
     * Actualiza la contraseña de un usuario
     * @param updatePasswordDto DTO con los datos para actualizar la contraseña
     * @return true si la contraseña fue actualizada correctamente
     */
    boolean updatePassword(UserDTO.UpdatePassword updatePasswordDto);

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Optional con los detalles del usuario o vacío si no existe
     */
    Optional<UserDTO.UserDetails> getUserById(Long id);

    /**
     * Busca un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Optional con los detalles del usuario o vacío si no existe
     */
    Optional<UserDTO.UserDetails> getUserByUsername(String username);

    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Optional con los detalles del usuario o vacío si no existe
     */
    Optional<UserDTO.UserDetails> getUserByEmail(String email);

    /**
     * Busca un usuario por su número de documento
     * @param document Número de documento
     * @return Optional con los detalles del usuario o vacío si no existe
     */
    Optional<UserDTO.UserDetails> getUserByDocument(String document);

    /**
     * Obtiene todos los usuarios de forma paginada
     * @param pageable Información de paginación
     * @return Página con los usuarios encontrados
     */
    Page<UserDTO.UserDetails> getAllUsers(Pageable pageable);

    /**
     * Busca usuarios por su estado (activo/inactivo)
     * @param active Estado de activación
     * @param pageable Información de paginación
     * @return Página con los usuarios que coinciden con el estado
     */
    Page<UserDTO.UserDetails> getUsersByActiveStatus(Boolean active, Pageable pageable);

    /**
     * Verifica si existe un usuario con el nombre de usuario indicado
     * @param username Nombre de usuario
     * @return true si existe un usuario con ese nombre de usuario
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el email indicado
     * @param email Email
     * @return true si existe un usuario con ese email
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el documento indicado
     * @param document Número de documento
     * @return true si existe un usuario con ese documento
     */
    boolean existsByDocument(String document);

    /**
     * Registra el último acceso del usuario
     * @param username Nombre de usuario
     */
    void updateLastLogin(String username);

    /**
     * Activa o desactiva un usuario
     * @param id ID del usuario
     * @param active Estado de activación
     * @return los detalles del usuario actualizado
     */
    UserDTO.UserDetails toggleUserActive(Long id, Boolean active);

    /**
     * Busca un usuario (entidad) por su nombre de usuario para autenticación
     * @param username Nombre de usuario
     * @return Optional con la entidad User o vacío si no existe
     */
    Optional<User> findUserEntityByUsername(String username);
}