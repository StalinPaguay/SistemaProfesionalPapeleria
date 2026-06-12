package org.example.config;

import org.example.modules.auth.entity.Rol;
import org.example.modules.auth.entity.Usuario;
import org.example.modules.auth.repository.UsuarioRepository;
import org.example.modules.categorias.entity.Categoria;
import org.example.modules.categorias.repository.CategoriaRepository;
import org.example.modules.productos.entity.Producto;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.proveedores.entity.Proveedor;
import org.example.modules.proveedores.repository.ProveedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            CategoriaRepository categoriaRepository,
            ProveedorRepository proveedorRepository,
            ProductoRepository productoRepository) {
        
        return args -> {
            // 1. Crear Usuario Admin
            Usuario admin = usuarioRepository.findByCorreo("admin@paperds.com").orElse(new Usuario());
            if (admin.getId() == null) {
                admin.setNombre("Super");
                admin.setApellido("Administrador");
                admin.setCorreo("admin@paperds.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol(Rol.ADMINISTRADOR);
                usuarioRepository.save(admin);
                System.out.println("Superusuario Administrador garantizado: admin@paperds.com / admin123");
            }
            
            // 1.5 Crear Usuario Cliente por defecto
            Usuario cliente = usuarioRepository.findByCorreo("cliente@paperds.com").orElse(new Usuario());
            if (cliente.getId() == null) {
                cliente.setNombre("Juan");
                cliente.setApellido("Pérez");
                cliente.setCorreo("cliente@paperds.com");
                cliente.setPassword(passwordEncoder.encode("cliente123"));
                cliente.setRol(Rol.USUARIO);
                usuarioRepository.save(cliente);
                System.out.println("Usuario Cliente garantizado: cliente@paperds.com / cliente123");
            }

            // 2. Sembrar 20 Categorías con nombres reales
            boolean hasRealCategories = categoriaRepository.findAll().stream().anyMatch(c -> c.getNombre().equals("Cuadernos y Libretas"));
            if (!hasRealCategories) {
                String[] nombresCategorias = {
                    "Cuadernos y Libretas", "Lápices y Bolígrafos", "Marcadores y Resaltadores", 
                    "Borradores y Correctores", "Reglas y Compases", "Pegantes y Cintas", 
                    "Tijeras y Cúteres", "Papel y Cartulinas", "Láminas y Mapas", 
                    "Carpetas y Archivadores", "Pinturas y Pinceles", "Plastilinas y Arcillas", 
                    "Calculadoras", "Mochilas y Estuches", "Agendas y Diarios", 
                    "Pizarras y Tizas", "Clips y Grapadoras", "Post-its y Banderitas", 
                    "Sobres y Carpetas", "Artículos de Oficina"
                };
                List<Categoria> categorias = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    Categoria c = new Categoria();
                    c.setNombre(nombresCategorias[i]);
                    categorias.add(c);
                }
                categoriaRepository.saveAll(categorias);
                System.out.println("20 Categorías reales creadas.");
            }

            // 3. Sembrar 20 Proveedores con nombres reales
            boolean hasRealProviders = proveedorRepository.findAll().stream().anyMatch(p -> p.getNombre().equals("Norma S.A."));
            if (!hasRealProviders) {
                String[] nombresProveedores = {
                    "Norma S.A.", "Faber-Castell", "Pelikan", "Estrella", "Papelería Nacional", 
                    "Distribuidora del Pacífico", "Bic", "Pilot Corporation", "Staedtler", "Scribe", 
                    "3M (Post-it)", "Casio", "Artículos Escolares S.A.", "Ofi-Todo", 
                    "Distribuidora de Papeles", "Maped", "Pentel", "Sharpie", "Prismacolor", "Ledesma"
                };
                List<Proveedor> proveedores = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    Proveedor p = new Proveedor();
                    p.setNombre(nombresProveedores[i]);
                    p.setCorreo("contacto@" + nombresProveedores[i].replaceAll("[\\s\\-\\(\\)\\.]+","").toLowerCase() + ".com");
                    p.setTelefono("09900000" + (i < 10 ? "0"+i : i));
                    proveedores.add(p);
                }
                proveedorRepository.saveAll(proveedores);
                System.out.println("20 Proveedores reales creados.");
            }

            // 4. Sembrar 20 Productos con nombres reales
            boolean hasRealProducts = productoRepository.findAll().stream().anyMatch(p -> p.getNombre().equals("Lápiz HB No. 2"));
            if (!hasRealProducts) {
                String[] nombresProductos = {
                    "Cuaderno Universitario 100 hojas", "Lápiz HB No. 2", "Marcador Permanente Negro", 
                    "Borrador de Queso", "Caja de Colores x12", "Pegamento en Barra 21g", 
                    "Tijeras Punta Roma", "Cartulina Blanca A4", "Láminas Educativas Variadas", 
                    "Carpeta de Cartón con Liga", "Acuarelas x12 colores", "Plastilina Play-Doh", 
                    "Calculadora Científica", "Estuche Escolar Básico", "Agenda Diaria Ejecutiva", 
                    "Marcadores para Pizarra Blanca", "Caja de Grapas Standard", "Paquete de Post-its Amarillos", 
                    "Cinta Adhesiva Transparente", "Resaltador Amarillo Fluor"
                };
                String[] marcas = {
                    "Norma", "Faber-Castell", "Sharpie", "Pelikan", "Prismacolor", 
                    "UHU", "Maped", "Papelera", "Ediciones", "Estrella", 
                    "Pelikan", "Hasbro", "Casio", "Genérico", "Norma", 
                    "Expo", "Bostitch", "3M", "Scotch", "Faber-Castell"
                };
                String[] imagenes = {
                    "https://images.unsplash.com/photo-1531346878377-a5be20888e57?w=500&auto=format&fit=crop", // Cuaderno
                    "https://images.unsplash.com/photo-1583485088034-697b5a69fdd9?w=500&auto=format&fit=crop", // Lapiz
                    "https://images.unsplash.com/photo-1580569214296-5cb2affce8a3?w=500&auto=format&fit=crop", // Marcador
                    "https://images.unsplash.com/photo-1600880292203-757bb62b4baf?w=500&auto=format&fit=crop", // Borrador
                    "https://images.unsplash.com/photo-1513364776144-60967b0f800f?w=500&auto=format&fit=crop", // Colores
                    "https://images.unsplash.com/photo-1620005707787-84bc0f2420a3?w=500&auto=format&fit=crop", // Pegamento
                    "https://images.unsplash.com/photo-1581452932822-6b95b8ee9484?w=500&auto=format&fit=crop", // Tijeras
                    "https://images.unsplash.com/photo-1603484477859-abe6a73f9366?w=500&auto=format&fit=crop", // Cartulina
                    "https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=500&auto=format&fit=crop", // Laminas
                    "https://images.unsplash.com/photo-1586075010923-2dd4570fb338?w=500&auto=format&fit=crop", // Carpeta
                    "https://images.unsplash.com/photo-1583209814683-c023dd293cc6?w=500&auto=format&fit=crop", // Acuarelas
                    "https://images.unsplash.com/photo-1596461404969-9ae70f2830c1?w=500&auto=format&fit=crop", // Plastilina
                    "https://images.unsplash.com/photo-1587145820266-a5951ee6f620?w=500&auto=format&fit=crop", // Calculadora
                    "https://images.unsplash.com/photo-1550592704-6c76defa99ce?w=500&auto=format&fit=crop", // Estuche
                    "https://images.unsplash.com/photo-1506784926709-22f1ec395907?w=500&auto=format&fit=crop", // Agenda
                    "https://images.unsplash.com/photo-1580569214296-5cb2affce8a3?w=500&auto=format&fit=crop", // Marcadores Pizarra
                    "https://images.unsplash.com/photo-1528652610738-410a5628f21d?w=500&auto=format&fit=crop", // Grapas
                    "https://images.unsplash.com/photo-1586075010923-2dd4570fb338?w=500&auto=format&fit=crop", // Post-its
                    "https://images.unsplash.com/photo-1601056586829-1c9fc638b936?w=500&auto=format&fit=crop", // Cinta Adhesiva
                    "https://images.unsplash.com/photo-1527863280620-c8311a6f81ef?w=500&auto=format&fit=crop"  // Resaltador
                };
                List<Categoria> categorias = categoriaRepository.findAll();
                List<Producto> productos = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    Producto p = new Producto();
                    p.setNombre(nombresProductos[i]);
                    p.setStock(50 + i);
                    p.setStockMinimo(5);
                    p.setStockMaximo(200);
                    p.setPrecio(new BigDecimal("1." + (i * 10)));
                    p.setMarca(marcas[i]);
                    p.setDescripcion("Descripción detallada de " + nombresProductos[i]);
                    p.setImagenUrl(imagenes[i]);
                    if (!categorias.isEmpty()) {
                        p.setCategoria(categorias.get(i % categorias.size()));
                    }
                    productos.add(p);
                }
                System.out.println("20 Productos reales creados con imágenes personalizadas.");
            } else {
                List<Producto> productosExistentes = productoRepository.findAll();
                boolean actualizados = false;
                
                for (Producto p : productosExistentes) {
                    // Determinar la imagen basada en el nombre para asegurar coherencia total sin depender de internet
                    String name = p.getNombre().toLowerCase();
                    String url = "/assets/productos/general.png"; // Default generic stationery
                    
                    if (name.contains("cuaderno") || name.contains("libreta")) {
                        url = "/assets/productos/cuaderno.png";
                    } else if (name.contains("colores") || name.contains("caja de colores") || name.contains("acuarela")) {
                        url = "/assets/productos/colores.png";
                    } else if (name.contains("lapiz") || name.contains("lápiz")) {
                        url = "/assets/productos/lapiz.png";
                    } else if (name.contains("borrador") || name.contains("goma") || name.contains("queso")) {
                        url = "/assets/productos/borrador.png";
                    } else if (name.contains("resaltador") || name.contains("esfero") || name.contains("boligrafo")) {
                        url = "/assets/productos/esfero.png";
                    } else if (name.contains("marcador")) {
                        url = "/assets/productos/marcador.png";
                    } else if (name.contains("pegamento") || name.contains("pegante") || name.contains("cinta") || name.contains("adhesiva")) {
                        url = "/assets/productos/pegamento.png";
                    } else if (name.contains("tijera")) {
                        url = "/assets/productos/tijeras.png";
                    } else if (name.contains("cartulina")) {
                        url = "/assets/productos/cartulina.png";
                    } else if (name.contains("lamina") || name.contains("láminas") || name.contains("post-it") || name.contains("post")) {
                        url = "/assets/productos/laminas.png";
                    } else if (name.contains("carpeta")) {
                        url = "/assets/productos/carpeta.png";
                    } else if (name.contains("acuarela")) {
                        url = "/assets/productos/acuarelas.png";
                    } else if (name.contains("plastilina")) {
                        url = "/assets/productos/plastilina.png";
                    } else if (name.contains("calculadora")) {
                        url = "/assets/productos/calculadora.png";
                    } else if (name.contains("estuche") || name.contains("geometría")) {
                        url = "/assets/productos/estuche.png";
                    } else if (name.contains("agenda")) {
                        url = "/assets/productos/agenda.png";
                    }
                    
                    // Actualizar SIEMPRE la imagen para asegurar que se arreglen las actuales
                    p.setImagenUrl(url);
                    actualizados = true;
                }
                
                if (actualizados) {
                    productoRepository.saveAll(productosExistentes);
                    System.out.println("Imágenes COHERENTES actualizadas para todos los productos basados en su nombre.");
                }
            }

            // 5. Eliminar los registros de prueba generados por error
            List<Producto> productosViejos = productoRepository.findAll().stream()
                    .filter(p -> p.getNombre() != null && p.getNombre().startsWith("Producto Papelería "))
                    .collect(Collectors.toList());
            if (!productosViejos.isEmpty()) {
                productoRepository.deleteAll(productosViejos);
                System.out.println("Eliminados " + productosViejos.size() + " productos de prueba (Producto Papelería X).");
            }

            List<Proveedor> proveedoresViejos = proveedorRepository.findAll().stream()
                    .filter(p -> p.getNombre() != null && p.getNombre().startsWith("Proveedor ") && p.getNombre().length() <= 13) 
                    .collect(Collectors.toList());
            if (!proveedoresViejos.isEmpty()) {
                proveedorRepository.deleteAll(proveedoresViejos);
                System.out.println("Eliminados " + proveedoresViejos.size() + " proveedores de prueba (Proveedor X).");
            }

            List<Categoria> categoriasViejas = categoriaRepository.findAll().stream()
                    .filter(c -> c.getNombre() != null && c.getNombre().startsWith("Categoría "))
                    .collect(Collectors.toList());
            if (!categoriasViejas.isEmpty()) {
                // Obtener una categoría válida real a la cual reasignar los productos sueltos
                List<Categoria> categoriasReales = categoriaRepository.findAll().stream()
                        .filter(c -> c.getNombre() != null && !c.getNombre().startsWith("Categoría "))
                        .collect(Collectors.toList());
                Categoria categoriaSalvavidas = !categoriasReales.isEmpty() ? categoriasReales.get(0) : null;

                if (categoriaSalvavidas != null) {
                    List<Producto> todos = productoRepository.findAll();
                    boolean modificados = false;
                    for (Producto p : todos) {
                        if (p.getCategoria() != null) {
                            boolean esVieja = categoriasViejas.stream().anyMatch(cv -> cv.getId().equals(p.getCategoria().getId()));
                            if (esVieja) {
                                p.setCategoria(categoriaSalvavidas); // Asignar a categoría real en vez de null
                                modificados = true;
                            }
                        }
                    }
                    if (modificados) {
                        productoRepository.saveAll(todos);
                    }
                }
                categoriaRepository.deleteAll(categoriasViejas);
                System.out.println("Eliminadas " + categoriasViejas.size() + " categorías de prueba (Categoría X).");
            }
        };
    }
}
