package org.example.modules.auditoria.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.modules.auditoria.entity.RegistroAuditoria;
import org.example.modules.auditoria.repository.RegistroAuditoriaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuditoriaAspect {

    private final RegistroAuditoriaRepository auditoriaRepository;

    public AuditoriaAspect(RegistroAuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    // Intercept all save, update, delete methods in Controllers or Services
    // Let's target all public methods in controller package that don't start with 'get'
    @Pointcut("execution(* org.example.modules.*.controller.*.*(..))")
    public void controllerMethods() {}

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        
        // Skip purely read methods
        if (methodName.startsWith("get") || methodName.startsWith("find") || methodName.startsWith("obtener") || methodName.startsWith("listar")) {
            return;
        }

        String entityName = joinPoint.getTarget().getClass().getSimpleName().replace("Controller", "");
        
        String action = "UPDATE/CREATE";
        if (methodName.toLowerCase().contains("delete") || methodName.toLowerCase().contains("eliminar")) {
            action = "DELETE";
        }

        String userEmail = "ANONIMO";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName() != null) {
            userEmail = auth.getName();
        }

        RegistroAuditoria audit = new RegistroAuditoria();
        audit.setAccion(action);
        audit.setEntidad(entityName);
        audit.setEntidadId(0L); // Normally we'd extract ID from the result or args
        audit.setUsuarioCorreo(userEmail);
        audit.setDetalles("Method: " + methodName + " | Args: " + Arrays.toString(joinPoint.getArgs()));

        auditoriaRepository.save(audit);
    }
}
