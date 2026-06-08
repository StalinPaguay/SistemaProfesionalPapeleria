package org.example.modules.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.modules.inventario.entity.TipoMovimiento;
import java.time.LocalDateTime;

public class MovimientoInventarioDTO {

    private Long id;

    @NotNull(message = "El campo productoId es obligatorio")
    private Long productoId;
    
    private String productoNombre;
    
    @NotNull(message = "El campo tipoMovimiento es obligatorio")
    private TipoMovimiento tipoMovimiento;
    
    @NotNull(message = "El campo cantidad es obligatorio")
    private Integer cantidad;
    
    @NotNull(message = "El campo fechaMovimiento es obligatorio")
    private LocalDateTime fechaMovimiento;
    
    private Long responsableId;

    @NotBlank(message = "El campo responsableNombre es obligatorio")
    private String responsableNombre;
    
    @NotBlank(message = "El campo motivo es obligatorio")
    private String motivo;
    
    private Integer stockPrevio;

    @NotNull(message = "El campo stockActual es obligatorio")
    private Integer stockActual;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }

    public Long getResponsableId() { return responsableId; }
    public void setResponsableId(Long responsableId) { this.responsableId = responsableId; }

    public String getResponsableNombre() { return responsableNombre; }
    public void setResponsableNombre(String responsableNombre) { this.responsableNombre = responsableNombre; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Integer getStockPrevio() { return stockPrevio; }
    public void setStockPrevio(Integer stockPrevio) { this.stockPrevio = stockPrevio; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
}
