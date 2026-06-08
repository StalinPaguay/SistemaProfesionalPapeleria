$baseDir = "c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example"

$modules = @{
    "auth" = @("Usuario", "Rol", "AuthResponse", "LoginRequest", "RegisterRequest", "AuthController", "AuthService", "UsuarioRepository", "UserDetailsServiceImpl", "JwtAuthenticationFilter", "JwtUtil", "SecurityConfig")
    "productos" = @("Producto", "ProductoDTO", "ProductoController", "ProductoService", "ProductoServiceImpl", "ProductoRepository", "ProductoMapper")
    "categorias" = @("Categoria", "CategoriaDTO", "CategoriaController", "CategoriaService", "CategoriaServiceImpl", "CategoriaRepository", "CategoriaMapper")
    "proveedores" = @("Proveedor", "ProveedorDTO", "ProveedorController", "ProveedorService", "ProveedorServiceImpl", "ProveedorRepository", "ProveedorMapper")
    "compras" = @("Compra", "CompraDTO", "CompraController", "CompraService", "CompraServiceImpl", "CompraRepository", "CompraMapper")
    "detallecompra" = @("DetalleCompra", "DetalleCompraDTO", "DetalleCompraController", "DetalleCompraService", "DetalleCompraServiceImpl", "DetalleCompraRepository", "DetalleCompraMapper")
    "ventas" = @("Venta", "VentaDTO", "VentaController", "VentaService", "VentaServiceImpl", "VentaRepository", "VentaMapper", "DetalleVenta", "DetalleVentaDTO", "DetalleVentaMapper", "DetalleVentaRepository")
    "clientes" = @("Cliente", "ClienteDTO", "ClienteController", "ClienteService", "ClienteServiceImpl", "ClienteRepository", "ClienteMapper")
    "auditoria" = @("RegistroAuditoria", "AuditoriaController", "AuditoriaService", "RegistroAuditoriaRepository")
    "inventario" = @("MovimientoInventario", "TipoMovimiento", "MovimientoInventarioDTO", "MovimientoInventarioController", "MovimientoInventarioService", "MovimientoInventarioServiceImpl", "MovimientoInventarioRepository", "MovimientoInventarioMapper")
    "dashboard" = @("DashboardController")
    "cookie" = @("CookieController")
}

$common = @("BaseService", "BaseServiceImpl")

$exception = @("RecursoNoEncontradoException", "ReglaNegocioException", "GlobalExceptionHandler")

$config = @("CorsConfig")

# Create structure
$dirs = @("common\service", "soap\config", "soap\endpoint", "soap\mapper", "soap\schema", "exception", "config")
foreach ($m in $modules.Keys) {
    $dirs += "modules\$m\controller"
    $dirs += "modules\$m\service\interfaces"
    $dirs += "modules\$m\service\implementation"
    $dirs += "modules\$m\repository"
    $dirs += "modules\$m\entity"
    $dirs += "modules\$m\dto"
    $dirs += "modules\$m\mapper"
    if ($m -eq "auth") {
        $dirs += "security"
    }
}

foreach ($d in $dirs) {
    $path = "$baseDir\$d"
    if (-not (Test-Path $path)) {
        New-Item -ItemType Directory -Force -Path $path | Out-Null
    }
}

# Mapping class name to new full package
$classToPackage = @{}

