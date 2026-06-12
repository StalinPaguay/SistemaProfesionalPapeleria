package org.example.modules.productos.service.implementation;

import org.example.modules.productos.dto.ProductoDTO;
import org.example.modules.categorias.entity.Categoria;
import org.example.modules.productos.entity.Producto;
import org.example.exception.RecursoNoEncontradoException;
import org.example.exception.ReglaNegocioException;
import org.example.modules.productos.mapper.ProductoMapper;
import org.example.modules.categorias.repository.CategoriaRepository;
import org.example.modules.productos.repository.ProductoRepository;
import org.example.modules.productos.service.interfaces.ProductoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.example.common.service.BaseServiceImpl;
@Service
/**
 * Implementación central de la capa de servicio para la gestión de Productos.
 * <p>
 * Clase transaccional encargada de procesar las reglas de negocio, validar
 * existencias, gestionar estados críticos de inventario y coordinar la
 * persistencia a través del patrón Repositorio y el mapeo de entidades (DTO).
 * Hereda operaciones CRUD genéricas de {@link org.example.common.service.BaseServiceImpl}.
 * </p>
 *
 * @author Equipo de Desarrollo PaperDS
 * @version 1.1.0
 * @see org.example.modules.productos.service.interfaces.ProductoService
 * @since 1.0
 */
public class ProductoServiceImpl extends BaseServiceImpl<Producto, ProductoDTO, Long> implements ProductoService {

    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    protected JpaRepository<Producto, Long> getRepository() {
        return repository;
    }

    @Override
    protected ProductoDTO toDTO(Producto entity) {
        return ProductoMapper.toDTO(entity);
    }

    @Override
    protected Producto toEntity(ProductoDTO dto) {
        return ProductoMapper.toEntity(dto);
    }

    /**
     * Valida, asocia categorías y persiste un nuevo producto en la base de datos.
     * También evalúa automáticamente el umbral de stock para levantar alertas si es necesario.
     *
     * @param dto El Data Transfer Object que encapsula los datos del nuevo producto.
     * @return El {@link ProductoDTO} persistido con su identificador único generado.
     * @throws org.example.exception.ReglaNegocioException si el precio es <= 0 o el stock es negativo.
     * @throws org.example.exception.RecursoNoEncontradoException si la categoría asociada no existe.
     */
    @Override
    public ProductoDTO save(ProductoDTO dto) {
        validarReglasNegocio(dto);
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + dto.getCategoriaId() + " no encontrada."));

        Producto entity = toEntity(dto);
        entity.setCategoria(categoria);
        if (entity.getStock() <= entity.getStockMinimo()) {
            entity.setAlertaStockBajo(true);
        } else {
            entity.setAlertaStockBajo(false);
        }
        return toDTO(repository.save(entity));
    }

    /**
     * Ejecuta una actualización segura de los datos de un producto existente.
     * Recupera la entidad original y sobrescribe únicamente los campos permitidos,
     * recalculando el estado de las alertas de inventario.
     *
     * @param id  ID único del producto a modificar.
     * @param dto El objeto con los nuevos datos a volcar en la entidad.
     * @return El {@link ProductoDTO} resultante después de aplicar la actualización.
     * @throws org.example.exception.RecursoNoEncontradoException si el producto o la categoría no existen.
     */
    @Override
    public ProductoDTO update(Long id, ProductoDTO dto) {
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + id + " no encontrado."));

        validarReglasNegocio(dto);
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría con ID " + dto.getCategoriaId() + " no encontrada."));

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        existente.setCategoria(categoria);
        if (dto.getDescripcion() != null) existente.setDescripcion(dto.getDescripcion());
        if (dto.getImagenUrl() != null) existente.setImagenUrl(dto.getImagenUrl());
        if (existente.getStock() <= existente.getStockMinimo()) {
            existente.setAlertaStockBajo(true);
        } else {
            existente.setAlertaStockBajo(false);
        }
        return toDTO(repository.save(existente));
    }

    private void validarReglasNegocio(ProductoDTO dto) {
        if (dto.getPrecio() == null || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ReglaNegocioException("El precio debe ser mayor a 0.");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new ReglaNegocioException("El stock no puede ser negativo.");
        }
    }

    @Override
    public List<ProductoDTO> obtenerBajoStock() {
        return findAll().stream()
                .filter(p -> p.getStock() <= p.getStockMinimo() && p.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDTO> obtenerAgotados() {
        return findAll().stream()
                .filter(p -> p.getStock() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public Producto getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con ID " + id + " no encontrado."));
    }

    /**
     * Modifica el nivel de existencias físicas de un producto de manera atómica.
     * Calcula el nuevo balance de stock e invoca mecanismos de alerta automática
     * en caso de traspasar el umbral mínimo definido para dicho producto.
     *
     * @param id              Identificador del producto en el inventario.
     * @param cantidadAgregar El diferencial de inventario (positivo para entradas, negativo para salidas/ventas).
     * @throws org.example.exception.ReglaNegocioException si la operación resulta en un stock matemáticamente imposible (< 0).
     */
    @Override
    public void actualizarStock(Long id, Integer cantidadAgregar) {
        Producto producto = getEntityById(id);
        int nuevoStock = producto.getStock() + cantidadAgregar;
        if (nuevoStock < 0) {
            throw new ReglaNegocioException("Stock insuficiente para el producto: " + producto.getNombre());
        }
        producto.setStock(nuevoStock);
        
        if (nuevoStock <= producto.getStockMinimo()) {
            producto.setAlertaStockBajo(true);
            System.out.println("ALERTA: Stock bajo para el producto " + producto.getNombre() + " (Stock actual: " + nuevoStock + ")");
        } else {
            producto.setAlertaStockBajo(false);
        }
        
        repository.save(producto);
    }
}
