package dsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.*;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;



public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/eetac/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.dsa package
        final ResourceConfig rc = new ResourceConfig().packages("dsa");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();

        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("./public/");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");


        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));

        System.in.read();
        server.stop();
    }
    /*
    //MAIN CREADO PARA PROBAR EL FUNCIONAMIENTO DEL PROGRAMA DESDE EL INTELIJ

    static List<Producto> cartaProductos = new ArrayList<Producto>();
    public static void main(String[] args){

        ProductManagerImpl manager;
        //Creamos la instancia de la variable global (Singleton)
        manager = ProductManagerImpl.getInstance();

        //Creamos el registro de Usuarios
        String [] usu = new String[10];
        usu[0] = "Sara";
        usu[1] = "Joan";
        usu[2] = "Maria";
        usu[3] = "Andreu";
        usu[4] = "Pepe";
        usu[5] = "Luisa";
        usu[6] = "Jose";
        usu[7] = "Juan";
        usu[8] = "Anna";
        usu[9] = "Cristian";

        //Añadimos el registro de usuarios a la clase dsa.ProductManagerImpl
        manager.setUsuarios(usu);

        //Creamos los productos y los añadimos a la lista de productos que pasaremos a la clase ProducuctManagerImpl
        Producto p1 = new Producto("Macarrones", 7);
        Producto p2 = new Producto("Sopa", 5);
        Producto p3 = new Producto("Entrecot", 17);
        Producto p4 = new Producto("Paella", 16);
        Producto p5 = new Producto("Bocadillo", 4);
        Producto p6 = new Producto("Ensalada", 6);
        Producto p7 = new Producto("Croquetas", 6);
        cartaProductos.add(p1);
        cartaProductos.add(p2);
        cartaProductos.add(p3);
        cartaProductos.add(p4);
        cartaProductos.add(p5);
        cartaProductos.add(p6);
        cartaProductos.add(p7);
        manager.setCartaProductos(cartaProductos);

        //Primer Método: ordenamos la lista de productos previamente creada
        cartaProductos = manager.listaOrdeAscPrecio();

        ///Aqui se crea la comanda del pedido
        HashMap<Producto, Integer> comanda1 = new HashMap<Producto, Integer>();
        comanda1.put(p1,2);//Añadimos diferentes productos y su cantidad (en este caso  2 de Macarrones)
        comanda1.put(p2,1);//En vez de coger p2, tambien se podrian coger de la carta
        // de Productos que hay en dsa.ProductManagerImpl (manager.cartaProductos.get(index)) pero es lo mismo
        comanda1.put(p7,2);
        comanda1.put(p3,1);

        HashMap<Producto, Integer> comanda2 = new HashMap<Producto, Integer>();
        comanda2.put(p4,2);//Añadimos diferentes productos y su cantidad (en este caso  2 de Paella)
        comanda2.put(p6,1);
        comanda2.put(p5,1);
        comanda2.put(p3,2);

        //Creamos el pedido definitivo escribiendo el usuario que hace la comanda (tiene que
        // estar entre los usuarios registrados, sino, no se realizará el pedido )

        Pedido pedido1 = new Pedido("Juan",comanda1);
        Pedido pedido2 = new Pedido("Maria",comanda2);

        //Realizamos el pedido
        manager.realizarPedido(pedido1);
        manager.realizarPedido(pedido2);

        //Servimos el pedido que entro primero (lo sacamos de la cola y lo añadimo a la lista de pedidos servidos que esta en ProdManagerImpl
        manager.servirPedido();
        manager.servirPedido();

        //Obtenemos la lista de los pedidos realizados por un usuario dado
        List<Pedido> pedidosUsuario = manager.listaPedidosUsuario("Juan");

        List<Producto> productosMasvendidosDesc = manager.listaOrdenadaDescProductosMasVendidos();


    }
    */
}
