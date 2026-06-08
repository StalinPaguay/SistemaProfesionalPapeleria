package org.example.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConsultarStockRequest", namespace = "http://example.org/soap")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Entidad que representa a ConsultarStockRequest.
 */
public class ConsultarStockRequest {

    @XmlElement(required = true)
    private Long productoId;

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Long getProductoId() {
        return productoId;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}
