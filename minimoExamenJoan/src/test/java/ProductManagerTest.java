import dsa.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductManagerTest {

    ProductManagerImpl test;
    @Before
    public void setUp() throws Exception {
        test = ProductManagerImpl.getInstance();
        String[] usuarios = new String[4];
        List<Producto> menu = new ArrayList<Producto>();

        usuarios[0] = "Juan";
        usuarios[1] = "Maria";
        usuarios[2] = "Pepe";
        usuarios[3] = "Jose";
        test.setUsuarios(usuarios);


        Producto p1 = new Producto("Macarrones", 7);
        Producto p2 = new Producto("Sopa", 5);
        Producto p3 = new Producto("Entrecot", 17);
        menu.add(p1);
        menu.add(p2);
        menu.add(p3);
        test.setCartaProductos(menu);


        usuarios[0] = "Gorka";
        usuarios[1] = "Joan";
        usuarios[2] = "Pere";
        usuarios[3] = "Anna";
        test.setUsuarios(usuarios);

        Producto pp1 = new Producto("Carne", 12);
        Producto pp2 = new Producto("Dorada", 15);
        Producto pp3 = new Producto("Caviar", 80);
        menu.add(pp1);
        menu.add(pp2);
        menu.add(pp3);
        test.setCartaProductos(menu);
    }
        @After
        public void tearDown() throws Exception {
            //test.resetarInstance(); //Teoricamente seria como cerrar la conexión con Servidor pero en este caso no estoy seguro como ejecutar este test i por lo tanto
        }


        @Test
        public void realizarPedido () {

            HashMap<Producto, Integer> comanda1 = new HashMap<Producto, Integer>();
            comanda1.put(test.getCartaProductos().get(0), 2);//Añadimos diferentes productos y su cantidad (en este caso  2 de Macarrones)
            comanda1.put(test.getCartaProductos().get(1), 1);
            comanda1.put(test.getCartaProductos().get(2), 2);
            comanda1.put(test.getCartaProductos().get(3), 1);

            Pedido ped = new Pedido(test.getUsuarios()[1], comanda1);


            assertEquals(0, test.getPedidosRealizados().size());
            test.realizarPedido(ped);
            assertEquals(1, test.getPedidosRealizados().size());
        }

        @Test
        public void servirPedido () {
            HashMap<Producto, Integer> comanda1 = new HashMap<Producto, Integer>();
            HashMap<Producto, Integer> comanda2 = new HashMap<Producto, Integer>();
            comanda1.put(test.getCartaProductos().get(0), 2);//Añadimos diferentes productos y su cantidad (en este caso  2 de Macarrones)
            comanda1.put(test.getCartaProductos().get(1), 1);
            comanda1.put(test.getCartaProductos().get(2), 2);
            comanda1.put(test.getCartaProductos().get(3), 1);

            comanda2.put(test.getCartaProductos().get(0), 3);//Añadimos diferentes productos y su cantidad (en este caso  2 de Macarrones)
            comanda2.put(test.getCartaProductos().get(1), 2);
            comanda2.put(test.getCartaProductos().get(2), 1);
            comanda2.put(test.getCartaProductos().get(3), 1);

            Pedido ped = new Pedido(test.getUsuarios()[1], comanda1);
            Pedido ped2 = new Pedido(test.getUsuarios()[2], comanda2);

            test.realizarPedido(ped);
            test.realizarPedido(ped2);
            assertEquals(0, test.getPedidosServidos().size());

            test.servirPedido();

            assertEquals(1, test.getPedidosServidos().size());

            test.servirPedido();

            assertEquals(2, test.getPedidosServidos().size());


            assertEquals(test.getPedidosRealizados().get(0).getUsuario(), test.getPedidosServidos().get(0).getUsuario()); //EL primer pedido servido
            // tiene que ser el primero que se realizo

        }


    }

