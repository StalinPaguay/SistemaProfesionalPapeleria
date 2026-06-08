package org.example.modules.ventas.repository;

import org.example.modules.ventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Interfaz que define las operaciones para VentaRepository.
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
