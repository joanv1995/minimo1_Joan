package dsa;

import java.util.*;
//import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class ProductManagerImpl implements ProductManager {

    String[] usuarios;


    List<Producto> cartaProductos;
    List <Pedido> pedidosRealizados = new ArrayList<Pedido>();
    List <Pedido> pedidosServidos = new ArrayList<Pedido>();
    Queue<Pedido> colaPedidos = new LinkedList<Pedido>();
    private static ProductManagerImpl instance;
    Logger log = Logger.getLogger(getClass().getName());

    public static ProductManagerImpl getInstance(){
        if(instance == null)
        {
            instance = new ProductManagerImpl();
            BasicConfigurator.configure();
        }
        return instance;
    }


    public List<Producto> listaOrdeAscPrecio() {


        Collections.sort(cartaProductos,new ComparadorPrecio()); //Se ha creado una pequeña clase abajo impleentado un comparador
        log.info("Lista de productos ordenada ascendentemente por precio");
        return cartaProductos;

    }

    public void realizarPedido(Pedido p) {
        boolean encontrado = false;
        for(int i=0; i<this.getUsuarios().length;i++) {
            if(this.getUsuarios()[i].equals(p.getUsuario()))
            {
                encontrado = true;
            }
        }
        if(encontrado){
            colaPedidos.add(p);
            pedidosRealizados.add(p);
            log.info("Pedido de "+p.getUsuario()+" realizado correctamente");


        }
        else{

            log.error("Usuario no esta identificado, no puede realizar el pedido");
        }
    }

    public void servirPedido() {

        Pedido p = colaPedidos.poll();
        pedidosServidos.add(p);
        log.info("Pedido de "+p.getUsuario()+" servido !");

    }

    public void setUsuarios(String[] usuarios) {
        this.usuarios = usuarios;
    }

    public void setCartaProductos(List<Producto> cartaProductos) {
        this.cartaProductos = cartaProductos;
    }

    public List<Pedido> listaPedidosUsuario(String nombre) {
        List<Pedido> pedidosDeUsuario = new ArrayList<Pedido>();
        for( Pedido p: pedidosRealizados){

            if(p.getUsuario().equals(nombre)){
                pedidosDeUsuario.add(p);
            }

        }
        if(pedidosDeUsuario.isEmpty()){
            log.error("Este Usuario no ha realizado ningún pedido");
        }
        return pedidosDeUsuario;
    }

    public List<Producto> listaOrdenadaDescProductosMasVendidos(){
        HashMap<Producto, Integer> productosVendidosTotales = new HashMap<Producto, Integer>();
        if(!pedidosServidos.isEmpty()) {
            for (Pedido p : pedidosServidos) {
                HashMap<Producto, Integer> productosDePedido = p.getMapProductos();
                for (Producto j : productosDePedido.keySet()) {
                    if(productosVendidosTotales.size() ==0){
                        productosVendidosTotales.put(j, productosDePedido.get(j));
                    }
                    else {
                        for (Map.Entry<Producto, Integer> entry : productosVendidosTotales.entrySet()) {
                            if (!entry.getKey().getName().equals(j.getName())) {
                                productosVendidosTotales.put(j, productosDePedido.get(j));

                            } else {
                                productosVendidosTotales.put(j, productosVendidosTotales.get(j) + productosDePedido.get(j));


                            }
                        }
                    }

                }
            }
            List<Producto> productosOrdenadosVentasDesc = new ArrayList<Producto>();
            Integer sizep = productosVendidosTotales.size();
            for (int i = 0; i < sizep; i++) {
                Integer maxValue = (Collections.max(productosVendidosTotales.values()));
                for (HashMap.Entry<Producto, Integer> entry : productosVendidosTotales.entrySet()) {
                    if (entry.getValue().equals(maxValue)) {
                        productosOrdenadosVentasDesc.add(entry.getKey());
                        productosVendidosTotales.remove(entry.getKey());
                        break;
                    }
                }

            }

            log.info("Lista retornada con los porductos mas vendidos (de más a menos)");
            return productosOrdenadosVentasDesc;
        }
        else{
            log.error("No hay ningún pedido servido");
            return null;
        }



    }
    public void resetarInstance (){

        instance=null;
    }

    class ComparadorPrecio implements Comparator<Producto>{

        public int compare(Producto a, Producto b){
            return a.getValue()< b.getValue() ? -1 : a.getValue() == b.getValue() ? 0 : 1;
        }
    }

    public List<Pedido> getPedidosRealizados() {
        return pedidosRealizados;
    }

    public List<Pedido> getPedidosServidos() {
        return pedidosServidos;
    }

    public Queue<Pedido> getColaPedidos() {
        return colaPedidos;
    }

    public String[] getUsuarios() {
        return usuarios;

    }

    public List<Producto> getCartaProductos() {
        return cartaProductos;
    }
}




