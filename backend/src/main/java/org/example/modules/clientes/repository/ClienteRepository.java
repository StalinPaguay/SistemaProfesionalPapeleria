package org.example.modules.clientes.repository;

import org.example.modules.clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * Interfaz que define las operaciones para ClienteRepository.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

