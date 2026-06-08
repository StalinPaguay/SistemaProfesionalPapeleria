package org.example.modules.categorias.controller;

import org.example.modules.categorias.entity.Categoria;

import org.example.modules.categorias.dto.CategoriaDTO;
import org.example.modules.categorias.service.interfaces.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@PreAuthorize("hasRole('ADMINISTRADOR')")
/**
 * Controlador REST para manejar las peticiones de Categoria.
 */
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * Método listar.
     */
    public List<CategoriaDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    /**
     * Método obtenerPorId.
     */
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    /**
     * Método crear.
     */
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaDTO dto) {
        CategoriaDTO nueva = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    /**
     * Método actualizar.
     */
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        CategoriaDTO actualizada = service.update(id, dto);
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

