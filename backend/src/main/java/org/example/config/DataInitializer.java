package org.example.config;

import org.example.modules.auth.entity.Rol;
import org.example.modules.auth.entity.Usuario;
import org.example.modules.auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Usuario admin = usuarioRepository.findByCorreo("admin@paperds.com").orElse(new Usuario());
            admin.setNombre("Super");
            admin.setApellido("Administrador");
            admin.setCorreo("admin@paperds.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMINISTRADOR);

            usuarioRepository.save(admin);
            System.out.println("Superusuario Administrador garantizado: admin@paperds.com / admin123");
        };
    }
}
