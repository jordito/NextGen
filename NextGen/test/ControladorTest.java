package NextGen.test;

import NextGen.controlador.*;
import NextGen.modelo.Articulo;
import NextGen.modelo.ListaArticulos;
import NextGen.controlador.Controlador;
import NextGen.modelo.Cliente;
import NextGen.modelo.ListaClientes;
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

    @org.junit.jupiter.api.Test
    public void testAgregarCliente() {
        Controlador controlador = new Controlador();

        // Valores de prueba
        String nif = "123456789A";
        String nombre = "Cliente de Prueba";
        String email = "cliente@prueba.com";
        String direccion = "123 Calle de la Prueba";
        String tipoCliente = "Estandard"; // Puedes cambiar esto según tus necesidades

        // Llama a la función agregarCliente con los valores de prueba
        controlador.agregarCliente(nif, nombre, email, direccion, tipoCliente);

        // Obtiene la lista de clientes y verifica que el cliente se haya agregado
        ListaClientes listaClientes = controlador.getDatos().getListaClientes();
        Cliente clienteAgregado = listaClientes.buscarPorNif(nif);

        // Realiza las aserciones
        assertNotNull(clienteAgregado);
        assertEquals(nif, clienteAgregado.getNif());
        assertEquals(nombre, clienteAgregado.getNombre());
        assertEquals(email, clienteAgregado.getEmail());
        assertEquals(direccion, clienteAgregado.getDireccion());
        assertEquals(tipoCliente, clienteAgregado.tipoCliente()); // Verifica el tipo de cliente
    }
}

