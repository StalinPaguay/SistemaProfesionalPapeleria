package org.example.soap.response;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.example.soap.schema.ProductoSoap;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://example.org/soap/productos", propOrder = {
    "productos"
})
@XmlRootElement(name = "obtenerProductosResponse", namespace = "http://example.org/soap/productos")
public class ObtenerProductosResponse {
    @XmlElement(namespace = "http://example.org/soap/productos")
    private List<ProductoSoap> productos;

    public List<ProductoSoap> getProductos() {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        return this.productos;
    }
}
