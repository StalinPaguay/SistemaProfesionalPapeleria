import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DetalleCompra } from '../models/detalle-compra.model';

@Injectable({ providedIn: 'root' })
export class DetalleService {
  private apiUrl = 'http://localhost:8080/api/detalles-compra';

  constructor(private http: HttpClient) {}

  listar(): Observable<DetalleCompra[]> {
    return this.http.get<DetalleCompra[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<DetalleCompra> {
    return this.http.get<DetalleCompra>(`${this.apiUrl}/${id}`);
  }

  crear(detalle: DetalleCompra): Observable<DetalleCompra> {
    return this.http.post<DetalleCompra>(this.apiUrl, detalle);
  }

  actualizar(id: number, detalle: DetalleCompra): Observable<DetalleCompra> {
    return this.http.put<DetalleCompra>(`${this.apiUrl}/${id}`, detalle);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
