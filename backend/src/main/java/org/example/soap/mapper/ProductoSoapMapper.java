package org.example.soap.mapper;

import org.example.modules.categorias.entity.Categoria;
import org.example.modules.productos.entity.Producto;
import org.example.soap.schema.ProductoSoap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductoSoapMapper {

    public ProductoSoap toSoap(Producto producto) {
        if (producto == null) return null;
        ProductoSoap soap = new ProductoSoap();
        soap.setId(producto.getId());
        soap.setNombre(producto.getNombre());
        if (producto.getCategoria() != null) {
            soap.setCategoria(producto.getCategoria().getNombre());
        }
        if (producto.getPrecio() != null) {
            soap.setPrecio(producto.getPrecio());
        }
        soap.setStock(producto.getStock() != null ? producto.getStock() : 0);
        soap.setMarca(producto.getMarca());
        soap.setDescripcion(producto.getDescripcion());
        soap.setImagenUrl(producto.getImagenUrl());
        return soap;
    }

    public Producto toEntity(ProductoSoap soap, Categoria categoria) {
        if (soap == null) return null;
        Producto producto = new Producto();
        producto.setNombre(soap.getNombre());
        producto.setCategoria(categoria);
        producto.setPrecio(soap.getPrecio() != null ? soap.getPrecio() : BigDecimal.ZERO);
        producto.setStock(soap.getStock());
        producto.setMarca(soap.getMarca());
        producto.setDescripcion(soap.getDescripcion());
        producto.setImagenUrl(soap.getImagenUrl());
        producto.setStockMinimo(5);
        producto.setStockMaximo(100);
        producto.setAlertaStockBajo(producto.getStock() < producto.getStockMinimo());
        return producto;
    }
}
