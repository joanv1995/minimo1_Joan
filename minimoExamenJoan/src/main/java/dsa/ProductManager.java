package dsa;

import java.util.List;

public interface ProductManager {


     List<Producto> listaOrdeAscPrecio();
     void realizarPedido(Pedido p);
     void servirPedido();
     List<Pedido> listaPedidosUsuario(String nombre);
     List<Producto> listaOrdenadaDescProductosMasVendidos();
}
