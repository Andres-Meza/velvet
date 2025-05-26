package com.uniminuto.velvet.service.impl;

import com.uniminuto.velvet.config.CustomUserDetails;
import com.uniminuto.velvet.model.entity.User;
import com.uniminuto.velvet.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Cargando usuario por username: {}", username);

        User user = userService.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        log.debug("Usuario encontrado: {} - Rol: {} - Sede: {}",
                user.getUsername(), user.getRole().getName(), user.getLocation().getName());

        return new CustomUserDetails(user);
    }
}