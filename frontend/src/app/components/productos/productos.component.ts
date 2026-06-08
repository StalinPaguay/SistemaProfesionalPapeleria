import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../models/producto.model';
import { Categoria } from '../../models/categoria.model';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = [];
  categorias: Categoria[] = [];
  productoForm: Producto = { nombre: '', precio: 0, stock: 0, categoriaId: 0 };
  editandoId: number | null = null;
  mostrarModal = false;
  mensaje = '';
  tipoMensaje: 'success' | 'danger' = 'success';

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
  }

  cargarProductos(): void {
    this.productoService.listar().subscribe({
      next: (data) => this.productos = data,
      error: () => this.mostrarAlerta('Error al cargar productos', 'danger')
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (data) => this.categorias = data,
      error: () => this.mostrarAlerta('Error al cargar categorías', 'danger')
    });
  }

  abrirModalCrear(): void {
    this.productoForm = { nombre: '', precio: 0, stock: 0, categoriaId: 0 };
    this.editandoId = null;
    this.mostrarModal = true;
  }

  abrirModalEditar(prod: Producto): void {
    this.productoForm = { ...prod };
    this.editandoId = prod.id!;
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.productoForm = { nombre: '', precio: 0, stock: 0, categoriaId: 0 };
    this.editandoId = null;
  }

  guardar(): void {
    if (!this.productoForm.nombre.trim() || !this.productoForm.categoriaId) return;

    if (this.editandoId) {
      this.productoService.actualizar(this.editandoId, this.productoForm).subscribe({
        next: () => {
          this.mostrarAlerta('Producto actualizado correctamente', 'success');
          this.cerrarModal();
          this.cargarProductos();
        },
        error: () => this.mostrarAlerta('Error al actualizar producto', 'danger')
      });
    } else {
      this.productoService.crear(this.productoForm).subscribe({
        next: () => {
          this.mostrarAlerta('Producto creado correctamente', 'success');
          this.cerrarModal();
          this.cargarProductos();
        },
        error: () => this.mostrarAlerta('Error al crear producto', 'danger')
      });
    }
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro de eliminar este producto?')) {
      this.productoService.eliminar(id).subscribe({
        next: () => {
          this.mostrarAlerta('Producto eliminado', 'success');
          this.cargarProductos();
        },
        error: () => this.mostrarAlerta('Error al eliminar producto', 'danger')
      });
    }
  }

  mostrarAlerta(msg: string, tipo: 'success' | 'danger'): void {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 3500);
  }
}
