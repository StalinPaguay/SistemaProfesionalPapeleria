import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Compra } from '../../models/compra.model';
import { DetalleCompra } from '../../models/detalle-compra.model';
import { Proveedor } from '../../models/proveedor.model';
import { Producto } from '../../models/producto.model';
import { CompraService } from '../../services/compra.service';
import { ProveedorService } from '../../services/proveedor.service';
import { ProductoService } from '../../services/producto.service';

@Component({
  selector: 'app-compras',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './compras.component.html',
  styleUrls: ['./compras.component.css']
})
export class ComprasComponent implements OnInit {
  compras: Compra[] = [];
  proveedores: Proveedor[] = [];
  productos: Producto[] = [];
  
  // Variables para nueva compra
  mostrarModal = false;
  nuevaCompra: Compra = { proveedorId: 0, fecha: new Date().toISOString().split('T')[0], detalles: [] };
  detalleTemp: DetalleCompra = { productoId: 0, cantidad: 1, precioCompra: 0 };
  
  mensaje = '';
  tipoMensaje: 'success' | 'danger' = 'success';

  constructor(
    private compraService: CompraService,
    private proveedorService: ProveedorService,
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.cargarCompras();
    this.cargarProveedores();
    this.cargarProductos();
  }

  cargarCompras(): void {
    this.compraService.listar().subscribe({
      next: (data) => this.compras = data,
      error: () => this.mostrarAlerta('Error al cargar compras', 'danger')
    });
  }

  cargarProveedores(): void {
    this.proveedorService.listar().subscribe(data => this.proveedores = data);
  }

  cargarProductos(): void {
    this.productoService.listar().subscribe(data => this.productos = data);
  }

  abrirModalNuevaCompra(): void {
    this.nuevaCompra = { 
      proveedorId: 0, 
      fecha: new Date().toISOString().split('T')[0],
      detalles: []
    };
    this.detalleTemp = { productoId: 0, cantidad: 1, precioCompra: 0 };
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  agregarDetalle(): void {
    if (this.detalleTemp.productoId && this.detalleTemp.cantidad > 0 && this.detalleTemp.precioCompra > 0) {
      const prodSelect = this.productos.find(p => p.id === this.detalleTemp.productoId);
      this.nuevaCompra.detalles!.push({
        ...this.detalleTemp,
        productoNombre: prodSelect?.nombre
      });
      // Reset temp
      this.detalleTemp = { productoId: 0, cantidad: 1, precioCompra: 0 };
    }
  }

  removerDetalle(index: number): void {
    this.nuevaCompra.detalles!.splice(index, 1);
  }

  calcularTotalDetalle(d: DetalleCompra): number {
    return d.cantidad * d.precioCompra;
  }

  calcularTotalCompra(): number {
    return this.nuevaCompra.detalles!.reduce((sum, d) => sum + (d.cantidad * d.precioCompra), 0);
  }

  registrarCompra(): void {
    if (!this.nuevaCompra.proveedorId || this.nuevaCompra.detalles!.length === 0) return;
    
    this.compraService.registrar(this.nuevaCompra).subscribe({
      next: () => {
        this.mostrarAlerta('Compra registrada exitosamente', 'success');
        this.cerrarModal();
        this.cargarCompras();
        this.cargarProductos(); // Refrescar para ver nuevos stocks
      },
      error: () => this.mostrarAlerta('Error al registrar compra', 'danger')
    });
  }

  eliminarCompra(id: number): void {
    if (confirm('¿Eliminar compra? Esto NO descontará el stock de los productos ingresados.')) {
      this.compraService.eliminar(id).subscribe({
        next: () => {
          this.mostrarAlerta('Compra eliminada', 'success');
          this.cargarCompras();
        },
        error: () => this.mostrarAlerta('Error al eliminar compra', 'danger')
      });
    }
  }

  mostrarAlerta(msg: string, tipo: 'success' | 'danger'): void {
    this.mensaje = msg;
    this.tipoMensaje = tipo;
    setTimeout(() => this.mensaje = '', 3500);
  }
}
