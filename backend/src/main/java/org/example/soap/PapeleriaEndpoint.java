package org.example.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
/**
 * Entidad que representa a PapeleriaEndpoint.
 */
public class PapeleriaEndpoint {

    private static final String NAMESPACE_URI = "http://example.org/soap";
    private final PapeleriaSoapService soapService;

    public PapeleriaEndpoint(PapeleriaSoapService soapService) {
        this.soapService = soapService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConsultarStockRequest")
    @ResponsePayload
    /**
     * Método consultarStock.
     */
    public StockDisponibilidadResponse consultarStock(@RequestPayload ConsultarStockRequest request) {
        return soapService.consultarDisponibilidad(request);
    }
}
