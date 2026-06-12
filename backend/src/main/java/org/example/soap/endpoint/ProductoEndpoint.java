package org.example.soap.endpoint;

import org.example.modules.categorias.entity.Categoria;
import org.example.modules.categorias.repository.CategoriaRepository;
import org.example.modules.productos.entity.Producto;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.soap.mapper.ProductoSoapMapper;
import org.example.soap.request.ImportarProductosMasivosRequest;
import org.example.soap.request.ObtenerProductosRequest;
import org.example.soap.response.ImportarProductosMasivosResponse;
import org.example.soap.response.ObtenerProductosResponse;
import org.example.soap.schema.ProductoSoap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class ProductoEndpoint {

    private static final String NAMESPACE_URI = "http://example.org/soap/productos";

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoSoapMapper productoSoapMapper;

    @Autowired
    public ProductoEndpoint(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProductoSoapMapper productoSoapMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoSoapMapper = productoSoapMapper;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerProductosRequest")
    @ResponsePayload
    public ObtenerProductosResponse obtenerProductos(@RequestPayload ObtenerProductosRequest request) {
        ObtenerProductosResponse response = new ObtenerProductosResponse();
        List<Producto> productos = productoRepository.findAll();
        for (Producto p : productos) {
            response.getProductos().add(productoSoapMapper.toSoap(p));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "importarProductosMasivosRequest")
    @ResponsePayload
    @Transactional
    public ImportarProductosMasivosResponse importarProductosMasivos(@RequestPayload ImportarProductosMasivosRequest request) {
        ImportarProductosMasivosResponse response = new ImportarProductosMasivosResponse();
        List<Producto> nuevosProductos = new ArrayList<>();

        for (ProductoSoap soap : request.getProductos()) {
            String nombreCategoria = soap.getCategoria();
            if (nombreCategoria == null || nombreCategoria.trim().isEmpty()) {
                nombreCategoria = "Sin Categoría";
            }
            final String catName = nombreCategoria;
            Categoria categoria = categoriaRepository.findByNombre(catName)
                .orElseGet(() -> {
                    Categoria nueva = new Categoria();
                    nueva.setNombre(catName);
                    return categoriaRepository.save(nueva);
                });
            Producto p = productoSoapMapper.toEntity(soap, categoria);
            nuevosProductos.add(p);
        }

        productoRepository.saveAll(nuevosProductos);
        response.setMensaje("Importación masiva exitosa.");
        response.setCantidadImportada(nuevosProductos.size());
        return response;
    }
}
