package org.example.soap.scheduler;

import org.example.soap.client.ProveedorExternoClient;
import org.example.soap.request.ImportarProductosMasivosRequest;
import org.example.soap.response.ImportarProductosMasivosResponse;
import org.example.soap.endpoint.ProductoEndpoint;
import org.example.soap.response.ObtenerProductosResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InventarioSyncScheduler {
    private static final Logger log = LoggerFactory.getLogger(InventarioSyncScheduler.class);
    private final ProveedorExternoClient proveedorExternoClient;
    private final ProductoEndpoint productoEndpoint;

    public InventarioSyncScheduler(ProveedorExternoClient proveedorExternoClient, ProductoEndpoint productoEndpoint) {
        this.proveedorExternoClient = proveedorExternoClient;
        this.productoEndpoint = productoEndpoint;
    }

    // Se ejecuta 30 segundos despues de arrancar, y luego cada 2 minutos
    @Scheduled(initialDelay = 30000, fixedRate = 120000)
    public void syncProductosExternos() {
        log.info("[SOAP SYNC] Iniciando sincronización automática con proveedor externo...");
        ObtenerProductosResponse response = proveedorExternoClient.fetchProductosExternos();
        
        if (response != null && response.getProductos() != null && !response.getProductos().isEmpty()) {
            log.info("[SOAP SYNC] Se obtuvieron {} productos. Procesando importación masiva interna...", response.getProductos().size());
            ImportarProductosMasivosRequest request = new ImportarProductosMasivosRequest();
            request.getProductos().addAll(response.getProductos());
            
            ImportarProductosMasivosResponse res = productoEndpoint.importarProductosMasivos(request);
            log.info("[SOAP SYNC] Sincronización completada. {} Importados: {}", res.getMensaje(), res.getCantidadImportada());
        } else {
            log.warn("[SOAP SYNC] No se encontraron productos o hubo un error en la conexión.");
        }
    }
}
