package org.example.modules.dashboard.dto;

import java.math.BigDecimal;

public class DashboardStats {
    private long totalVentasMes;
    private long totalClientes;
    private long productosEnRiesgo;
    private BigDecimal gananciasMes;

    public long getTotalVentasMes() { return totalVentasMes; }
    public void setTotalVentasMes(long totalVentasMes) { this.totalVentasMes = totalVentasMes; }

    public long getTotalClientes() { return totalClientes; }
    public void setTotalClientes(long totalClientes) { this.totalClientes = totalClientes; }

    public long getProductosEnRiesgo() { return productosEnRiesgo; }
    public void setProductosEnRiesgo(long productosEnRiesgo) { this.productosEnRiesgo = productosEnRiesgo; }

    public BigDecimal getGananciasMes() { return gananciasMes; }
    public void setGananciasMes(BigDecimal gananciasMes) { this.gananciasMes = gananciasMes; }
}
