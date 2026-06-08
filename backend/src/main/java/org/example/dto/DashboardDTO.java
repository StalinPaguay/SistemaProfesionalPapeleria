package org.example.dto;

import java.math.BigDecimal;

/**
 * Objeto de Transferencia de Datos (DTO) para Dashboard.
 */
public class DashboardDTO {

    private Long totalProductos;
    private Long totalCategorias;
    private Long totalProveedores;
    private Long totalClientes;
    private Long totalVentas;
    private BigDecimal ingresosTotales;
    private Long productosAgotados;
    private Long productosBajoStock;

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Long getTotalProductos() { return totalProductos; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTotalProductos(Long totalProductos) { this.totalProductos = totalProductos; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getTotalCategorias() { return totalCategorias; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTotalCategorias(Long totalCategorias) { this.totalCategorias = totalCategorias; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getTotalProveedores() { return totalProveedores; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTotalProveedores(Long totalProveedores) { this.totalProveedores = totalProveedores; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getTotalClientes() { return totalClientes; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTotalClientes(Long totalClientes) { this.totalClientes = totalClientes; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getTotalVentas() { return totalVentas; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setTotalVentas(Long totalVentas) { this.totalVentas = totalVentas; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public BigDecimal getIngresosTotales() { return ingresosTotales; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setIngresosTotales(BigDecimal ingresosTotales) { this.ingresosTotales = ingresosTotales; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getProductosAgotados() { return productosAgotados; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProductosAgotados(Long productosAgotados) { this.productosAgotados = productosAgotados; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public Long getProductosBajoStock() { return productosBajoStock; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setProductosBajoStock(Long productosBajoStock) { this.productosBajoStock = productosBajoStock; }
}

