package org.example.modules.ventas.repository;

import org.example.modules.ventas.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Interfaz que define las operaciones para DetalleVentaRepository.
 */
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
