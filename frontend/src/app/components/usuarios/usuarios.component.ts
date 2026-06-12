import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../models/usuario.model';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];
  
  // Para el modal de Crear/Editar
  usuarioForm: Usuario = { nombre: '', apellido: '', correo: '', password: '', rol: 'USUARIO' };
  editandoId: number | null = null;
  mostrarModal = false;

  // Para el modal de Cambiar Rol
  mostrarModalRol = false;
  rolFormId: number | null = null;
  nuevoRol: string = 'USUARIO';

  mensaje = '';
  tipoMensaje: 'success' | 'danger' = 'success';

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.listar().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => this.mostrarAlerta('Error al cargar usuarios', 'danger')
    });
  }

  abrirModalCrear(): void {
    this.usuarioForm = { nombre: '', apellido: '', correo: '', password: '', rol: 'USUARIO' };
    this.editandoId = null;
    this.mostrarModal = true;
  }

  abrirModalEditar(u: Usuario): void {
    this.usuarioForm = { ...u, password: '' }; // No cargamos la contraseña en edición
    this.editandoId = u.id!;
    this.mostrarModal = true;
  }

  abrirModalRol(u: Usuario): void {
    this.rolFormId = u.id!;
    this.nuevoRol = u.rol || 'USUARIO';
    this.mostrarModalRol = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.mostrarModalRol = false;
    this.usuarioForm = { nombre: '', apellido: '', correo: '', password: '', rol: 'USUARIO' };
    this.editandoId = null;
    this.rolFormId = null;
  }

  guardar(): void {
    if (!this.usuarioForm.nombre.trim() || !this.usuarioForm.apellido.trim() || !this.usuarioForm.correo.trim()) {
      this.mostrarAlerta('Complete los campos obligatorios', 'danger');
      return;
    }

    if (this.editandoId) {
      // Editar
      const datosActualizar = { 
        nombre: this.usuarioForm.nombre, 
        apellido: this.usuarioForm.apellido 
      };
      
      this.usuarioService.actualizar(this.editandoId, datosActualizar).subscribe({
        next: () => {
          this.mostrarAlerta('Usuario actualizado correctamente', 'success');
          this.cerrarModal();
          this.cargarUsuarios();
        },
        error: (err) => this.mostrarAlerta('Error al actualizar usuario', 'danger')
      });
    } else {
      // Crear
      if (!this.usuarioForm.password) {
        this.mostrarAlerta('La contraseña es obligatoria para nuevos usuarios', 'danger');
        return;
      }
      this.usuarioService.crear(this.usuarioForm).subscribe({
        next: () => {
          this.mostrarAlerta('Usuario creado correctamente', 'success');
          this.cerrarModal();
          this.cargarUsuarios();
        },
        error: (err) => this.mostrarAlerta('Error al crear usuario. Verifique si el correo ya existe.', 'danger')
      });
    }
  }

  guardarRol(): void {
    if (this.rolFormId) {
      this.usuarioService.cambiarRol(this.rolFormId, this.nuevoRol).subscribe({
        next: () => {
          this.mostrarAlerta('Rol cambiado correctamente', 'success');
          this.cerrarModal();
          this.cargarUsuarios();
        },
        error: (err) => this.mostrarAlerta('Error al cambiar rol', 'danger')
      });
    }
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro de eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => {
          this.mostrarAlerta('Usuario eliminado', 'success');
          this.cargarUsuarios();
        },
        error: (err) => this.mostrarAlerta('Error al eliminar usuario.', 'danger')
      });
    }
  }

  mostrarAlerta(msg: string, tipo: 'success' | 'danger'): void {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 3500);
  }
}
