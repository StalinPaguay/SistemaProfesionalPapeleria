package org.example.modules.inventario.controller;

import jakarta.validation.Valid;
import org.example.modules.inventario.dto.MovimientoInventarioDTO;
import org.example.modules.inventario.service.interfaces.MovimientoInventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
/**
 * Controlador REST para manejar las peticiones de MovimientoInventario.
 */
public class MovimientoInventarioController {

    private final MovimientoInventarioService service;

    public MovimientoInventarioController(MovimientoInventarioService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Método listar.
     */
    public List<MovimientoInventarioDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/producto/{productoId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Método listarPorProducto.
     */
    public ResponseEntity<List<MovimientoInventarioDTO>> listarPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.buscarPorProducto(productoId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<MovimientoInventarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Método registrarMovimiento.
     */
    public ResponseEntity<MovimientoInventarioDTO> registrarMovimiento(@Valid @RequestBody MovimientoInventarioDTO dto) {
        MovimientoInventarioDTO nuevo = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}
