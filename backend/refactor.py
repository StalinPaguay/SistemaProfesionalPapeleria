import os
import re

base_dir = r"c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example"

modules = {
    "auth": ["Usuario", "Rol", "AuthResponse", "LoginRequest", "RegisterRequest", "AuthController", "AuthService", "UsuarioRepository", "UserDetailsServiceImpl", "JwtAuthenticationFilter", "JwtUtil", "SecurityConfig"],
    "productos": ["Producto", "ProductoDTO", "ProductoController", "ProductoService", "ProductoServiceImpl", "ProductoRepository", "ProductoMapper"],
    "categorias": ["Categoria", "CategoriaDTO", "CategoriaController", "CategoriaService", "CategoriaServiceImpl", "CategoriaRepository", "CategoriaMapper"],
    "proveedores": ["Proveedor", "ProveedorDTO", "ProveedorController", "ProveedorService", "ProveedorServiceImpl", "ProveedorRepository", "ProveedorMapper"],
    "compras": ["Compra", "CompraDTO", "CompraController", "CompraService", "CompraServiceImpl", "CompraRepository", "CompraMapper"],
    "detallecompra": ["DetalleCompra", "DetalleCompraDTO", "DetalleCompraController", "DetalleCompraService", "DetalleCompraServiceImpl", "DetalleCompraRepository", "DetalleCompraMapper"],
    "ventas": ["Venta", "VentaDTO", "VentaController", "VentaService", "VentaServiceImpl", "VentaRepository", "VentaMapper", "DetalleVenta", "DetalleVentaDTO", "DetalleVentaMapper", "DetalleVentaRepository"],
    "clientes": ["Cliente", "ClienteDTO", "ClienteController", "ClienteService", "ClienteServiceImpl", "ClienteRepository", "ClienteMapper"],
    "auditoria": ["RegistroAuditoria", "AuditoriaController", "AuditoriaService", "RegistroAuditoriaRepository"],
    "inventario": ["MovimientoInventario", "TipoMovimiento", "MovimientoInventarioDTO", "MovimientoInventarioController", "MovimientoInventarioService", "MovimientoInventarioServiceImpl", "MovimientoInventarioRepository", "MovimientoInventarioMapper"],
    "dashboard": ["DashboardController"],
    "cookie": ["CookieController"]
}

common = ["BaseService", "BaseServiceImpl"]
exceptions = ["RecursoNoEncontradoException", "ReglaNegocioException", "GlobalExceptionHandler"]
config = ["CorsConfig"]

def get_target_info(file_name, base_name):
    if base_name in common:
        return "org.example.common.service"
    if base_name in exceptions:
        return "org.example.exception"
    if base_name in config:
        return "org.example.config"
    
    for m, classes in modules.items():
        if base_name in classes:
            if file_name.endswith("Controller.java"): sub = "controller"
            elif file_name.endswith("ServiceImpl.java"): sub = "service.implementation"
            elif file_name.endswith("Service.java"): sub = "service.interfaces"
            elif file_name.endswith("Repository.java"): sub = "repository"
            elif file_name.endswith("DTO.java"): sub = "dto"
            elif file_name.endswith("Request.java") or file_name.endswith("Response.java"): sub = "dto"
            elif file_name.endswith("Mapper.java"): sub = "mapper"
            elif "SecurityConfig" in file_name or "Jwt" in file_name or "UserDetails" in file_name:
                return "org.example.security"
            else: sub = "entity"
            return f"org.example.modules.{m}.{sub}"
    return None

class_to_pkg = {}
all_java_files = []

for root, _, files in os.walk(base_dir):
    for f in files:
        if f.endswith(".java") and f != "Main.java":
            all_java_files.append(os.path.join(root, f))
            base_name = f[:-5]
            pkg = get_target_info(f, base_name)
            if pkg:
                class_to_pkg[base_name] = pkg

for file_path in all_java_files:
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()

    base_name = os.path.basename(file_path)[:-5]
    pkg = class_to_pkg.get(base_name)
    if not pkg:
        pkg = get_target_info(os.path.basename(file_path), base_name)

    if pkg:
        content = re.sub(r'(?m)^package\s+org\.example\..*?;', f"package {pkg};", content)
        content = re.sub(r'(?m)^package\s+org\.example;', f"package {pkg};", content)

    for k, v in class_to_pkg.items():
        # Replace old imports
        old_imports = [
            f"org.example.entity.{k};",
            f"org.example.dto.{k};",
            f"org.example.controller.{k};",
            f"org.example.service.interfaces.{k};",
            f"org.example.service.implementation.{k};",
            f"org.example.repository.{k};",
            f"org.example.exception.{k};",
            f"org.example.mapper.{k};",
            f"org.example.security.{k};",
            f"org.example.config.{k};",
            f"org.example.dto.auth.{k};"
        ]
        new_import = f"{v}.{k};"
        for old in old_imports:
            content = content.replace(old, new_import)

    # Remove star imports from old entity packages just to be clean
    content = content.replace("import org.example.entity.*;", "")
    content = content.replace("import org.example.dto.*;", "")

    # For BaseService and BaseServiceImpl since they moved
    content = content.replace("org.example.service.interfaces.BaseService", "org.example.common.service.BaseService")
    content = content.replace("org.example.service.implementation.BaseServiceImpl", "org.example.common.service.BaseServiceImpl")

    with open(file_path, "w", encoding="utf-8") as f:
        f.write(content)

print("Python refactoring completed.")
