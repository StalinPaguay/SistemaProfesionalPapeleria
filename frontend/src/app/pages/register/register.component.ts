import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="login-container">
      <div class="login-card">
        <div class="text-center mb-8">
          <h1 class="text-3xl font-bold text-blue-600 mb-2">Paper<span class="text-gray-800">DS</span></h1>
          <p class="text-gray-500">Crea tu cuenta de Usuario</p>
        </div>

        <div *ngIf="error" class="alert alert-error mb-6">
          <span class="material-icons-outlined">error</span>
          <span>{{ error }}</span>
        </div>

        <div *ngIf="success" class="alert alert-success mb-6">
          <span class="material-icons-outlined">check_circle</span>
          <span>{{ success }}</span>
        </div>

        <form (ngSubmit)="onSubmit()" *ngIf="!success">
          <div class="form-group">
            <label class="form-label">Nombre</label>
            <input type="text" class="form-input" [(ngModel)]="nombre" name="nombre" required placeholder="Ej. Juan">
          </div>
          
          <div class="form-group">
            <label class="form-label">Apellido</label>
            <input type="text" class="form-input" [(ngModel)]="apellido" name="apellido" required placeholder="Ej. Perez">
          </div>

          <div class="form-group">
            <label class="form-label">Correo Electrónico</label>
            <input type="email" class="form-input" [(ngModel)]="correo" name="correo" required placeholder="tucorreo@ejemplo.com">
          </div>

          <div class="form-group">
            <label class="form-label">Contraseña</label>
            <input type="password" class="form-input" [(ngModel)]="password" name="password" required placeholder="••••••••">
          </div>

          <button type="submit" class="btn-primary" [disabled]="loading">
            <span *ngIf="!loading">Crear Cuenta</span>
            <span *ngIf="loading" class="material-icons-outlined animate-spin">refresh</span>
          </button>

          <div class="text-center mt-6 text-sm text-gray-500">
            ¿Ya tienes cuenta? <a routerLink="/login" class="text-blue-600 hover:underline font-medium">Inicia sesión aquí</a>
          </div>
        </form>
      </div>
    </div>
  `,
  styleUrls: ['../login/login.component.css'] // Usa los mismos estilos del login
})
export class RegisterComponent {
  nombre = '';
  apellido = '';
  correo = '';
  password = '';
  error = '';
  success = '';
  loading = false;

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    if (!this.nombre || !this.apellido || !this.correo || !this.password) {
      this.error = 'Por favor, completa todos los campos.';
      return;
    }

    this.loading = true;
    this.error = '';

    const payload = {
      nombre: this.nombre,
      apellido: this.apellido,
      correo: this.correo,
      password: this.password,
      rol: 'USUARIO'
    };

    this.http.post('http://localhost:8080/api/auth/register', payload).subscribe({
      next: () => {
        this.loading = false;
        this.success = '¡Cuenta creada con éxito! Redirigiendo al login...';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Error al registrar la cuenta. Es posible que el correo ya exista.';
        console.error(err);
      }
    });
  }
}
