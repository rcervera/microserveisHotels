package com.daw.hotel.auth.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daw.hotel.auth.model.Role;
import com.daw.hotel.auth.model.User;
import com.daw.hotel.auth.repository.RoleRepository;
import com.daw.hotel.auth.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // --- Crear rols si no existeixen ---
            Role roleUser = roleRepository.findByName("USER").orElseGet(() -> {
                Role r = new Role();
                r.name = "USER";
                return roleRepository.save(r);
            });

            Role roleAdmin = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role r = new Role();
                r.name = "ADMIN";
                return roleRepository.save(r);
            });

            // --- Crear usuaris si la taula està buida ---
            if (userRepository.count() == 0) {
                for (int i = 1; i <= 15; i++) {
                    User user = new User();
                    user.username = "user" + i;
                    user.password = passwordEncoder.encode("user" + i);
                    user.roles = new HashSet<>(Set.of(roleUser));
                    userRepository.save(user);
                }

                // Usuari admin amb dos rols
                User admin = new User();
                admin.username = "admin";
                admin.password = passwordEncoder.encode("admin");
                admin.roles = new HashSet<>(Set.of(roleUser, roleAdmin));
                userRepository.save(admin);

                System.out.println("✅ Usuaris i rols inicialitzats correctament!");
            } else {
                System.out.println("ℹ️ Usuaris ja existents, no es creen nous.");
            }
        };
    }
}
