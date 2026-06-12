import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  template: `
    <aside class="sidebar">
      <div class="sidebar-header">
        <span class="material-icons-outlined logo-icon">storefront</span>
        <div>
          <h1 class="logo-text">Paper<span class="text-blue-400">DS</span></h1>
          <span class="logo-sub">Gestión Empresarial</span>
        </div>
      </div>

      <nav class="sidebar-nav">
        <!-- MENÚ ADMINISTRADOR -->
        <ng-container *ngIf="authService.isAdmin()">
          <span class="nav-section-title">ADMINISTRACIÓN</span>

          <a routerLink="/dashboard" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">dashboard</span>
            <span>Dashboard</span>
          </a>

          <a routerLink="/usuarios" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">people</span>
            <span>Usuarios</span>
          </a>

          <a routerLink="/categorias" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">category</span>
            <span>Categorías</span>
          </a>

          <a routerLink="/productos" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">inventory_2</span>
            <span>Productos</span>
          </a>

          <a routerLink="/proveedores" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">local_shipping</span>
            <span>Proveedores</span>
          </a>

          <a routerLink="/compras" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">shopping_cart</span>
            <span>Compras</span>
          </a>
        </ng-container>

        <!-- MENÚ USUARIO -->
        <ng-container *ngIf="authService.isUser()">
          <span class="nav-section-title">MI CUENTA</span>

          <a routerLink="/tienda" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">store</span>
            <span>Tienda</span>
          </a>

          <a routerLink="/carrito" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">shopping_basket</span>
            <span>Mi Carrito</span>
          </a>

          <a routerLink="/mis-compras" routerLinkActive="active" class="nav-item">
            <span class="material-icons-outlined">receipt</span>
            <span>Mis Compras</span>
          </a>
        </ng-container>
      </nav>

      <div class="sidebar-footer">
        <div class="user-info">
          <div class="avatar">
            <span class="material-icons-outlined">person</span>
          </div>
          <div class="user-details">
            <span class="user-name">{{ user?.nombreCompleto || 'Usuario' }}</span>
            <span class="user-role">{{ authService.getRole() }}</span>
          </div>
        </div>
        <button class="logout-btn" (click)="logout()" title="Cerrar sesión">
          <span class="material-icons-outlined">logout</span>
        </button>
      </div>
    </aside>
  `,
  styles: [`
    .sidebar {
      position: fixed;
      top: 0;
      left: 0;
      width: var(--sidebar-width);
      height: 100vh;
      background: #1e1e2d; /* Modern Dark SaaS Sidebar */
      color: #fff;
      display: flex;
      flex-direction: column;
      z-index: 100;
      box-shadow: 4px 0 15px rgba(0,0,0,0.05);
    }

    .sidebar-header {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 24px 20px;
      background: rgba(0,0,0,0.15);
    }

    .logo-icon {
      font-size: 28px;
      color: #3b82f6;
    }

    .logo-text {
      font-size: 1.5rem;
      font-weight: 800;
      letter-spacing: -0.5px;
      color: #fff;
    }

    .logo-sub {
      font-size: 0.65rem;
      color: rgba(255,255,255,0.5);
      letter-spacing: 0.5px;
      text-transform: uppercase;
      font-weight: 500;
    }

    .sidebar-nav {
      flex: 1;
      padding: 24px 16px;
      overflow-y: auto;
    }

    .nav-section-title {
      display: block;
      font-size: 0.7rem;
      font-weight: 600;
      letter-spacing: 1.2px;
      color: #6b7280;
      padding: 8px 12px;
      margin-bottom: 8px;
      margin-top: 16px;
    }
    
    .nav-section-title:first-child {
      margin-top: 0;
    }

    .nav-item {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 12px 14px;
      margin: 4px 0;
      border-radius: 8px;
      color: #9ca3af;
      text-decoration: none;
      font-size: 0.9rem;
      font-weight: 500;
      transition: all 0.2s ease;
      cursor: pointer;
    }

    .nav-item .material-icons-outlined {
      font-size: 20px;
      transition: color 0.2s ease;
    }

    .nav-item:hover {
      background: rgba(255,255,255,0.05);
      color: #fff;
    }
    
    .nav-item:hover .material-icons-outlined {
      color: #3b82f6;
    }

    .nav-item.active {
      background: #3b82f6;
      color: #fff;
      font-weight: 600;
      box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
    }
    
    .nav-item.active .material-icons-outlined {
      color: #fff;
    }

    .sidebar-footer {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 16px 20px;
      background: rgba(0,0,0,0.2);
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 12px;
    }

    .avatar {
      width: 36px;
      height: 36px;
      border-radius: 8px;
      background: #374151;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #9ca3af;
    }

    .user-details {
      display: flex;
      flex-direction: column;
    }

    .user-name {
      font-size: 0.85rem;
      font-weight: 600;
      color: #fff;
    }

    .user-role {
      font-size: 0.7rem;
      color: #6b7280;
    }

    .logout-btn {
      background: none;
      border: none;
      color: #9ca3af;
      cursor: pointer;
      padding: 8px;
      border-radius: 8px;
      transition: all 0.2s ease;
    }

    .logout-btn:hover {
      background: rgba(239, 68, 68, 0.1);
      color: #ef4444;
    }
  `]
})
export class SidebarComponent {
  user: any;

  constructor(public authService: AuthService, private router: Router) {
    this.user = this.authService.getUser();
  }

  logout() {
    this.authService.logout();
  }
}

