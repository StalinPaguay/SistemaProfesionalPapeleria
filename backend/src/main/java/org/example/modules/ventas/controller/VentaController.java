package org.example.modules.ventas.controller;

import org.example.modules.ventas.entity.Venta;

import jakarta.validation.Valid;
import org.example.modules.ventas.dto.VentaDTO;
import org.example.modules.ventas.service.interfaces.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
/**
 * Controlador REST para manejar las peticiones de Venta.
 */
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Método listar.
     */
    public List<VentaDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Método registrar.
     */
    public ResponseEntity<VentaDTO> registrar(@Valid @RequestBody VentaDTO dto) {
        VentaDTO nueva = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
}
