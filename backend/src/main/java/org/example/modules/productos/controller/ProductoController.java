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
 * Controlador REST que gestiona las operaciones relacionadas con el catálogo de productos.
 * <p>
 * Expone endpoints seguros (protegidos por JWT) para la administración del inventario,
 * así como endpoints de acceso general para la navegación del cliente en la tienda.
 * Implementa el patrón DTO (Data Transfer Object) para el intercambio de datos.
 * </p>
 *
 * @author Equipo de Desarrollo PaperDS
 * @version 1.0.0
 * @since 1.0
 */
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    /**
     * Recupera el catálogo completo de productos registrados en el sistema.
     * Endpoint de acceso público utilizado principalmente por el portal de tienda online.
     *
     * @return Una lista de {@link ProductoDTO} que contiene la información pública de los productos.
     */
    public List<ProductoDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    /**
     * Obtiene los detalles específicos de un producto a partir de su identificador único.
     *
     * @param id El identificador único (Primary Key) del producto a consultar.
     * @return {@link ResponseEntity} que contiene el {@link ProductoDTO} si se encuentra, 
     *         o un código de estado 404 (Not Found) en caso contrario.
     */
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Registra un nuevo producto en el catálogo del sistema.
     * <p>
     * Este endpoint requiere privilegios de ADMINISTRADOR debido a que impacta el inventario central.
     * </p>
     *
     * @param dto El objeto {@link ProductoDTO} que contiene los metadatos del nuevo producto.
     * @return {@link ResponseEntity} con el producto recién creado y el código de estado 201 (Created).
     */
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO nuevo = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Actualiza la información y existencias de un producto existente.
     * Operación restringida exclusivamente al rol de ADMINISTRADOR.
     *
     * @param id  El identificador único del producto que será modificado.
     * @param dto Objeto {@link ProductoDTO} con la información actualizada a persistir.
     * @return {@link ResponseEntity} conteniendo el producto modificado tras su paso por la base de datos.
     */
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        ProductoDTO actualizado = service.update(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    /**
     * Elimina lógicamente o físicamente un producto del catálogo del sistema.
     * Esta acción es irreversible y está estrictamente reservada para administradores.
     *
     * @param id El identificador del producto a remover.
     * @return {@link ResponseEntity} vacío con estado 204 (No Content) confirmando la eliminación.
     */
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertas/bajo-stock")
    /**
     * Consulta el inventario para identificar productos que han caído por debajo de su umbral mínimo de stock.
     * Útil para módulos de inteligencia de negocios y abastecimiento.
     *
     * @return Lista de productos en estado crítico o de reabastecimiento recomendado.
     */
    public ResponseEntity<List<ProductoDTO>> obtenerBajoStock() {
        return ResponseEntity.ok(service.obtenerBajoStock());
    }

    @GetMapping("/alertas/agotados")
    /**
     * Identifica los productos que se encuentran con stock igual o menor a cero (0).
     * Endpoint crítico para la desactivación temporal de ventas en el frontend.
     *
     * @return Lista de productos agotados en el inventario actual.
     */
    public ResponseEntity<List<ProductoDTO>> obtenerAgotados() {
        return ResponseEntity.ok(service.obtenerAgotados());
    }
}
