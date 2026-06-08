# Papelería PF - API REST Backend

API REST para gestión de papelería desarrollada con Spring Boot.

## Requisitos

- Java 17
- PostgreSQL (activo y corriendo)
- IntelliJ IDEA
- Insomnia (para pruebas de API)

## Configuración de Base de Datos

1. Asegúrate de que PostgreSQL esté activo
2. Crea la base de datos manualmente:
   ```sql
   CREATE DATABASE "Papeleria_PF";
   ```
3. Las **tablas se crean automáticamente** al ejecutar el proyecto (JPA/Hibernate con `ddl-auto=update`)

### Credenciales (en `application-local.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Papeleria_PF
spring.datasource.username=postgres
spring.datasource.password=1751050913
```

## Instrucciones de Ejecución

1. **Verificar** que PostgreSQL esté activo
2. **Abrir** el proyecto en IntelliJ IDEA
3. **Esperar** a que Maven descargue las dependencias
4. **Ejecutar** la clase `Main.java` (`org.example.Main`)
5. **Verificar** en la consola que las tablas se crearon automáticamente
6. **Probar** los endpoints en Insomnia (puerto `8080`)

---

## Endpoints API REST

### 📁 Categorías

| Método | URL                           | Descripción       |
|--------|-------------------------------|--------------------|
| GET    | `/api/categorias`             | Listar todas       |
| GET    | `/api/categorias/{id}`        | Obtener por ID     |
| POST   | `/api/categorias`             | Crear              |
| PUT    | `/api/categorias/{id}`        | Actualizar         |
| DELETE | `/api/categorias/{id}`        | Eliminar           |

**POST / PUT Body:**
```json
{
  "nombre": "Útiles escolares"
}
```

---

### 📦 Productos

| Método | URL                          | Descripción       |
|--------|------------------------------|--------------------|
| GET    | `/api/productos`             | Listar todos       |
| GET    | `/api/productos/{id}`        | Obtener por ID     |
| POST   | `/api/productos`             | Crear              |
| PUT    | `/api/productos/{id}`        | Actualizar         |
| DELETE | `/api/productos/{id}`        | Eliminar           |

**POST / PUT Body:**
```json
{
  "nombre": "Cuaderno universitario",
  "precio": 2.50,
  "stock": 0,
  "categoriaId": 1
}
```

---

### 🏭 Proveedores

| Método | URL                            | Descripción       |
|--------|--------------------------------|--------------------|
| GET    | `/api/proveedores`             | Listar todos       |
| GET    | `/api/proveedores/{id}`        | Obtener por ID     |
| POST   | `/api/proveedores`             | Crear              |
| PUT    | `/api/proveedores/{id}`        | Actualizar         |
| DELETE | `/api/proveedores/{id}`        | Eliminar           |

**POST / PUT Body:**
```json
{
  "nombre": "Distribuidora Papelera S.A.",
  "telefono": "0991234567",
  "correo": "ventas@papelera.com"
}
```

---

### 🛒 Compras (con Detalles y aumento automático de stock)

| Método | URL                        | Descripción       |
|--------|----------------------------|--------------------|
| GET    | `/api/compras`             | Listar todas       |
| GET    | `/api/compras/{id}`        | Obtener por ID     |
| POST   | `/api/compras`             | Registrar compra   |
| PUT    | `/api/compras/{id}`        | Actualizar         |
| DELETE | `/api/compras/{id}`        | Eliminar           |

**POST Body (registrar compra completa con detalles):**
```json
{
  "proveedorId": 1,
  "detalles": [
    {
      "productoId": 1,
      "cantidad": 50,
      "precioCompra": 1.80
    },
    {
      "productoId": 2,
      "cantidad": 30,
      "precioCompra": 0.50
    }
  ]
}
```

> ⚠️ **LÓGICA CRÍTICA:** Al registrar una compra, el stock de cada producto se **incrementa automáticamente** con la cantidad comprada.
> Ejemplo: stock actual = 0, cantidad comprada = 50 → nuevo stock = 50

**PUT Body (actualizar solo cabecera):**
```json
{
  "proveedorId": 2,
  "fecha": "2026-06-01"
}
```

---

### 📋 Detalles de Compra (individual)

| Método | URL                              | Descripción       |
|--------|----------------------------------|--------------------|
| GET    | `/api/detalles-compra`           | Listar todos       |
| GET    | `/api/detalles-compra/{id}`      | Obtener por ID     |
| POST   | `/api/detalles-compra`           | Crear              |
| PUT    | `/api/detalles-compra/{id}`      | Actualizar         |
| DELETE | `/api/detalles-compra/{id}`      | Eliminar           |

**POST Body:**
```json
{
  "productoId": 1,
  "cantidad": 20,
  "precioCompra": 1.75
}
```

> ⚠️ Al crear un detalle individual, también se **incrementa el stock** del producto.

---

## Flujo de Pruebas en Insomnia

### Paso 1: Crear Categorías
```
POST http://localhost:8080/api/categorias
Body: { "nombre": "Útiles escolares" }

