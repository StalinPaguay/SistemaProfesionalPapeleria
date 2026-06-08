package org.example.modules.productos.repository;
import org.example.modules.productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface ProductoRepository extends JpaRepository<Producto, Long> {}
