import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-mis-compras',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="mis-compras-container">
      <div class="header">
        <h1 class="text-3xl font-bold text-gray-800">Mis Compras</h1>
        <p class="text-gray-500">Historial de tus pedidos en PaperDS</p>
      </div>

      <div *ngIf="loading" class="loading-state">
        <span class="material-icons-outlined animate-spin text-blue-500 text-4xl">refresh</span>
        <p class="mt-4 text-gray-500">Cargando tu historial de compras...</p>
      </div>

      <div *ngIf="!loading && compras.length === 0" class="empty-state">
        <span class="material-icons-outlined text-6xl text-gray-300">receipt_long</span>
        <h3 class="text-xl font-bold text-gray-700 mt-4">Aún no tienes compras</h3>
        <p class="text-gray-500 mt-2">Tus pedidos aparecerán aquí una vez que realices tu primera compra.</p>
      </div>

      <div *ngIf="!loading && compras.length > 0" class="compras-list">
        <div *ngFor="let compra of compras" class="compra-card">
          <div class="compra-header">
            <div>
              <span class="text-sm text-gray-500 font-medium">Pedido #{{ compra.id }}</span>
              <p class="font-bold text-gray-800">{{ compra.fechaVenta | date:'medium' }}</p>
            </div>
            <div class="text-right">
              <span class="text-sm text-gray-500 font-medium">Total</span>
              <p class="font-bold text-blue-600 text-xl">\${{ compra.total | number:'1.2-2' }}</p>
            </div>
          </div>
          
          <div class="compra-body">
            <h4 class="font-semibold text-gray-700 mb-2 border-b pb-2">Detalle de productos</h4>
            <div *ngFor="let detalle of compra.detalles" class="detalle-item">
              <div class="flex items-center gap-2">
                <span class="material-icons-outlined text-gray-400 text-sm">inventory_2</span>
                <span class="font-medium text-gray-800">{{ detalle.productoNombre }}</span>
              </div>
              <div class="text-right text-sm">
                <span class="text-gray-500">{{ detalle.cantidad }} x \${{ detalle.precioUnitario }}</span>
                <span class="font-semibold text-gray-700 ml-2">\${{ detalle.subtotal }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .mis-compras-container {
      max-width: 900px;
      margin: 0 auto;
    }
    .header {
      margin-bottom: 32px;
    }
    .loading-state, .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 64px 0;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05);
    }
    .compras-list {
      display: flex;
      flex-direction: column;
      gap: 20px;
    }
    .compra-card {
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05);
      overflow: hidden;
      transition: transform 0.2s;
    }
    .compra-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1);
    }
    .compra-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px 24px;
      background: #f8fafc;
      border-bottom: 1px solid #e2e8f0;
    }
    .compra-body {
      padding: 20px 24px;
    }
    .detalle-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 8px 0;
      border-bottom: 1px dashed #e2e8f0;
    }
    .detalle-item:last-child {
      border-bottom: none;
    }
  `]
})
export class MisComprasComponent implements OnInit {
  compras: any[] = [];
  loading = true;

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit() {
    const user = this.authService.getUser();
    this.http.get<any[]>('http://localhost:8080/api/ventas').subscribe({
      next: (data) => {
        // En nuestro sistema las "ventas" de la tienda son las "compras" del usuario.
        // Filtramos para que solo vea las suyas (usando el nombre de vendedor o clienteNombre dependiendo de cómo se guardó).
        // En carrito.component.ts se guarda clienteNombre como el nombre del usuario.
        this.compras = data.filter(venta => venta.clienteNombre === (user?.nombreCompleto || 'Cliente General'));
        // Ordenar por las más recientes
        this.compras.sort((a, b) => new Date(b.fechaVenta).getTime() - new Date(a.fechaVenta).getTime());
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar historial de compras', err);
        this.loading = false;
      }
    });
  }
}
