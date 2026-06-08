package org.example.modules.auditoria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_auditoria")
/**
 * Entidad que representa a RegistroAuditoria.
 */
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo accion es obligatorio")
    private String accion;

    @NotBlank(message = "El campo entidad es obligatorio")
    private String entidad;

    @NotNull(message = "El campo entidadId es obligatorio")
    @Column(name = "entidad_id")
    private Long entidadId;

    @NotBlank(message = "El campo usuarioCorreo es obligatorio")
    @Column(name = "usuario_correo")
    private String usuarioCorreo;

    private String detalles;

    @NotNull(message = "El campo fechaAccion es obligatorio")
    @Column(name = "fecha_accion")
    private LocalDateTime fechaAccion;

    @PrePersist
    public void prePersist() {
        if (this.fechaAccion == null) {
            this.fechaAccion = LocalDateTime.now();
        }
    }

    public RegistroAuditoria() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }

    public Long getEntidadId() { return entidadId; }
    public void setEntidadId(Long entidadId) { this.entidadId = entidadId; }

    public String getUsuarioCorreo() { return usuarioCorreo; }
    public void setUsuarioCorreo(String usuarioCorreo) { this.usuarioCorreo = usuarioCorreo; }

    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }

    public LocalDateTime getFechaAccion() { return fechaAccion; }
    public void setFechaAccion(LocalDateTime fechaAccion) { this.fechaAccion = fechaAccion; }
}
