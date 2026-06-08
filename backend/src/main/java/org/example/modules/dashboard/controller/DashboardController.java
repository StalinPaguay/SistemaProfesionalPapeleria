package org.example.modules.dashboard.controller;

import org.example.modules.dashboard.dto.DashboardStats;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.ventas.repository.VentaRepository;
import org.example.modules.clientes.repository.ClienteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;

    public DashboardController(ProductoRepository productoRepository, VentaRepository ventaRepository, ClienteRepository clienteRepository) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/stats")
    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();
        
        // Simulación o consultas reales
        stats.setTotalVentasMes(ventaRepository.count());
        stats.setTotalClientes(clienteRepository.count());
        
        // Contar productos con alertaStockBajo == true
        stats.setProductosEnRiesgo(productoRepository.findAll().stream()
            .filter(p -> p.getAlertaStockBajo() != null && p.getAlertaStockBajo())
            .count());
            
        stats.setGananciasMes(BigDecimal.valueOf(15000.50)); // Simulado para el dashboard
        
        return stats;
    }
}
