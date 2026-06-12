package org.example.modules.auth.controller;

import jakarta.validation.Valid;
import org.example.modules.auth.dto.ActualizarUsuarioDTO;
import org.example.modules.auth.dto.CambiarRolDTO;
import org.example.modules.auth.dto.CrearUsuarioDTO;
import org.example.modules.auth.dto.UsuarioResponseDTO;
import org.example.modules.auth.service.interfaces.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody CrearUsuarioDTO dto) {
        return new ResponseEntity<>(usuarioService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody ActualizarUsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.update(id, dto));
    }

    @PatchMapping("/{id}/rol")
    public ResponseEntity<UsuarioResponseDTO> changeRole(@PathVariable Long id, @Valid @RequestBody CambiarRolDTO dto) {
        return ResponseEntity.ok(usuarioService.changeRole(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
