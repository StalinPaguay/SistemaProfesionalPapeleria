package org.example.modules.dashboard.controller;

import org.example.modules.dashboard.dto.DashboardStats;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.ventas.repository.VentaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import org.example.modules.productos.entity.Producto;
import org.example.modules.ventas.entity.Venta;
import java.util.List;

import org.example.modules.auth.repository.UsuarioRepository;
import org.example.modules.auth.entity.Rol;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardController(ProductoRepository productoRepository, VentaRepository ventaRepository, UsuarioRepository usuarioRepository) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/stats")
    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();
        
        List<Venta> ventas = ventaRepository.findAll();
        stats.setTotalVentasMes((long) ventas.size());
        
        // Contar cuentas de usuario que tienen el rol de USUARIO (clientes del sistema)
        long totalClientes = usuarioRepository.findAll().stream()
            .filter(u -> u.getRol() == Rol.USUARIO)
            .count();
        stats.setTotalClientes(totalClientes);
        
        List<Producto> productos = productoRepository.findAll();
        
        // Contar productos con stock en riesgo
        stats.setProductosEnRiesgo(productos.stream()
            .filter(p -> (p.getAlertaStockBajo() != null && p.getAlertaStockBajo()) || (p.getStock() <= p.getStockMinimo()))
            .count());
            
        // Calcular Ingresos: Suma de ventas totales - Suma del costo de inventario (dinero invertido)
        BigDecimal totalVentas = ventas.stream()
            .map(Venta::getTotal)
            .filter(t -> t != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal totalInventario = productos.stream()
            .map(p -> {
                BigDecimal precio = p.getPrecio() != null ? p.getPrecio() : BigDecimal.ZERO;
                return precio.multiply(BigDecimal.valueOf(p.getStock() != null ? p.getStock() : 0));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        stats.setGananciasMes(totalVentas.subtract(totalInventario));
        
        return stats;
    }
}
