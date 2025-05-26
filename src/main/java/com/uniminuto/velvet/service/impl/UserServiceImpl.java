package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.exception.UniqueConstraintViolationException;
import com.uniminuto.velvet.model.dto.UserDTO;
import com.uniminuto.velvet.model.entity.User;
import com.uniminuto.velvet.exception.ResourceNotFoundException;
import com.uniminuto.velvet.model.mapper.UserMapper;
import com.uniminuto.velvet.model.repository.UserRepository;
import com.uniminuto.velvet.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO.UserDetails createUser(UserDTO.CreateUser createUserDto) {
        // Validar que no existan duplicados
        validateUniqueConstraints(
                null,
                createUserDto.getUsername(),
                createUserDto.getEmail(),
                createUserDto.getDocument()
        );

        // Convertir DTO a entidad y establecer contraseña encriptada
        User user = userMapper.createUserDtoToUser(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        // Guardar usuario
        User savedUser = userRepository.save(user);
        log.info("Usuario creado con ID: {}", savedUser.getId());

        // Retornar DTO con detalles
        return userMapper.userToUserDetailsDto(savedUser);
    }

    @Override
    public UserDTO.UserDetails updateUser(UserDTO.UpdateUser updateUserDto) {
        // Buscar usuario
        User user = userRepository.findById(updateUserDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + updateUserDto.getId()));

        // Validar que no existan duplicados de username, email o documento
        validateUniqueConstraints(
                updateUserDto.getId(),
                updateUserDto.getUsername(),
                updateUserDto.getEmail(),
                updateUserDto.getDocument()
        );

        // Actualizar usuario
        userMapper.updateUserFromDto(updateUserDto, user);
        User updatedUser = userRepository.save(user);
        log.info("Usuario actualizado con ID: {}", updatedUser.getId());

        // Retornar DTO con detalles actualizados
        return userMapper.userToUserDetailsDto(updatedUser);
    }

    @Override
    public boolean updatePassword(UserDTO.UpdatePassword updatePasswordDto) {
        // Buscar usuario
        User user = userRepository.findById(updatePasswordDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + updatePasswordDto.getUserId()));

        // Verificar que la contraseña actual sea correcta
        if (!passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword())) {
            log.warn("Intento de cambio de contraseña con contraseña actual incorrecta para usuario ID: {}", updatePasswordDto.getUserId());
            return false;
        }

        // Verificar que la nueva contraseña y la confirmación coincidan
        if (!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getConfirmPassword())) {
            log.warn("Las contraseñas nueva y confirmación no coinciden para usuario ID: {}", updatePasswordDto.getUserId());
            return false;
        }

        // Actualizar contraseña
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
        log.info("Contraseña actualizada para usuario ID: {}", user.getId());

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO.UserDetails> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO.UserDetails> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO.UserDetails> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO.UserDetails> getUserByDocument(String document) {
        return userRepository.findByDocument(document)
                .map(userMapper::userToUserDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO.UserDetails> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO.UserDetails> getUsersByActiveStatus(Boolean active, Pageable pageable) {
        return userRepository.findByActive(active, pageable)
                .map(userMapper::userToUserDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDocument(String document) {
        return userRepository.existsByDocument(document);
    }

    @Override
    public void updateLastLogin(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.setLastLogin(LocalDateTime.now());
                    userRepository.save(user);
                    log.info("Actualizado último acceso para usuario: {}", username);
                });
    }

    @Override
    public UserDTO.UserDetails toggleUserActive(Long id, Boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        user.setActive(active);
        User updatedUser = userRepository.save(user);
        log.info("Usuario con ID: {} cambiado a estado: {}", id, active);

        return userMapper.userToUserDetailsDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Valida que no existan duplicados de username, email o documento
     */
    private void validateUniqueConstraints(Long userId, String username, String email, String document) {
        // Verificar username único
        userRepository.findByUsername(username)
                .ifPresent(existingUser -> {
                    if (!Objects.equals(existingUser.getId(), userId)) {
                        throw new UniqueConstraintViolationException("Ya existe un usuario con el nombre de usuario: " + username);
                    }
                });

        // Verificar email único
        userRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    if (!Objects.equals(existingUser.getId(), userId)) {
                        throw new UniqueConstraintViolationException("Ya existe un usuario con el email: " + email);
                    }
                });

        // Verificar documento único
        userRepository.findByDocument(document)
                .ifPresent(existingUser -> {
                    if (!Objects.equals(existingUser.getId(), userId)) {
                        throw new UniqueConstraintViolationException("Ya existe un usuario con el documento: " + document);
                    }
                });
    }
}