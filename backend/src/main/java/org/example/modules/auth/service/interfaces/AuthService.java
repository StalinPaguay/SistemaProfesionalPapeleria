package org.example.modules.auth.service.interfaces;

import org.example.modules.auth.dto.AuthResponse;
import org.example.modules.auth.dto.LoginRequest;
import org.example.modules.auth.dto.RegisterRequest;
import org.example.modules.auth.entity.Rol;
import org.example.modules.auth.entity.Usuario;
import org.example.exception.ReglaNegocioException;
import org.example.modules.auth.repository.UsuarioRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.modules.auditoria.service.interfaces.AuditoriaService;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
/**
 * Servicio para gestionar Auth.
 */
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuditoriaService auditoriaService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       AuditoriaService auditoriaService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.auditoriaService = auditoriaService;
    }

    /**

     * Método registrar.

     */

    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new ReglaNegocioException("El correo ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        if (request.getRol() != null) {
            usuario.setRol(request.getRol());
        } else {
            usuario.setRol(Rol.USUARIO);
        }

        Usuario guardado = usuarioRepository.save(usuario);

        // Generar token inicial opcional o simplemente devolver éxito. Aquí devolveremos token.
        // UserDetails (simulado temporalmente para generar token sin Authentication context)
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(guardado.getCorreo())
                .password(guardado.getPassword())
                .authorities("ROLE_" + guardado.getRol().name())
                .build();
                
        String token = jwtTokenProvider.generateToken(userDetails, guardado.getRol().name());
        
        auditoriaService.registrar("REGISTER", "Usuario", guardado.getId(), "Registro de usuario: " + guardado.getCorreo());
        
        return new AuthResponse(token, guardado.getCorreo(), guardado.getRol().name(), guardado.getNombre() + " " + guardado.getApellido());
    }

    /**

     * Método login.

     */

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new ReglaNegocioException("Usuario no encontrado"));

        String token = jwtTokenProvider.generateToken(userDetails, usuario.getRol().name());

        auditoriaService.registrar("LOGIN", "Usuario", usuario.getId(), "Inicio de sesión: " + usuario.getCorreo());

        return new AuthResponse(token, usuario.getCorreo(), usuario.getRol().name(), usuario.getNombre() + " " + usuario.getApellido());
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Usuario getUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String correo = ((UserDetails) principal).getUsername();
            return usuarioRepository.findByCorreo(correo)
                    .orElseThrow(() -> new ReglaNegocioException("Usuario autenticado no encontrado"));
        }
        throw new ReglaNegocioException("No hay un usuario autenticado");
    }
}

