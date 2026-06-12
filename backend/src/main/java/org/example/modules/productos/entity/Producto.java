package org.example.modules.productos.entity;

import org.example.modules.categorias.entity.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El campo stockMinimo es obligatorio")
    @Column(name = "stock_minimo", columnDefinition = "integer default 5")
    private Integer stockMinimo = 5;

    @NotNull(message = "El campo stockMaximo es obligatorio")
    @Column(name = "stock_maximo", columnDefinition = "integer default 100")
    private Integer stockMaximo = 100;

    @Column(name = "alerta_stock_bajo")
    private Boolean alertaStockBajo = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private BigDecimal precio;

    @Column(name = "marca")
    private String marca;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "imagen_url", length = 1000)
    private String imagenUrl;

    public Producto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public Integer getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(Integer stockMaximo) { this.stockMaximo = stockMaximo; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Boolean getAlertaStockBajo() { return alertaStockBajo; }
    public void setAlertaStockBajo(Boolean alertaStockBajo) { this.alertaStockBajo = alertaStockBajo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
