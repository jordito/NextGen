package NextGen.controlador;

import NextGen.modelo.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * Clase que actúa como el controlador principal de la aplicación.
 */
public class Controlador {
    /**
     * Atributo que representa la instancia de Datos.
     */
    private Datos datos;
    Scanner teclado = new Scanner(System.in);
    /**
     * Constructor que inicializa una instancia de Datos.
     */
    public Controlador() {
        datos = new Datos();
    }

    /**
     * Lista y muestra todos los articulos presentes en la lista.
     */

    public void listarArticulos() {
        ListaArticulos listaArticulos = datos.getListaArticulos();
        if (listaArticulos.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay artículos registrados." + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de artículos:");
            for (Articulo articulo : listaArticulos.getArrayList()) {
                System.out.println("Los articulos son los siguientes:\n " + "\u001B[0m" + articulo.toString());
            }
        }
    }

    /**
     * Método para agregar un artículo a la lista
     * El artículo que se desea agregar a la lista.
     */
    public void agregarArticulo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34m" + "Agregar un nuevo artículo:" + "\u001B[0m");

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        ListaArticulos listaArticulos = datos.getListaArticulos();
        for (Articulo articuloExistente : listaArticulos.getArrayList()) {
            if (articuloExistente.getCodigo().equals(codigo)) {
                System.out.println("\u001B[31m" + "¡Error! Ya existe un artículo con el mismo código." + "\u001B[0m");
                return;
            }
        }

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Gastos de Envío: ");
        double gastosEnvio = scanner.nextDouble();

        System.out.print("Preparación en Minutos: ");
        int preparacionEnMin = scanner.nextInt();

        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precio, gastosEnvio, preparacionEnMin);

        listaArticulos.add(nuevoArticulo);

        System.out.println("\u001B[34m" + "Datos del artículo agregado:" + "\u001B[0m");
        System.out.println("+---------------------+-----------------------+");
        System.out.println("  Campo               | Valor                  ");
        System.out.println("+---------------------+-----------------------+");
        System.out.println(String.format("  Código              | %s", nuevoArticulo.getCodigo()));
        System.out.println(String.format("  Descripción         | %s", nuevoArticulo.getDescripcion()));
        System.out.println(String.format("  Precio              | %.2f€", nuevoArticulo.getPrecio()));
        System.out.println(String.format("  Gastos de Envío     | %.2f€", nuevoArticulo.getGastosEnvio()));
        System.out.println(String.format("  Preparación (min)   | %d min", nuevoArticulo.getPreparacionEnMin()));
        System.out.println("+---------------------+-----------------------+");
    }

    /**
     * Método para eliminar un artículo de la lista.
     * @param articulo El artículo que se desea eliminar de la lista.
     */
    public void eliminarArticulo(Articulo articulo) {

    }

    /**
     * Lista y muestra todos los clientes presentes en la lista.
     */
    public void listarClientes() {
        ListaClientes listaClientes = datos.getListaClientes();
        if (listaClientes.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay clientes registrados.\n" + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de clientes:\n\n");
            for (Cliente cliente: listaClientes.getArrayList()) {
                System.out.println("Los clientes son los siguientes:\n " + "\u001B[0m" + cliente.toString());
            }
        }
    }

    public void listarClienteEstandard () {
        ListaClientes listaClientes = datos.getListaClientes();
        if (listaClientes.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay clientes estándar.\n" + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de clientes estándar:\n" + "\u001B[0m");
            for (Cliente cliente : listaClientes.getArrayList()) {
                if (cliente instanceof ClienteEstandard) {
                    System.out.println(cliente.toString());
                }
            }
        }
    }

    public void listarClientePremium () {
        ListaClientes listaClientes = datos.getListaClientes();
        if (listaClientes.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay clientes premium.\n" + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de clientes premium :\n" + "\u001B[0m");
            for (Cliente cliente : listaClientes.getArrayList()) {
                if (cliente instanceof ClientePremium) {
                    System.out.println(cliente.toString());
                }
            }
        }
    }
    /**
     * Agrega un nuevo cliente a la lista de clientes solicitando al usuario los datos necesarios.
     */
    public void agregarCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34m" + "Agregar un nuevo cliente:" + "\u001B[0m");

        System.out.print("NIF: ");
        String nif = scanner.nextLine();

        ListaClientes listaClientes = datos.getListaClientes();
        for (Cliente clienteExistente : listaClientes.getArrayList()) {
            if (clienteExistente.getNif().equals(nif)) {
                System.out.println("\u001B[31m" + "¡Error! Ya existe un cliente con el mismo NIF." + "\u001B[0m");
                return;
            }
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Dirección de envío: ");
        String direccion = scanner.nextLine();

        System.out.print("Tipo de cliente (Estandard/Premium): ");
        String tipoCliente = scanner.nextLine();

        Cliente nuevoCliente;

        if (tipoCliente.equalsIgnoreCase("Estandard")) {
            nuevoCliente = new ClienteEstandard(nif, nombre, email, direccion);
        } else if (tipoCliente.equalsIgnoreCase("Premium")) {
            nuevoCliente = new ClientePremium(nif, nombre, email, direccion);
        } else {
            System.out.println("\u001B[33m" + "Tipo de cliente no válido. Por favor, ingrese 'Estandard' o 'Premium'." + "\u001B[0m");

            do {
                System.out.print("Tipo de cliente (Estandard/Premium): ");
                tipoCliente = scanner.nextLine();
            } while (!tipoCliente.equalsIgnoreCase("Estandard") && !tipoCliente.equalsIgnoreCase("Premium"));

            if (tipoCliente.equalsIgnoreCase("Estandard")) {
                nuevoCliente = new ClienteEstandard(nif, nombre, email, direccion);
            } else {
                nuevoCliente = new ClientePremium(nif, nombre, email, direccion);
            }
        }

        listaClientes.add(nuevoCliente);

        System.out.println("\u001B[34m" + "Cliente agregado con éxito:" + "\u001B[0m");
        System.out.println("+---------------------+-----------------------+");
        System.out.println(" Campo               | Valor");
        System.out.println("+---------------------+-----------------------+");
        System.out.println(String.format(" NIF                 | %s", nuevoCliente.getNif()));
        System.out.println(String.format(" Nombre              | %s", nuevoCliente.getNombre()));
        System.out.println(String.format(" Email               | %s", nuevoCliente.getEmail()));
        System.out.println(String.format(" Dirección de envío  | %s", nuevoCliente.getDireccion()));
        System.out.println(String.format(" Tipo de Cliente     | %s", tipoCliente));
        System.out.println("+---------------------+-----------------------+");
    }

    /**
     * Método para eliminar un cliente de la lista
     * El cliente que se desea eliminar de la lista
     */
    public void eliminarCliente() {
        System.out.println("\u001B[34m" + "Escoge un cliente de la lista de clientes disponibles:" + "\u001B[0m");
        ListaClientes listaClientes = datos.getListaClientes();
        for (Cliente cliente : listaClientes.getArrayList()) {
            System.out.println("NIF:     " + cliente.getNif());
            System.out.println("Nombre:  " + cliente.getNombre());
            System.out.println("------------------------");
        }

        System.out.print("\u001B[34m" + "Ingrese el NIF del Cliente que desea eliminar: " + "\u001B[0m");
        String nifCliente = teclado.nextLine();

        Cliente cliente = listaClientes.buscarPorNif(nifCliente);

        if (cliente != null) {
            System.out.println("\u001B[31m" + "¿Está seguro de que desea eliminar al siguiente cliente?" + "\u001B[0m");
            System.out.println("NIF:     " + cliente.getNif());
            System.out.println("Nombre:  " + cliente.getNombre());
            System.out.print("Confirme (Si/No): ");
            String confirmacion = teclado.nextLine();

            if (confirmacion.equalsIgnoreCase("Si")) {
                listaClientes.borrar(cliente);
                System.out.println("\u001B[33m" + "Cliente eliminado con éxito." + "\u001B[0m");
            } else {
                System.out.println("\u001B[32m" + "Eliminación de cliente cancelada." + "\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31m" + "¡Error! No se encontró un cliente con el NIF especificado." + "\u001B[0m");
        }
    }
    /**
     * Lista y muestra todos los pedidos presentes en la lista.
     */
    public void listarPedidos() {
        ListaPedidos listaPedidos = datos.getListaPedidos();
        if (listaPedidos.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay pedidos registrados.\n" + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de pedidos:\n\n");
            for (Pedido pedido: listaPedidos.getArrayList()) {
                System.out.println("Los pedidos realizados son los siguientes:\n " + "\u001B[0m" + pedido.toString());
            }
        }
    }

    public void listarPedidosPendientes() {
        //FALTA AÑADIR
    }

    public void listarPedidosEnviados() {
        //FALTA AÑADIR
    }
    /**
     * Agrega un nuevo pedido a la lista de pedidos solicitando al usuario los datos necesarios.
     */
    public void agregarPedido() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34m" + "Agregar un nuevo pedido:" + "\u001B[0m");

        System.out.print("Número de Pedido: ");
        int numeroPedido = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Fecha y Hora (yyyy-MM-dd HH:mm:ss): ");
        String fechaHoraStr = scanner.nextLine();
        Date fechaHora;

        try {
            fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaHoraStr);
        } catch (ParseException e) {
            System.out.println("\u001B[33m" + "Formato de fecha y hora no válido. El pedido se creará con la fecha y hora actual." + "\u001B[0m");
            fechaHora = new Date();
        }

        System.out.println("\u001B[34m" + "Escoge un cliente de la lista de clientes disponibles:" + "\u001B[0m");
        ListaClientes listaClientes = datos.getListaClientes();
        for (Cliente cliente : listaClientes.getArrayList()) {
            System.out.println("NIF:          " + cliente.getNif());
            System.out.println("Nombre:       " + cliente.getNombre());
            System.out.println("Email:        " + cliente.getEmail());
            System.out.println("Dirección:    " + cliente.getDireccion());
            System.out.println("Tipo cliente: " + cliente.getDireccion());
            System.out.println("------------------------");
        }

        System.out.print("\u001B[34m" + "Ingrese el NIF del Cliente (o escriba " + "\u001B[33m" + "'nuevo'" + "\u001B[0m" + "\u001B[34m" + " para crear uno nuevo): " + "\u001B[0m");
        String nifCliente = scanner.nextLine();

        if (nifCliente.equalsIgnoreCase("nuevo")) {
            agregarCliente();
            System.out.print("Ingrese el NIF del Cliente y continua con el pedido: ");
            nifCliente = scanner.nextLine();
        }

        Cliente cliente = listaClientes.buscarPorNif(nifCliente);

        if (cliente == null) {
            System.out.println("\u001B[31m" + "¡Error! No se encontró un cliente con el NIF especificado." + "\u001B[0m");
            return;
        }

        System.out.println("\u001B[34m" + "Escoge un articulo de la lista de artículos disponibles:" + "\u001B[0m");
        ListaArticulos listaArticulos = datos.getListaArticulos();
        for (Articulo articulo : listaArticulos.getArrayList()) {
            System.out.println("Código:                 " + articulo.getCodigo());
            System.out.println("Descripción:            " + articulo.getDescripcion());
            System.out.println("Precio:                 " + articulo.getPrecio() + "€");
            System.out.println("Gastos de Envío:        " + articulo.getGastosEnvio() + "€");
            System.out.println("Preparación en Minutos: " + articulo.getPreparacionEnMin() + " min");
            System.out.println("------------------------");
        }

        System.out.print("Ingrese el Código del Artículo deseado: ");
        String codigoArticulo = scanner.nextLine();
        Articulo articulo = datos.getListaArticulos().buscarPorCodigo(codigoArticulo);

        if (articulo == null) {
            System.out.println("\u001B[31m" + "¡Error! No se encontró un artículo con el código especificado." + "\u001B[0m");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        Pedido nuevoPedido = new Pedido(numeroPedido, fechaHora, cliente, articulo, cantidad);

        ListaPedidos listaPedidos = datos.getListaPedidos();
        listaPedidos.add(nuevoPedido);

        System.out.println("\u001B[34m" + "Pedido agregado con éxito:" + "\u001B[0m");
        System.out.println("+---------------------+-----------------------+");
        System.out.println("  Campo               | Valor                 ");
        System.out.println("+---------------------+-----------------------+");
        System.out.println(String.format("  Número de Pedido     | %s", Pedido.getNumeroPedido()));
        System.out.println(String.format("  Fecha y Hora         | %s", nuevoPedido.getFechaHora()));
        System.out.println(String.format("  NIF del Cliente      | %s", nuevoPedido.getCliente().getNif()));
        System.out.println(String.format("  Nombre del Cliente   | %s", nuevoPedido.getCliente().getNombre()));
        System.out.println(String.format("  Código del Artículo  | %s", nuevoPedido.getArticulo().getCodigo()));
        System.out.println(String.format("  Descripción Artículo | %s", nuevoPedido.getArticulo().getDescripcion()));
        System.out.println(String.format("  Cantidad             | %d", nuevoPedido.getCantidad()));
        System.out.println(String.format("  Precio Total         | %.2f€", nuevoPedido.precioTotal()));
        System.out.println("+---------------------+-----------------------+");
    }

    /**
     * Método para eliminar un pedido de la lista
     * @param pedido El pedido que se desea eliminar de la lista
     */
    public void eliminarPedido(Pedido pedido) {
        //eliminar pedido
    }
}