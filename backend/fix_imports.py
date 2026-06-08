import os
import re

base_dir = r"c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example"

# Map of simple class name to its fully qualified name
import_map = {
    "Usuario": "org.example.modules.auth.entity.Usuario",
    "Cliente": "org.example.modules.clientes.entity.Cliente",
    "Producto": "org.example.modules.productos.entity.Producto",
    "Venta": "org.example.modules.ventas.entity.Venta",
    "VentaRepository": "org.example.modules.ventas.repository.VentaRepository",
    "BaseService": "org.example.common.service.BaseService",
    "DetalleCompra": "org.example.modules.detallecompra.entity.DetalleCompra",
    "Proveedor": "org.example.modules.proveedores.entity.Proveedor",
    "Categoria": "org.example.modules.categorias.entity.Categoria",
    "ProductoRepository": "org.example.modules.productos.repository.ProductoRepository",
    "CategoriaRepository": "org.example.modules.categorias.repository.CategoriaRepository",
    "ProveedorRepository": "org.example.modules.proveedores.repository.ProveedorRepository",
    "ClienteRepository": "org.example.modules.clientes.repository.ClienteRepository",
    "Compra": "org.example.modules.compras.entity.Compra",
    "BaseServiceImpl": "org.example.common.service.BaseServiceImpl"
}

def fix_imports():
    for root, _, files in os.walk(base_dir):
        for f in files:
            if f.endswith(".java"):
                file_path = os.path.join(root, f)
                with open(file_path, "r", encoding="utf-8") as file:
                    content = file.read()
                
                original_content = content
                
                # Check for each class and add import if needed
                for class_name, full_pkg in import_map.items():
                    # If the class is used in the file as a word
                    if re.search(rf'\b{class_name}\b', content):
                        import_stmt = f"import {full_pkg};"
                        pkg_stmt = f"package {full_pkg.rsplit('.', 1)[0]};"
                        
                        if import_stmt not in content and pkg_stmt not in content:
                            content = re.sub(
                                r'(package\s+[\w\.]+;)',
                                r'\1\n\n' + import_stmt,
                                content,
                                count=1
                            )
                
                if content != original_content:
                    with open(file_path, "w", encoding="utf-8") as file:
                        file.write(content)
                        print(f"Fixed imports in {f}")

fix_imports()
