import { DetalleCompra } from './detalle-compra.model';

export interface Compra {
  id?: number;
  fecha?: string;
  proveedorId: number;
  proveedorNombre?: string;
  detalles?: DetalleCompra[];
}
