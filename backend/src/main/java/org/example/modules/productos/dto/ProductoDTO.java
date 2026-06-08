package org.example.modules.productos.dto;

import org.example.modules.productos.entity.Producto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos (DTO) para Producto.
 */
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El campo precio es obligatorio")
    private BigDecimal precio;

    @NotNull(message = "El campo stock es obligatorio")
    private Integer stock;

    private Integer stockMinimo = 5;
    private Integer stockMaximo = 100;

    @NotNull(message = "El campo categoriaId es obligatorio")
    private Long categoriaId;

    // Campo de solo lectura para respuestas
    @NotBlank(message = "El campo categoriaNombre es obligatorio")
    private String categoriaNombre;

    public ProductoDTO() {}

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Long getId() { return id; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setId(Long id) { this.id = id; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getNombre() { return nombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public BigDecimal getPrecio() { return precio; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Integer getStock() { return stock; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setStock(Integer stock) { this.stock = stock; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Integer getStockMinimo() { return stockMinimo; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Integer getStockMaximo() { return stockMaximo; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setStockMaximo(Integer stockMaximo) { this.stockMaximo = stockMaximo; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getCategoriaId() { return categoriaId; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getCategoriaNombre() { return categoriaNombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
}
