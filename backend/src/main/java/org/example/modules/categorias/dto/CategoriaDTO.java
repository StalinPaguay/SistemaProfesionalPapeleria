package org.example.modules.categorias.dto;

import org.example.modules.categorias.entity.Categoria;

import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de Transferencia de Datos (DTO) para Categoria.
 */
public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;

    public CategoriaDTO() {}
    public CategoriaDTO(Long id, String nombre) { this.id = id; this.nombre = nombre; }

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

