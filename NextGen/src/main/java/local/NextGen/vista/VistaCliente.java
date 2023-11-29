package local.NextGen.vista;
import local.NextGen.controlador.*;
import local.NextGen.modelo.*;
import local.NextGen.modelo.DAO.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import local.NextGen.controlador.*;

public class VistaCliente {
    static Scanner scanner = new Scanner(System.in);
    public static void gestionClientes() throws SQLException {
        boolean salir = false;
        char opcion;
        do {
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║     GESTIÓN CLIENTES         ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Listar Clientes           ║");
            System.out.println("║ 2. Listar Clientes Estandard ║");
            System.out.println("║ 3. Listar Clientes Premium   ║");
            System.out.println("║ 4. Añadir Cliente            ║");
            System.out.println("║ 5. Eliminar Cliente          ║");
            System.out.println("║ 6. Actualizar Cliente        ║");
            System.out.println("║ 0. Salir                     ║");
            System.out.println("╚══════════════════════════════╝");
            opcion = pedirOpcion();
            switch (opcion) {
                case '1' -> local.NextGen.controlador.Controlador.listarClientes();
                case '2' -> local.NextGen.controlador.Controlador.listarClientesEstandard();
                case '3' -> local.NextGen.controlador.Controlador.listarClientesPremium();
                case '4' -> agregarCliente();
                case '5' -> eliminarCliente();
                case '6' -> actualizarCliente();
                case '0' -> salir = true;
                default ->
                        System.out.println("\u001B[31m" + "Opción inválida. Por favor, elija una opción válida." + "\u001B[0m");
            }
        }
        while (!salir);
    }

    private static char pedirOpcion() {
        System.out.print("Ingrese la opción deseada: ");
        return scanner.nextLine().charAt(0);
    }
    public static void agregarCliente() throws SQLException {
        System.out.println("\u001B[34mIngrese los datos del nuevo cliente:\u001B[0m");

        System.out.print("\u001B[34mNIF:\u001B[0m ");
        String nif = scanner.nextLine();

        System.out.print("\u001B[34mNombre:\u001B[0m ");
        String nombre = scanner.nextLine();

        System.out.print("\u001B[34mEmail:\u001B[0m ");
        String email = scanner.nextLine();

        System.out.print("\u001B[34mDirección:\u001B[0m ");
        String direccion = scanner.nextLine();

        System.out.print("\u001B[34m¿Es cliente estándar? (S/N):\u001B[0m ");
        char opcion = scanner.nextLine().toUpperCase().charAt(0);

        local.NextGen.modelo.Cliente cliente;

        if (opcion == 'S') {
            cliente = new local.NextGen.modelo.ClienteEstandard(0, nif, nombre, email, direccion) {
                @Override
                public Map<String, Object> toMap() {
                    return null;
                }
            };
        } else {
            cliente = new local.NextGen.modelo.ClientePremium(0, nif, nombre, email, direccion, 0, 0) {
                @Override
                public Map<String, Object> toMap() {
                    return null;
                }
            };
        }

        if (local.NextGen.controlador.Controlador.agregarCliente(cliente)) {
            System.out.println("\u001B[32mCliente agregado con éxito\u001B[0m");
            System.out.println(cliente);
        } else {
            System.out.println("\u001B[32mError a agregar el cliente\u001B[0m");
        }
    }

    public static void actualizarCliente() throws SQLException {
        System.out.print("\u001B[34mIngrese el Id del cliente a actualizar: \u001B[0m");
        int idCliente = scanner.nextInt();

        scanner.nextLine();

        local.NextGen.modelo.Cliente cliente = local.NextGen.modelo.DAO.ClienteDAO.obtenerPorId(idCliente);

        if (cliente != null) {
            System.out.println("\u001B[34mDatos actuales del cliente:\u001B[0m");
            System.out.println(cliente);

            System.out.println("\u001B[34mIngrese los nuevos datos del cliente:\u001B[0m");

            System.out.print("\u001B[34mNuevo Nombre: \u001B[0m");
            String nuevoNombre = scanner.nextLine();
            cliente.setNombre(nuevoNombre);

            System.out.print("\u001B[34mNuevo Email: \u001B[0m");
            String nuevoEmail = scanner.nextLine();
            cliente.setEmail(nuevoEmail);

            System.out.print("\u001B[34mNueva Dirección: \u001B[0m");
            String nuevaDireccion = scanner.nextLine();
            cliente.setDireccion(nuevaDireccion);

            if (local.NextGen.controlador.Controlador.actualizarCliente(cliente)) {
                System.out.println("\u001B[32mCliente actualizado con éxito\u001B[0m");
            } else {
                System.out.println("\u001B[31mError al actualizar el cliente\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31mCliente no encontrado\u001B[0m");
        }
    }
    private static void eliminarCliente() throws SQLException {
        System.out.print("\u001B[34mIngrese el NIF del cliente que desea eliminar: \u001B[0m");
        String nif = scanner.nextLine();

        if (local.NextGen.controlador.Controlador.eliminarCliente(nif)) {
            System.out.println("\u001B[32mCliente eliminado con éxito\u001B[0m");
        } else {
            System.out.println("\u001B[31mError al eliminar el cliente\u001B[0m");
        }
    }
}
