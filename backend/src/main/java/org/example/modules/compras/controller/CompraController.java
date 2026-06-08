package org.example.modules.compras.controller;

import org.example.modules.compras.entity.Compra;

import org.example.modules.compras.dto.CompraDTO;
import org.example.modules.compras.service.interfaces.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@PreAuthorize("hasRole('ADMINISTRADOR')")
/**
 * Controlador REST para manejar las peticiones de Compra.
 */
public class CompraController {

    private final CompraService service;

    public CompraController(CompraService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * Método listar.
     */
    public List<CompraDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<CompraDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    /**
     * Método crear.
     */
    public ResponseEntity<CompraDTO> crear(@Valid @RequestBody CompraDTO dto) {
        CompraDTO nueva = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    /**
     * Método actualizar.
     */
    public ResponseEntity<CompraDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CompraDTO dto) {
        CompraDTO actualizada = service.update(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    /**
     * Método eliminar.
     */
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

