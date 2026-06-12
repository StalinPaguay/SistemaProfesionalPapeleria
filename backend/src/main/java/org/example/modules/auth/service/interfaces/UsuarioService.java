package org.example.modules.auth.service.interfaces;

import org.example.modules.auth.dto.ActualizarUsuarioDTO;
import org.example.modules.auth.dto.CambiarRolDTO;
import org.example.modules.auth.dto.CrearUsuarioDTO;
import org.example.modules.auth.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> findAll();
    UsuarioResponseDTO findById(Long id);
    UsuarioResponseDTO create(CrearUsuarioDTO dto);
    UsuarioResponseDTO update(Long id, ActualizarUsuarioDTO dto);
    void delete(Long id);
    UsuarioResponseDTO changeRole(Long id, CambiarRolDTO dto);
}
