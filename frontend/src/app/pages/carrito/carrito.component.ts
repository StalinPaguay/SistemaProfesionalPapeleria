import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService, CartItem } from '../../services/cart.service';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})
export class CarritoComponent implements OnInit {
  items: CartItem[] = [];
  total: number = 0;
  isProcessing: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    public cartService: CartService,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cartService.items$.subscribe(items => {
      this.items = items;
      this.total = this.cartService.getTotal();
    });
  }

  updateQuantity(item: CartItem, delta: number) {
    const newQty = item.cantidad + delta;
    if (newQty > 0 && newQty <= item.stockDisponible) {
      this.cartService.updateQuantity(item.productoId, newQty);
    }
  }

  removeItem(productoId: number) {
    this.cartService.removeFromCart(productoId);
  }

  checkout() {
    if (this.items.length === 0) return;
    
    this.isProcessing = true;
    this.errorMessage = '';
    
    const user = this.authService.getUser();
    
    // Payload required by VentaDTO
    const ventaPayload = {
      clienteId: null,
      clienteNombre: user ? user.nombreCompleto : 'Cliente General',
      vendedorNombre: 'Tienda Online PaperDS',
      total: this.total,
      fechaVenta: new Date().toISOString(),
      detalles: this.items.map(item => ({
        productoId: item.productoId,
        productoNombre: item.nombre,
        cantidad: item.cantidad,
        precioUnitario: item.precio,
        subtotal: item.precio * item.cantidad
      }))
    };

    this.http.post('http://localhost:8080/api/ventas', ventaPayload).subscribe({
      next: (res: any) => {
        this.isProcessing = false;
        this.successMessage = '¡Compra realizada con éxito! Descargando factura...';
        
        // Generar Factura
        import('../../services/invoice.service').then(m => {
          const invoiceService = new m.InvoiceService();
          invoiceService.generateInvoice(ventaPayload, ventaPayload.clienteNombre);
        });

        this.cartService.clearCart();
        
        // Redirect to details after 3 seconds
        setTimeout(() => {
          this.router.navigate(['/mis-compras']);
        }, 2000);
      },
      error: (err) => {
        this.isProcessing = false;
        this.errorMessage = 'Hubo un error al procesar tu compra. Verifica el stock o intenta de nuevo.';
        console.error(err);
      }
    });
  }
}
