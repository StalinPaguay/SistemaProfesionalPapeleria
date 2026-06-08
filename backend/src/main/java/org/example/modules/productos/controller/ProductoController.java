package org.example.modules.productos.controller;

import org.example.modules.productos.entity.Producto;

import org.example.modules.productos.dto.ProductoDTO;
import org.example.modules.productos.service.interfaces.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
/**
 * Controlador REST para manejar las peticiones de Producto.
 */
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * Método listar.
     */
    public List<ProductoDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Método crear.
     */
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO nuevo = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Método actualizar.
     */
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        ProductoDTO actualizado = service.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Método eliminar.
     */
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertas/bajo-stock")
    /**
     * Método obtenerBajoStock.
     */
    public ResponseEntity<List<ProductoDTO>> obtenerBajoStock() {
        return ResponseEntity.ok(service.obtenerBajoStock());
    }

    @GetMapping("/alertas/agotados")
    /**
     * Método obtenerAgotados.
     */
    public ResponseEntity<List<ProductoDTO>> obtenerAgotados() {
        return ResponseEntity.ok(service.obtenerAgotados());
    }
}