POST http://localhost:8080/api/categorias
Body: { "nombre": "Oficina" }
```

### Paso 2: Crear Productos (con stock inicial 0)
```
POST http://localhost:8080/api/productos
Body: { "nombre": "Cuaderno universitario", "precio": 2.50, "stock": 0, "categoriaId": 1 }

POST http://localhost:8080/api/productos
Body: { "nombre": "Lápiz HB", "precio": 0.75, "stock": 0, "categoriaId": 1 }

POST http://localhost:8080/api/productos
Body: { "nombre": "Resma de papel A4", "precio": 4.50, "stock": 0, "categoriaId": 2 }
```

### Paso 3: Crear Proveedores
```
POST http://localhost:8080/api/proveedores
Body: { "nombre": "Distribuidora Papelera S.A.", "telefono": "0991234567", "correo": "ventas@papelera.com" }
```

### Paso 4: Registrar Compra (el stock aumenta automáticamente)
```
POST http://localhost:8080/api/compras
Body:
{
  "proveedorId": 1,
  "detalles": [
    { "productoId": 1, "cantidad": 50, "precioCompra": 1.80 },
    { "productoId": 2, "cantidad": 100, "precioCompra": 0.40 }
  ]
}
```

### Paso 5: Verificar stock actualizado
```
GET http://localhost:8080/api/productos
→ El cuaderno ahora tiene stock = 50
→ El lápiz ahora tiene stock = 100
```

### Paso 6: Listar todas las compras
```
GET http://localhost:8080/api/compras
```

### Paso 7: Actualizar un producto
```
PUT http://localhost:8080/api/productos/1
Body: { "nombre": "Cuaderno universitario 100H", "precio": 3.00, "stock": 50, "categoriaId": 1 }
```

### Paso 8: Eliminar
```
DELETE http://localhost:8080/api/categorias/2
DELETE http://localhost:8080/api/proveedores/1
```

---

## Estructura del Proyecto

```
src/main/java/org/example/
├── Main.java                          # Punto de entrada
├── config/
│   └── CorsConfig.java               # Configuración CORS
├── controller/
│   ├── CategoriaController.java       # Endpoints Categoría
│   ├── ProductoController.java        # Endpoints Producto
│   ├── ProveedorController.java       # Endpoints Proveedor
│   ├── CompraController.java          # Endpoints Compra
│   └── DetalleCompraController.java   # Endpoints DetalleCompra
├── dto/
│   ├── CategoriaDTO.java
│   ├── ProductoDTO.java
│   ├── ProveedorDTO.java
│   ├── CompraDTO.java
│   └── DetalleCompraDTO.java
├── entity/
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Proveedor.java
│   ├── Compra.java
│   └── DetalleCompra.java
├── exception/
│   ├── RecursoNoEncontradoException.java
│   ├── ReglaNegocioException.java
│   └── GlobalExceptionHandler.java
├── mapper/
│   ├── CategoriaMapper.java
│   ├── ProductoMapper.java
│   ├── ProveedorMapper.java
│   ├── CompraMapper.java
│   └── DetalleCompraMapper.java
├── repository/
│   ├── CategoriaRepository.java
│   ├── ProductoRepository.java
│   ├── ProveedorRepository.java
│   ├── CompraRepository.java
│   └── DetalleCompraRepository.java
└── service/
    ├── CategoriaService.java          # Interface
    ├── CategoriaServiceImpl.java      # Implementación
    ├── ProductoService.java
    ├── ProductoServiceImpl.java
    ├── ProveedorService.java
    ├── ProveedorServiceImpl.java
    ├── CompraService.java
    ├── CompraServiceImpl.java         # ⚠️ Lógica de aumento de stock
    ├── DetalleCompraService.java
    └── DetalleCompraServiceImpl.java  # ⚠️ Lógica de aumento de stock
```

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA / Hibernate
- PostgreSQL
- Jakarta Validation
