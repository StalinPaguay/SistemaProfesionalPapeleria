package org.example.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StockDisponibilidadResponse", namespace = "http://example.org/soap")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Entidad que representa a StockDisponibilidadResponse.
 */
public class StockDisponibilidadResponse {

    @XmlElement(required = true)
    private Long productoId;

    @XmlElement(required = true)
    private boolean disponible;

    @XmlElement(required = true)
    private Integer stockActual;

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

    /**

     * Método isDisponible.

     */

    public boolean isDisponible() {
        return disponible;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Integer getStockActual() {
        return stockActual;
    }

    /**

     * Establece el valor.

     * @param valor el valor a establecer

     */

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }
}
