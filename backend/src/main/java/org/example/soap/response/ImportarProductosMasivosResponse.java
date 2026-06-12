package org.example.soap.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://example.org/soap/productos", propOrder = {
    "mensaje", "cantidadImportada"
})
@XmlRootElement(name = "importarProductosMasivosResponse", namespace = "http://example.org/soap/productos")
public class ImportarProductosMasivosResponse {
    @XmlElement(required = true, namespace = "http://example.org/soap/productos")
    private String mensaje;
    @XmlElement(namespace = "http://example.org/soap/productos")
    private int cantidadImportada;

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public int getCantidadImportada() { return cantidadImportada; }
    public void setCantidadImportada(int cantidadImportada) { this.cantidadImportada = cantidadImportada; }
}
