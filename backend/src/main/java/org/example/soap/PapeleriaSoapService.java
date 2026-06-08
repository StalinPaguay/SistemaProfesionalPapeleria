package org.example.soap;

import org.springframework.stereotype.Service;

@Service
/**
 * Servicio para gestionar PapeleriaSoap.
 */
public class PapeleriaSoapService {

    /**

     * Método consultarDisponibilidad.

     */

    public StockDisponibilidadResponse consultarDisponibilidad(ConsultarStockRequest request) {
        // Implementación simulada o llamada al ProductoService real
        StockDisponibilidadResponse response = new StockDisponibilidadResponse();
        response.setProductoId(request.getProductoId());
        response.setDisponible(true);
        response.setStockActual(100);
        return response;
    }
}
