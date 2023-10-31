package NextGen.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import NextGen.controlador.Controlador;
import NextGen.modelo.Cliente;
import NextGen.modelo.ListaClientes;

public class ControladorTest {

    @Test
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


