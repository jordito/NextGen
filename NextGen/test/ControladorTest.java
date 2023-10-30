package NextGen.test;
import NextGen.controlador.*;
import NextGen.modelo.Articulo;
import NextGen.modelo.ListaArticulos;

import static org.junit.jupiter.api.Assertions.*;

class ControladorTest {

    @org.junit.jupiter.api.Test
    void agregarArticulo() {

        // Creamos un artículo y lo añadimos
        Articulo articulo = new Articulo("1234A", "Artículo de prueba", 23.5, 4.99, 3);
        ListaArticulos listaArticulos = new ListaArticulos();
        listaArticulos.add(articulo);

        // Comprobamos que se haya añadido
        assertEquals(1, listaArticulos.getSize());

    }

    @org.junit.jupiter.api.Test
    void agregarArticulos() {
        // Creamos un artículo, lo añadimos a la lista y repetimos el proceso
        ListaArticulos listaArticulos = new ListaArticulos();
        Articulo articulo = new Articulo("1234A", "Artículo de prueba", 23.5, 4.99, 3);
        listaArticulos.add(articulo);
        Articulo articulo2 = new Articulo("1234A", "Artículo de prueba", 23.5, 4.99, 3);
        listaArticulos.add(articulo);

        // Comprobamos que se hayan añadido
        assertEquals(2, listaArticulos.getSize());
    }


    @org.junit.jupiter.api.Test
    void agregarArticuloExistente() {
        // Creamos un artículo y lo añadimos a la lista
        Articulo articulo = new Articulo("1234A", "Artículo de prueba", 23.5, 4.99, 3);
        ListaArticulos listaArticulos = new ListaArticulos();
        listaArticulos.add(articulo);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            // Le volvemos a añadir el mismo artículo, debería devolver una excepción
            for (Articulo articuloExistente : listaArticulos.getArrayList()) {
                if (articuloExistente.getCodigo().equals(articulo.getCodigo())) {
                    throw new RuntimeException("Ya existe un artículo con el mismo código");
                }
            }
        });

        String actualMessage = exception.getMessage();
        // Comprobamos que la excepción coincida
        assertTrue(actualMessage.contains("Ya existe un artículo con el mismo código"));
    }
}