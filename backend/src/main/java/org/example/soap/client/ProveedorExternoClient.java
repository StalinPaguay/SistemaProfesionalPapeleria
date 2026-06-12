package org.example.soap.client;

import org.example.soap.request.ObtenerProductosRequest;
import org.example.soap.response.ObtenerProductosResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class ProveedorExternoClient {
    private static final Logger log = LoggerFactory.getLogger(ProveedorExternoClient.class);
    private final WebServiceTemplate webServiceTemplate;

    public ProveedorExternoClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public ObtenerProductosResponse fetchProductosExternos() {
        log.info("Llamando al servicio SOAP externo para obtener productos...");
        ObtenerProductosRequest request = new ObtenerProductosRequest();
        try {
            ObtenerProductosResponse response = (ObtenerProductosResponse) webServiceTemplate.marshalSendAndReceive(request);
            log.info("Productos recibidos exitosamente.");
            return response;
        } catch (Exception e) {
            log.error("Error al consumir SOAP externo: {}", e.getMessage());
            return null;
        }
    }
}
