import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Proveedor } from '../../models/proveedor.model';
import { ProveedorService } from '../../services/proveedor.service';

@Component({
  selector: 'app-proveedores',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.css']
})
export class ProveedoresComponent implements OnInit {
  proveedores: Proveedor[] = [];
  proveedorForm: Proveedor = { nombre: '', telefono: '', correo: '' };
  editandoId: number | null = null;
  mostrarModal = false;
  mensaje = '';
  tipoMensaje: 'success' | 'danger' = 'success';

  constructor(private proveedorService: ProveedorService) {}

  ngOnInit(): void {
    this.cargarProveedores();
  }

  cargarProveedores(): void {
    this.proveedorService.listar().subscribe({
      next: (data) => this.proveedores = data,
      error: () => this.mostrarAlerta('Error al cargar proveedores', 'danger')
    });
  }

  abrirModalCrear(): void {
    this.proveedorForm = { nombre: '', telefono: '', correo: '' };
    this.editandoId = null;
    this.mostrarModal = true;
  }

  abrirModalEditar(prov: Proveedor): void {
    this.proveedorForm = { ...prov };
    this.editandoId = prov.id!;
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.proveedorForm = { nombre: '', telefono: '', correo: '' };
    this.editandoId = null;
  }

  guardar(): void {
    if (!this.proveedorForm.nombre.trim()) return;

    if (this.editandoId) {
      this.proveedorService.actualizar(this.editandoId, this.proveedorForm).subscribe({
        next: () => {
          this.mostrarAlerta('Proveedor actualizado correctamente', 'success');
          this.cerrarModal();
          this.cargarProveedores();
        },
        error: () => this.mostrarAlerta('Error al actualizar proveedor', 'danger')
      });
    } else {
      this.proveedorService.crear(this.proveedorForm).subscribe({
        next: () => {
          this.mostrarAlerta('Proveedor creado correctamente', 'success');
          this.cerrarModal();
          this.cargarProveedores();
        },
        error: () => this.mostrarAlerta('Error al crear proveedor', 'danger')
      });
    }
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro de eliminar este proveedor?')) {
      this.proveedorService.eliminar(id).subscribe({
        next: () => {
          this.mostrarAlerta('Proveedor eliminado', 'success');
          this.cargarProveedores();
        },
        error: () => this.mostrarAlerta('Error al eliminar. Verifique si tiene compras asociadas.', 'danger')
      });
    }
  }

  mostrarAlerta(msg: string, tipo: 'success' | 'danger'): void {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 3500);
  }
}
