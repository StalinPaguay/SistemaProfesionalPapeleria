import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Compra } from '../../models/compra.model';
import { CompraService } from '../../services/compra.service';

@Component({
  selector: 'app-detalles-compra',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './detalles-compra.component.html',
  styleUrls: ['./detalles-compra.component.css']
})
export class DetallesCompraComponent implements OnInit {
  compra: Compra | null = null;
  cargando = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private compraService: CompraService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('compraId');
    if (id) {
      this.cargarCompra(Number(id));
    }
  }

  cargarCompra(id: number): void {
    this.compraService.obtenerPorId(id).subscribe({
      next: (data) => {
        this.compra = data;
        this.cargando = false;
      },
      error: () => {
        this.error = true;
        this.cargando = false;
      }
    });
  }

  calcularTotal(): number {
    if (!this.compra || !this.compra.detalles) return 0;
    return this.compra.detalles.reduce((sum, d) => sum + (d.cantidad * d.precioCompra), 0);
  }
}
