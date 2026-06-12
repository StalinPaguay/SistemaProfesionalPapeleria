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
 * Controlador REST encargado de la gestión integral de las transacciones de ventas.
 * <p>
 * Facilita el registro de nuevas compras, la generación de detalles de venta
 * y la extracción del historial transaccional. Utiliza seguridad basada en roles
 * (JWT) para aislar las compras de clientes normales del panel administrativo.
 * </p>
 *
 * @author Equipo de Desarrollo PaperDS
 * @version 1.0.0
 * @since 1.0
 */
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Extrae el listado completo del historial de ventas registradas.
     * Operación accesible para auditores o clientes (con futuros filtros por ID).
     *
     * @return Lista estructurada de {@link VentaDTO} con metadatos de las transacciones.
     */
    public List<VentaDTO> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Consulta una transacción de venta individual basada en su folio o identificador.
     * Incluye los detalles desglosados (productos, cantidades, subtotales).
     *
     * @param id El identificador único de la transacción.
     * @return {@link ResponseEntity} envolviendo los detalles de la venta requerida.
     */
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO')")
    /**
     * Registra y procesa una nueva orden de compra en el sistema.
     * Este proceso desencadena la deducción automática del stock en el inventario.
     *
     * @param dto El payload de la compra que incluye el cliente, montos y desglose de artículos.
     * @return {@link ResponseEntity} confirmando la persistencia de la factura (Código 201).
     */
    public ResponseEntity<VentaDTO> registrar(@Valid @RequestBody VentaDTO dto) {
        VentaDTO nueva = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
}
