import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-tienda',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tienda.component.html',
  styleUrls: ['./tienda.component.css']
})
export class TiendaComponent implements OnInit {
  productos: any[] = [];
  filteredProductos: any[] = [];
  searchTerm: string = '';
  loading = true;
  error = '';

  constructor(
    private productoService: ProductoService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos() {
    this.loading = true;
    this.productoService.listar().subscribe({
      next: (data: any) => {
        this.productos = data;
        this.filteredProductos = data;
        this.loading = false;
      },
      error: (err: any) => {
        this.error = 'Error al cargar productos de la tienda.';
        this.loading = false;
      }
    });
  }

  buscar() {
    if (!this.searchTerm.trim()) {
      this.filteredProductos = this.productos;
      return;
    }
    
    const term = this.searchTerm.toLowerCase();
    this.filteredProductos = this.productos.filter(p => 
      p.nombre.toLowerCase().includes(term) || 
      (p.categoria?.nombre && p.categoria.nombre.toLowerCase().includes(term))
    );
  }

  agregarAlCarrito(producto: any) {
    if (producto.stock <= 0) {
      alert('Producto agotado');
      return;
    }
    
    this.cartService.addToCart({
      productoId: producto.id,
      nombre: producto.nombre,
      precio: producto.precio,
      cantidad: 1,
      stockDisponible: producto.stock
    });
    
    // Opcional: mostrar un toast o mensaje de éxito
  }
}
