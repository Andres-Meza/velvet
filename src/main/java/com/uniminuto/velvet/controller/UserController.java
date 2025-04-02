package com.uniminuto.velvet.controller;

import com.uniminuto.velvet.model.dto.UserDTO;
import com.uniminuto.velvet.service.interfaces.UserService;
import com.uniminuto.velvet.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v4/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> createUser(@Valid @RequestBody UserDTO.CreateUser createUserDto) {
        log.info("Solicitud para crear nuevo usuario: {}", createUserDto.getUsername());
        UserDTO.UserDetails createdUser = userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Usuario creado exitosamente", createdUser));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> updateUser(@Valid @RequestBody UserDTO.UpdateUser updateUserDto) {
        log.info("Solicitud para actualizar usuario ID: {}", updateUserDto.getId());
        UserDTO.UserDetails updatedUser = userService.updateUser(updateUserDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado exitosamente", updatedUser));
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@Valid @RequestBody UserDTO.UpdatePassword updatePasswordDto) {
        log.info("Solicitud para actualizar contraseña del usuario ID: {}", updatePasswordDto.getUserId());
        boolean success = userService.updatePassword(updatePasswordDto);

        if (success) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Contraseña actualizada exitosamente", null));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Error al actualizar contraseña. Verifica los datos ingresados", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> getUserById(@PathVariable Long id) {
        log.info("Buscando usuario por ID: {}", id);
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado con ID: " + id, null)));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> getUserByUsername(@PathVariable String username) {
        log.info("Buscando usuario por nombre de usuario: {}", username);
        return userService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado con nombre: " + username, null)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> getUserByEmail(@PathVariable String email) {
        log.info("Buscando usuario por email: {}", email);
        return userService.getUserByEmail(email)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado con email: " + email, null)));
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> getUserByDocument(@PathVariable String document) {
        log.info("Buscando usuario por documento: {}", document);
        return userService.getUserByDocument(document)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Usuario no encontrado con documento: " + document, null)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDTO.UserDetails>>> getAllUsers(Pageable pageable) {
        log.info("Obteniendo lista de usuarios paginada");
        Page<UserDTO.UserDetails> usersPage = userService.getAllUsers(pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de usuarios obtenida", usersPage));
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Page<UserDTO.UserDetails>>> getUsersByStatus(
            @RequestParam Boolean active,
            Pageable pageable) {
        log.info("Obteniendo lista de usuarios por estado: {}", active);
        Page<UserDTO.UserDetails> usersPage = userService.getUsersByActiveStatus(active, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de usuarios filtrada por estado", usersPage));
    }

    @GetMapping("/check/username")
    public ResponseEntity<ApiResponse<Boolean>> checkUsernameExists(@RequestParam String username) {
        log.info("Verificando existencia de nombre de usuario: {}", username);
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(true, exists ? "El nombre de usuario ya existe" : "Nombre de usuario disponible", exists));
    }

    @GetMapping("/check/email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(@RequestParam String email) {
        log.info("Verificando existencia de email: {}", email);
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(true, exists ? "El email ya existe" : "Email disponible", exists));
    }

    @GetMapping("/check/document")
    public ResponseEntity<ApiResponse<Boolean>> checkDocumentExists(@RequestParam String document) {
        log.info("Verificando existencia de documento: {}", document);
        boolean exists = userService.existsByDocument(document);
        return ResponseEntity.ok(new ApiResponse<>(true, exists ? "El documento ya existe" : "Documento disponible", exists));
    }

    @PutMapping("/{id}/last-login")
    public ResponseEntity<ApiResponse<Void>> updateLastLogin(@PathVariable String username) {
        log.info("Actualizando último acceso para usuario: {}", username);
        userService.updateLastLogin(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "Último acceso actualizado exitosamente", null));
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<ApiResponse<UserDTO.UserDetails>> toggleUserActive(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        log.info("Cambiando estado de activación del usuario ID: {} a: {}", id, active);
        UserDTO.UserDetails updatedUser = userService.toggleUserActive(id, active);
        return ResponseEntity.ok(new ApiResponse<>(true, "Estado de usuario actualizado exitosamente", updatedUser));
    }
}