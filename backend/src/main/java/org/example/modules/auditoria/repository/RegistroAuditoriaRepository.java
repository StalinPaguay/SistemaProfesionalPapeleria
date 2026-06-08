package org.example.modules.auditoria.repository;

import org.example.modules.auditoria.entity.RegistroAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistroAuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {
    List<RegistroAuditoria> findAllByOrderByFechaAccionDesc();
    List<RegistroAuditoria> findByEntidadOrderByFechaAccionDesc(String entidad);
    List<RegistroAuditoria> findByUsuarioCorreoOrderByFechaAccionDesc(String usuarioCorreo);
}
