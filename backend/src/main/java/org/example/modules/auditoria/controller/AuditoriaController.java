package org.example.modules.auditoria.controller;

import org.example.modules.auditoria.entity.RegistroAuditoria;
import org.example.modules.auditoria.service.interfaces.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@PreAuthorize("hasRole('ADMINISTRADOR')")
/**
 * Controlador REST para manejar las peticiones de Auditoria.
 */
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    /**
     * Método listarTodos.
     */
    public ResponseEntity<List<RegistroAuditoria>> listarTodos() {
        return ResponseEntity.ok(auditoriaService.listarTodos());
    }

    @GetMapping("/entidad/{entidad}")
    /**
     * Método listarPorEntidad.
     */
    public ResponseEntity<List<RegistroAuditoria>> listarPorEntidad(@PathVariable String entidad) {
        return ResponseEntity.ok(auditoriaService.listarPorEntidad(entidad));
    }

    @GetMapping("/usuario/{correo}")
    /**
     * Método listarPorUsuario.
     */
    public ResponseEntity<List<RegistroAuditoria>> listarPorUsuario(@PathVariable String correo) {
        return ResponseEntity.ok(auditoriaService.listarPorUsuario(correo));
    }
}

