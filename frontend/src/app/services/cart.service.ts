import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface CartItem {
  productoId: number;
  nombre: string;
  precio: number;
  cantidad: number;
  imagenUrl?: string;
  stockDisponible: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartKey = 'paperds_cart';
  private itemsSubject = new BehaviorSubject<CartItem[]>(this.loadCart());
  public items$ = this.itemsSubject.asObservable();

  constructor() {}

  private loadCart(): CartItem[] {
    const saved = localStorage.getItem(this.cartKey);
    return saved ? JSON.parse(saved) : [];
  }

  private saveCart(items: CartItem[]) {
    localStorage.setItem(this.cartKey, JSON.stringify(items));
    this.itemsSubject.next(items);
  }

  getItems(): CartItem[] {
    return this.itemsSubject.getValue();
  }

  addToCart(item: CartItem) {
    const items = this.getItems();
    const existingIndex = items.findIndex(i => i.productoId === item.productoId);

    if (existingIndex > -1) {
      if (items[existingIndex].cantidad < item.stockDisponible) {
        items[existingIndex].cantidad += 1;
      }
    } else {
      items.push({ ...item, cantidad: 1 });
    }
    
    this.saveCart(items);
  }

  removeFromCart(productoId: number) {
    const items = this.getItems().filter(item => item.productoId !== productoId);
    this.saveCart(items);
  }

  updateQuantity(productoId: number, cantidad: number) {
    const items = this.getItems();
    const item = items.find(i => i.productoId === productoId);
    if (item && cantidad > 0 && cantidad <= item.stockDisponible) {
      item.cantidad = cantidad;
      this.saveCart(items);
    }
  }

  clearCart() {
    this.saveCart([]);
  }

  getTotal(): number {
    return this.getItems().reduce((total, item) => total + (item.precio * item.cantidad), 0);
  }

  getItemCount(): number {
    return this.getItems().reduce((count, item) => count + item.cantidad, 0);
  }
}
