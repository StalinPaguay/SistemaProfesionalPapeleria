package org.example.modules.categorias.repository;
import org.example.modules.categorias.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}

