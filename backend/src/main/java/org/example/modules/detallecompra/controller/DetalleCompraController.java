package org.example.modules.detallecompra.controller;

import org.example.modules.detallecompra.entity.DetalleCompra;

import org.example.modules.detallecompra.dto.DetalleCompraDTO;
import org.example.modules.detallecompra.service.interfaces.DetalleCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/detalles-compra")
/**
 * Controlador REST para manejar las peticiones de DetalleCompra.
 */
public class DetalleCompraController {

    private final DetalleCompraService service;

    public DetalleCompraController(DetalleCompraService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * Método listar.
     */
    public List<DetalleCompraDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<DetalleCompraDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    /**
     * Método crear.
     */
    public ResponseEntity<DetalleCompraDTO> crear(@Valid @RequestBody DetalleCompraDTO dto) {
        DetalleCompraDTO nuevo = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    /**
     * Método actualizar.
     */
    public ResponseEntity<DetalleCompraDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DetalleCompraDTO dto) {
        DetalleCompraDTO actualizado = service.update(id, dto);
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
