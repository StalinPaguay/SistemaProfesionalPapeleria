import { Routes } from '@angular/router';
import { CategoriasComponent } from './components/categorias/categorias.component';
import { ProductosComponent } from './components/productos/productos.component';
import { ProveedoresComponent } from './components/proveedores/proveedores.component';
import { ComprasComponent } from './components/compras/compras.component';
import { DetallesCompraComponent } from './components/detalles-compra/detalles-compra.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { TiendaComponent } from './pages/tienda/tienda.component';
import { CarritoComponent } from './pages/carrito/carrito.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { authGuard, adminGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  
  // Rutas de Usuario
  { path: 'tienda', component: TiendaComponent, canActivate: [authGuard] },
  { path: 'carrito', component: CarritoComponent, canActivate: [authGuard] },
  { path: 'mis-compras', loadComponent: () => import('./pages/mis-compras/mis-compras.component').then(m => m.MisComprasComponent), canActivate: [authGuard] }, // refresh


  
  // Rutas de Administrador
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard, adminGuard] },
  { path: 'categorias', component: CategoriasComponent, canActivate: [authGuard, adminGuard] },
  { path: 'productos', component: ProductosComponent, canActivate: [authGuard, adminGuard] },
  { path: 'proveedores', component: ProveedoresComponent, canActivate: [authGuard, adminGuard] },
  { path: 'compras', component: ComprasComponent, canActivate: [authGuard, adminGuard] },
  { path: 'compras/:compraId/detalles', component: DetallesCompraComponent, canActivate: [authGuard, adminGuard] },
  
  { path: '**', redirectTo: 'login' }
];
