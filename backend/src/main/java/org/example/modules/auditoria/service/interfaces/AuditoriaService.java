package org.example.modules.auditoria.service.interfaces;

import org.example.modules.auditoria.entity.RegistroAuditoria;
import org.example.modules.auditoria.repository.RegistroAuditoriaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * Servicio para gestionar Auditoria.
 */
public class AuditoriaService {

    private final RegistroAuditoriaRepository repository;

    public AuditoriaService(RegistroAuditoriaRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra una acción de auditoría.
     * @param accion Tipo de acción (CREATE, UPDATE, DELETE, LOGIN, etc.)
     * @param entidad Nombre de la entidad afectada
     * @param entidadId ID de la entidad afectada (puede ser null)
     * @param detalles Descripción o JSON de los cambios
     */
    public void registrar(String accion, String entidad, Long entidadId, String detalles) {
        RegistroAuditoria registro = new RegistroAuditoria();
        registro.setAccion(accion);
        registro.setEntidad(entidad);
        registro.setEntidadId(entidadId);
        registro.setDetalles(detalles);

        // Obtener usuario del contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            registro.setUsuarioCorreo(((UserDetails) auth.getPrincipal()).getUsername());
        } else {
            registro.setUsuarioCorreo("SISTEMA");
        }

        repository.save(registro);
    }

    /**

     * Método listarTodos.

     */

    public List<RegistroAuditoria> listarTodos() {
        return repository.findAllByOrderByFechaAccionDesc();
    }

    /**

     * Método listarPorEntidad.

     */

    public List<RegistroAuditoria> listarPorEntidad(String entidad) {
        return repository.findByEntidadOrderByFechaAccionDesc(entidad);
    }

    /**

     * Método listarPorUsuario.

     */

    public List<RegistroAuditoria> listarPorUsuario(String correo) {
        return repository.findByUsuarioCorreoOrderByFechaAccionDesc(correo);
    }
}

