package dsa;

import java.util.HashMap;

public class Pedido {
    String usuario;
    HashMap<Producto, Integer> mapProductos= new HashMap<Producto, Integer>();


    public Pedido(String usuario, HashMap<Producto, Integer> mapProductos) {
        this.usuario = usuario;
        this.mapProductos = mapProductos;
    }

    public HashMap<Producto, Integer> getMapProductos() {
        return mapProductos;
    }

    public String getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "usuario='" + usuario + '\'' +
                ", mapProductos=" + mapProductos +
                '}';
    }
}