# Function to determine package and target dir
function Get-TargetInfo {
    param([string]$fileName, [string]$baseName)
    
    if ($common -contains $baseName) {
        return @{ Pkg="org.example.common.service"; Dir="$baseDir\common\service" }
    }
    if ($exception -contains $baseName) {
        return @{ Pkg="org.example.exception"; Dir="$baseDir\exception" }
    }
    if ($config -contains $baseName) {
        return @{ Pkg="org.example.config"; Dir="$baseDir\config" }
    }
    
    foreach ($m in $modules.Keys) {
        if ($modules[$m] -contains $baseName) {
            $sub = ""
            if ($fileName -match "Controller\.java$") { $sub = "controller" }
            elseif ($fileName -match "ServiceImpl\.java$") { $sub = "service.implementation" }
            elseif ($fileName -match "Service\.java$") { $sub = "service.interfaces" }
            elseif ($fileName -match "Repository\.java$") { $sub = "repository" }
            elseif ($fileName -match "DTO\.java$") { $sub = "dto" }
            elseif ($fileName -match "Request\.java$" -or $fileName -match "Response\.java$") { $sub = "dto" }
            elseif ($fileName -match "Mapper\.java$") { $sub = "mapper" }
            elseif ($fileName -match "SecurityConfig" -or $fileName -match "Jwt" -or $fileName -match "UserDetails") { 
                return @{ Pkg="org.example.security"; Dir="$baseDir\security" }
            }
            else { $sub = "entity" }
            
            $dirSub = $sub.Replace(".", "\")
            return @{ Pkg="org.example.modules.$m.$sub"; Dir="$baseDir\modules\$m\$dirSub" }
        }
    }
    return $null
}

# Scan all java files and move them
$javaFiles = Get-ChildItem -Path $baseDir -Recurse -Filter "*.java" | Where-Object { $_.FullName -notmatch "\\modules\\" -and $_.FullName -notmatch "\\common\\" -and $_.FullName -notmatch "\\soap\\" -and $_.Name -ne "Main.java" }

foreach ($file in $javaFiles) {
    $baseName = $file.BaseName
    $info = Get-TargetInfo -fileName $file.Name -baseName $baseName
    if ($info) {
        $classToPackage[$baseName] = $info.Pkg
        $dest = Join-Path $info.Dir $file.Name
        Move-Item -Path $file.FullName -Destination $dest -Force
        Write-Host "Moved $($file.Name) to $($info.Dir)"
    }
}

# Now rewrite files
$allMovedFiles = Get-ChildItem -Path $baseDir -Recurse -Filter "*.java"

foreach ($file in $allMovedFiles) {
    if ($file.Name -eq "Main.java") { continue }
    
    $content = Get-Content -Path $file.FullName -Raw
    $baseName = $file.BaseName
    $pkg = $classToPackage[$baseName]
    
    if (-not $pkg) {
        # Check if it's already properly packaged (like security)
        $info = Get-TargetInfo -fileName $file.Name -baseName $baseName
        if ($info) {
            $pkg = $info.Pkg
        }
    }
    
    if ($pkg) {
        # Replace package
        $content = $content -replace '(?m)^package\s+org\.example\..*?;', "package $pkg;"
    }
    
    # Replace imports
    foreach ($key in $classToPackage.Keys) {
        $oldImport1 = "org.example.entity.$key;"
        $oldImport2 = "org.example.dto.$key;"
        $oldImport3 = "org.example.controller.$key;"
        $oldImport4 = "org.example.service.interfaces.$key;"
        $oldImport5 = "org.example.service.implementation.$key;"
        $oldImport6 = "org.example.repository.$key;"
        $oldImport7 = "org.example.exception.$key;"
        $oldImport8 = "org.example.mapper.$key;"
        $oldImport9 = "org.example.security.$key;"
        $oldImport10 = "org.example.config.$key;"
        $oldImport11 = "org.example.dto.auth.$key;"
        
        $newImport = "$($classToPackage[$key]).$key;"
        
        $content = $content.Replace($oldImport1, $newImport)
                           .Replace($oldImport2, $newImport)
                           .Replace($oldImport3, $newImport)
                           .Replace($oldImport4, $newImport)
                           .Replace($oldImport5, $newImport)
                           .Replace($oldImport6, $newImport)
                           .Replace($oldImport7, $newImport)
                           .Replace($oldImport8, $newImport)
                           .Replace($oldImport9, $newImport)
                           .Replace($oldImport10, $newImport)
                           .Replace($oldImport11, $newImport)
    }
    
    # Also replace star imports just in case
    $content = $content -replace 'import org\.example\.entity\.\*;', ""
    
    Set-Content -Path $file.FullName -Value $content
}

Write-Host "Done!"
