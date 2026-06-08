import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Compra } from '../models/compra.model';

@Injectable({ providedIn: 'root' })
export class CompraService {
  private apiUrl = 'http://localhost:8080/api/compras';

  constructor(private http: HttpClient) {}

  listar(): Observable<Compra[]> {
    return this.http.get<Compra[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<Compra> {
    return this.http.get<Compra>(`${this.apiUrl}/${id}`);
  }

  registrar(compra: Compra): Observable<Compra> {
    return this.http.post<Compra>(this.apiUrl, compra);
  }

  actualizar(id: number, compra: Compra): Observable<Compra> {
    return this.http.put<Compra>(`${this.apiUrl}/${id}`, compra);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
