package org.example.modules.auth.service.implementation;

import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.auth.dto.ActualizarUsuarioDTO;
import org.example.modules.auth.dto.CambiarRolDTO;
import org.example.modules.auth.dto.CrearUsuarioDTO;
import org.example.modules.auth.dto.UsuarioResponseDTO;
import org.example.modules.auth.entity.Usuario;
import org.example.modules.auth.repository.UsuarioRepository;
import org.example.modules.auth.service.interfaces.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getCorreo(),
            usuario.getRol()
        );
    }

    @Override
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        return toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO create(CrearUsuarioDTO dto) {
        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new ReglaNegocioException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());

        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponseDTO update(Long id, ActualizarUsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!usuario.getCorreo().equals(dto.getCorreo()) && usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new ReglaNegocioException("El correo ya está registrado por otro usuario");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO changeRole(Long id, CambiarRolDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        usuario.setRol(dto.getRol());
        return toDTO(usuarioRepository.save(usuario));
    }
}
