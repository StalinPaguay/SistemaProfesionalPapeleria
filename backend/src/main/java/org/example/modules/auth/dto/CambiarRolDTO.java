package org.example.modules.auth.dto;

import jakarta.validation.constraints.NotNull;
import org.example.modules.auth.entity.Rol;

public class CambiarRolDTO {
    @NotNull(message = "El nuevo rol es obligatorio")
    private Rol rol;

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
