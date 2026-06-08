package org.example.modules.proveedores.controller;

import org.example.modules.proveedores.entity.Proveedor;

import org.example.modules.proveedores.dto.ProveedorDTO;
import org.example.modules.proveedores.service.interfaces.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@PreAuthorize("hasRole('ADMINISTRADOR')")
/**
 * Controlador REST para manejar las peticiones de Proveedor.
 */
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * Método listar.
     */
    public List<ProveedorDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<ProveedorDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    /**
     * Método crear.
     */
    public ResponseEntity<ProveedorDTO> crear(@Valid @RequestBody ProveedorDTO dto) {
        ProveedorDTO nuevo = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    /**
     * Método actualizar.
     */
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorDTO dto) {
        ProveedorDTO actualizado = service.update(id, dto);
        return ResponseEntity.ok(actualizado);
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
