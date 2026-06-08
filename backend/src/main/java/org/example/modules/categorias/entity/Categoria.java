package org.example.modules.categorias.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categorias")
/**
 * Entidad que representa a Categoria.
 */
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String nombre;

    public Categoria() {}
    public Categoria(Long id, String nombre) { this.id = id; this.nombre = nombre; }

    /**

     * Obtiene el valor.

     * @return el valor

     */

    public Long getId() { return id; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setId(Long id) { this.id = id; }
    /**
     * Obtiene el valor.
     * @return el valor
     */
    public String getNombre() { return nombre; }
    /**
     * Establece el valor.
     * @param valor el valor a establecer
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
}

