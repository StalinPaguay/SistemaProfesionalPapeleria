import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Categoria } from '../../models/categoria.model';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.css']
})
export class CategoriasComponent implements OnInit {
  categorias: Categoria[] = [];
  categoriaForm: Categoria = { nombre: '' };
  editandoId: number | null = null;
  mostrarModal = false;
  mensaje = '';
  tipoMensaje: 'success' | 'danger' = 'success';

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (data) => this.categorias = data,
      error: (err) => this.mostrarAlerta('Error al cargar categorías', 'danger')
    });
  }

  abrirModalCrear(): void {
    this.categoriaForm = { nombre: '' };
    this.editandoId = null;
    this.mostrarModal = true;
  }

  abrirModalEditar(cat: Categoria): void {
    this.categoriaForm = { ...cat };
    this.editandoId = cat.id!;
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.categoriaForm = { nombre: '' };
    this.editandoId = null;
  }

  guardar(): void {
    if (!this.categoriaForm.nombre.trim()) return;

    if (this.editandoId) {
      this.categoriaService.actualizar(this.editandoId, this.categoriaForm).subscribe({
        next: () => {
          this.mostrarAlerta('Categoría actualizada correctamente', 'success');
          this.cerrarModal();
          this.cargarCategorias();
        },
        error: (err) => this.mostrarAlerta('Error al actualizar categoría', 'danger')
      });
    } else {
      this.categoriaService.crear(this.categoriaForm).subscribe({
        next: () => {
          this.mostrarAlerta('Categoría creada correctamente', 'success');
          this.cerrarModal();
          this.cargarCategorias();
        },
        error: (err) => this.mostrarAlerta('Error al crear categoría', 'danger')
      });
    }
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro de eliminar esta categoría?')) {
      this.categoriaService.eliminar(id).subscribe({
        next: () => {
          this.mostrarAlerta('Categoría eliminada', 'success');
          this.cargarCategorias();
        },
        error: (err) => this.mostrarAlerta('Error al eliminar. Puede tener productos asociados.', 'danger')
      });
    }
  }

  mostrarAlerta(msg: string, tipo: 'success' | 'danger'): void {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 3500);
  }
}
