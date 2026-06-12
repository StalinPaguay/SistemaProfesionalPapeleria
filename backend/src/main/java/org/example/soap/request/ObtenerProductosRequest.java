package org.example.soap.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://example.org/soap/productos")
@XmlRootElement(name = "obtenerProductosRequest", namespace = "http://example.org/soap/productos")
public class ObtenerProductosRequest {
}
