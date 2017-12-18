package dsa;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.

@Path("/restauracio")
@Singleton
public class JSONService {
    protected ProductManagerImpl manager;

    public JSONService() {
        manager = ProductManagerImpl.getInstance();
        String[] usu = new String[10];
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

        manager.setUsuarios(usu);
        List<Producto> cartaProductos = new ArrayList<>();
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
        HashMap<Producto, Integer> comanda = new HashMap();
        comanda.put(p1,2);
        comanda.put(p2,3);
        HashMap<Producto, Integer> comanda2 = new HashMap();
        comanda.put(p3,1);
        comanda.put(p4,8);
        HashMap<Producto, Integer> comanda3 = new HashMap();
        comanda.put(p5,4);
        comanda.put(p6,2);

        Pedido p = new Pedido("Joan",comanda);
        Pedido pp2 = new Pedido("Joan", comanda2);
        Pedido pp3 = new Pedido("Joan", comanda3);
        manager.realizarPedido(p);
        manager.realizarPedido(pp2);
        manager.realizarPedido(pp3);




    }

    /// MINIMO 2 - PETICIONES REST -
    @GET
    @Path("/login/{nombre}/{password}")//login
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean hacerPedido(@PathParam("nombre") String nombre, @PathParam("password") String pass) {

        return true;

    }
    @GET
    @Path("/listaOrdenadaProductos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Producto> listaOrdenadaProductos() //Ordenar la lista de productos
    {


        return manager.listaOrdeAscPrecio();
    }

    @GET
    @Path("/pedidosRealizados")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pedido> pedidosRealizados() //Ordenar la lista de productos
    {

        return manager.getPedidosRealizados();
    }









    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON) //Mostrar Lista de usuarios registrados
    public String sacarUsuarios() {
        String linia = "";
        for (String p : manager.getUsuarios()) {
            linia = linia + p + "\n";


        }


        return linia;
    }

    @GET
    @Path("/listaProductos")
    @Produces(MediaType.APPLICATION_JSON)
    public String listaProductoso() //Mostrar lista productos
    {
        String productos = "     Lista de productos\n\n";
        for (Producto p : manager.getCartaProductos()) {

            productos = productos + "El Producto " + p.getName() + " cuyo precio es " + p.getValue() + "\n";

        }


        return productos;
    }


    @GET
    @Path("/listOrdProd")
    @Produces(MediaType.APPLICATION_JSON)
    public String listaOrdeAscPrecio() //Ordenar la lista de productos
    {
        String productos = "    Lista ordenada de productos\n\n";
        for (Producto p : manager.listaOrdeAscPrecio()) {

            productos = productos + "El Producto " + p.getName() + " cuyo precio es " + p.getValue() + "\n";

        }


        return productos;
    }


    @GET
    @Path("/hacerPedido/{nombre}/{numPedido}")//Realizar Pedido indicando el usuario y el numero de pedido(1,2 o 3)
    @Produces(MediaType.APPLICATION_JSON)
    public String hacerPedido(@PathParam("nombre") String nombre, @PathParam("numPedido") Integer numPedido) {
        String frase = "";

        HashMap<Producto, Integer> comanda = new HashMap<Producto, Integer>();
        if (numPedido == 1) {

            comanda.put(manager.getCartaProductos().get(0), 2);
            comanda.put(manager.getCartaProductos().get(1), 4);
            comanda.put(manager.getCartaProductos().get(2), 3);
        } else if (numPedido == 2) {
            comanda.put(manager.getCartaProductos().get(3), 2);
            comanda.put(manager.getCartaProductos().get(4), 4);
            comanda.put(manager.getCartaProductos().get(5), 3);

        } else if (numPedido == 3) {
            comanda.put(manager.getCartaProductos().get(3), 2);
            comanda.put(manager.getCartaProductos().get(4), 4);
            comanda.put(manager.getCartaProductos().get(5), 3);

        }


        if (numPedido == 1 || numPedido == 2 || numPedido == 3) {
            Pedido l = new Pedido(nombre, comanda);

            manager.realizarPedido(l);
            frase = frase + "El usuario " + nombre + " ha realizado el siguiente pedido:\n";
            for (HashMap.Entry<Producto, Integer> entry : comanda.entrySet()) {
                frase = frase + entry.getKey().getName() + "  x" + entry.getValue() + "\n";

            }
            frase = frase + "El pedido tambien se ha añadido a la cola de pedidos\n";
        } else {
            frase = frase + "No existe este identificador de pedido. Pruebe con 1, 2 o 3";
        }


        return frase;
    }

    @POST
    @Path("/realizarPedido/newPedido")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response realizarPedido(Pedido p) {
        manager.realizarPedido(p);

        return Response.status(201).entity("Pedido realizado y añadido a la cola de pedidos").build();

    }

    @POST
    @Path("/pp/np")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirPedido(Pedido m) {
        //manager.realizarPedido(m);
        return Response.status(201).entity("Pedido realizado por " + m.getUsuario()).build();

    }

    @GET
    @Path("/servirPedido") //Pedidos Realizados por un usuario dado
    @Produces(MediaType.APPLICATION_JSON)
    public String servirPedido() {
        String frase = "";
        if (!manager.getColaPedidos().isEmpty()) {
            manager.servirPedido();
            Pedido p = manager.getPedidosServidos().get(manager.getPedidosServidos().size() - 1);


            frase = frase + "Pedido de " + p.getUsuario() + " servido\n\n";
            int k = 1;

            for (HashMap.Entry<Producto, Integer> entry : p.getMapProductos().entrySet()) {

                frase = frase + entry.getKey().getName() + "  x" + entry.getValue() + "\n";
            }


        }
        else{
            frase = frase+"No hay mas pedidos en cola";

        }
        return frase ;
    }

    @GET
    @Path("/gotPedidos/{nombre}") //Pedidos Realizados por un usuario dado
    @Produces(MediaType.APPLICATION_JSON)
    public String listaPedidosUsuario(@PathParam("nombre") String nombre){
        String frase = "      Pedidos de "+nombre+"\n\n";
        int k = 1;
       for(Pedido d: manager.listaPedidosUsuario(nombre)){

           frase = frase + "El pedido numero "+k+" contiene:\n";
           for(HashMap.Entry<Producto, Integer> entry : d.getMapProductos().entrySet())
           {
                         frase = frase+ entry.getKey().getName()+"  x"+entry.getValue()+"\n";
           }
            frase =frase + "\n";
           k++;
       }
       if(frase==""){
           frase="No hay pedidos del usuario "+nombre;
       }
        return frase ;
    }
    @GET
    @Path("/listaProdMasVendidos")
    @Produces(MediaType.APPLICATION_JSON)
    public String listaOrdenadaDescProductosMasVendidos(){
        String vendidos="Producto mas vendido -->\n";
        Integer i=1;

        for(Producto p: manager.listaOrdenadaDescProductosMasVendidos()){

            vendidos= vendidos+ "El producto "+ p.getName()+"\n";
        }
        vendidos=vendidos+"--> Producto menos vendidos";

        return null;
    }


}
