package local.NextGen.vista;
import java.sql.Connection;
import java.util.*;

import local.NextGen.controlador.*;
import local.NextGen.modelo.Articulo;
import local.NextGen.modelo.Cliente;
import local.NextGen.modelo.DAO.*;
import local.NextGen.modelo.DetallePedido;
import local.NextGen.modelo.Pedido;

import java.sql.SQLException;


public class VistaPedido {
    private static Controlador controlador;
    static Scanner scanner = new Scanner(System.in);

    // Constructor que recibe una instancia de Controlador
    public VistaPedido(Controlador controlador) {
        VistaPedido.controlador = controlador;
    }
    private static char pedirOpcion() {
        System.out.print("Ingrese la opción deseada: ");
        return scanner.nextLine().charAt(0);
    }

    public static void gestionPedidos() throws SQLException {
        boolean salir = false;
        char opcion;

        do {
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║     GESTIÓN PEDIDOS          ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Listar Pedidos            ║");
            System.out.println("║ 2. Agregar Pedido            ║");
            System.out.println("║ 3. Eliminar Pedido           ║");
            System.out.println("║ 4. Listar Pendientes         ║");
            System.out.println("║ 5. Listar Enviados           ║");
            System.out.println("║ 0. Salir                     ║");
            System.out.println("╚══════════════════════════════╝");

            opcion = pedirOpcion();

            switch (opcion) {
                case '1':
                    Controlador.listarPedidos();
                    break;
                case '2':
                    agregarPedido();
                    break;
                case '3':
                    eliminarPedido();
                    break;
                case '4':
                    mostrarPedidosPendientes();
                    break;
                case '5':
                    mostrarPedidosEnviados();
                    break;
                case '0':
                    salir = true;
                    break;
                default:
                    System.out.println("\u001B[31m" + "Opción inválida. Por favor, elija una opción válida." + "\u001B[0m");
            }
        } while (!salir);
    }
    public static void agregarPedido() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34mAgregar Nuevo Pedido:\u001B[0m");

        try {
            System.out.print("\u001B[34mIngrese el id del cliente:\u001B[0m ");
            int idCliente = scanner.nextInt();
            Cliente cliente = ClienteDAO.obtenerPorId(idCliente);

            if (cliente != null) {
                Date fechaHora = new Date();
                Pedido nuevoPedido = new Pedido(0, fechaHora, cliente, new ArrayList<>());

                boolean agregarOtroDetalle = true;

                while (agregarOtroDetalle) {
                    try {
                        System.out.print("\u001B[34mIngrese el código del artículo:\u001B[0m ");
                        String codigo = scanner.next();
                        Articulo articulo = ArticuloDAO.obtenerPorCodigo(codigo);

                        if (articulo != null) {
                            System.out.print("\u001B[34mIngrese la cantidad:\u001B[0m ");
                            int cantidad = scanner.nextInt();
                            scanner.nextLine();

                            DetallePedido detalle = new DetallePedido(0, articulo, cantidad);
                            nuevoPedido.agregarDetalle(detalle);

                            System.out.print("\u001B[34m¿Desea agregar otro artículo? (S/N):\u001B[0m ");
                            char opcion = scanner.next().toUpperCase().charAt(0);
                            agregarOtroDetalle = (opcion == 'S');
                        } else {
                            System.out.println("\u001B[31mArtículo no encontrado. Ingrese un código de artículo válido.\u001B[0m");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[31mError: Ingrese un valor válido.\u001B[0m");
                        scanner.next();
                    }
                }

                Pedido resultadoPedido = Controlador.agregarPedido(nuevoPedido);

                if (resultadoPedido != null) {
                    System.out.println("\u001B[32mPedido agregado con éxito\u001B[0m");
                    System.out.println(resultadoPedido);
                } else {
                    System.out.println("\u001B[31mError al agregar el pedido\u001B[0m");
                }
            } else {
                System.out.println("\u001B[31mCliente no encontrado\u001B[0m");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\u001B[31mError al acceder a la base de datos\u001B[0m");
        }
    }



    private static void eliminarPedido() {
        try {
            System.out.print("\u001B[34mIngrese el número del pedido a eliminar: \u001B[0m");
            int numeroPedido = Integer.parseInt(scanner.nextLine());

            controlador.eliminarPedido(numeroPedido);

            System.out.println("\u001B[32mPedido eliminado con éxito\u001B[0m");
        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mError: Ingrese un número válido\u001B[0m");
        } catch (SQLException e) {
            System.out.println("\u001B[31mError al eliminar el pedido\u001B[0m");
            e.printStackTrace();
        }
    }

    public static void mostrarPedidosPendientes() throws SQLException {
        List<Pedido> pedidosPendientes = controlador.listarPedidosPendientes();
        if (pedidosPendientes.isEmpty()) {
            System.out.println("\u001B[31mNo hay pedidos pendientes en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34m\nLista de pedidos pendientes:\u001B[0m");
            for (local.NextGen.modelo.Pedido pedido : pedidosPendientes) {
                System.out.println(pedido.toString());
            }
        }
    }

    public static void mostrarPedidosEnviados() throws SQLException {
        List<Pedido> pedidosEnviados = controlador.listarPedidosEnviados();
        if (pedidosEnviados.isEmpty()) {
            System.out.println("\u001B[31mNo hay pedidos enviados en la base de datos.\u001B[0m");
        } else {
            System.out.println("\u001B[34m\nLista de pedidos enviados:\u001B[0m");
            for (local.NextGen.modelo.Pedido pedido : pedidosEnviados) {
                System.out.println(pedido.toString());
            }
        }
    }
}

