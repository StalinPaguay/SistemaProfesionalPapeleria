import os
import re

files_to_patch = {
    r'c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example\modules\compras\dto\CompraDTO.java':
    '    private java.time.LocalDate fecha;\n',
    
    r'c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example\modules\ventas\dto\DetalleVentaDTO.java':
    '    private java.math.BigDecimal precioUnitario;\n',

    r'c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example\modules\ventas\entity\DetalleVenta.java':
    '    @jakarta.validation.constraints.NotNull(message = "El precio es obligatorio")\n    private java.math.BigDecimal precioUnitario;\n',

    r'c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example\modules\ventas\dto\VentaDTO.java':
    '    private java.time.LocalDate fechaVenta;\n    private String clienteNombre;\n    private Long vendedorId;\n',
    
    r'c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example\modules\inventario\entity\MovimientoInventario.java':
    '    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)\n    private TipoMovimiento tipoMovimiento;\n    private Integer cantidad;\n    private Integer stockPrevio;\n'
}

for filepath, insertion in files_to_patch.items():
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Insert right after public class X {
    class_def_match = re.search(r'public class \w+(?: extends \w+)?(?: implements \w+)? \{', content)
    if class_def_match:
        pos = class_def_match.end()
        new_content = content[:pos] + '\n' + insertion + content[pos:]
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print('Patched', filepath)
