
package local.NextGen.controlador;

import local.NextGen.exceptions.CustomException;
import local.NextGen.modelo.*;
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
    /**
     * Atributo para la entrada de datos desde teclado.
     */
    private Scanner teclado = new Scanner(System.in);

    /**
     * Constructor que inicializa una instancia de Datos.
     */
    public Controlador() {
        datos = new Datos();
    }

    /**
     *  Agrega un método para obtener los datos
     */
    public Datos getDatos() {
        return datos;
    }

    /**
     * Lista y muestra todos los artículos presentes en la lista.
     */
    public void listarArticulos() {
        ListaArticulos listaArticulos = datos.getListaArticulos();
        if (listaArticulos.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay artículos registrados." + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de artículos:");
            for (Articulo articulo : listaArticulos.getArrayList()) {
                System.out.println(articulo.toString());
            }
        }
    }

    /**
     * Método para agregar un artículo a la lista.
     */
    public void agregarArticulo() throws CustomException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34m" + "Agregar un nuevo artículo:" + "\u001B[0m");

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        ListaArticulos listaArticulos = datos.getListaArticulos();

        boolean successNotFound = false;
        while (!successNotFound) {
            try {
                for (Articulo articuloExistente : listaArticulos.getArrayList()) {
                    if (articuloExistente.getCodigo().equals(codigo)) {
                        throw new CustomException("\u001B[31m" + "¡Error! Ya existe un artículo con el mismo código." + "\u001B[0m");
                    }
                }

                successNotFound = true;
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }

            String descripcion = "";
            boolean descriptionSuccess = false;
            while (!descriptionSuccess) {
                try {
                    System.out.print("Descripción: ");
                    descripcion = scanner.nextLine();
                    descriptionSuccess = true;
                } catch (Exception e) {
                    System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
                }
            }

            double precio = 0;
            boolean precioSuccess = false;
            while (!precioSuccess) {
                try {
                    System.out.print("Precio: ");
                    precio = scanner.nextDouble();
                    scanner.nextLine();
                    precioSuccess = true;
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
                }
            }

            double gastosEnvio = 0;
            boolean gastosEnvSuccess = false;
            while (!gastosEnvSuccess) {
                try {
                    System.out.print("Gastos de Envío: ");
                    gastosEnvio = scanner.nextDouble();
                    scanner.nextLine();
                    gastosEnvSuccess = true;
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
                }
            }

            int preparacionEnMin = 0;
            boolean preparacionMinSuccess = false;
            while (!preparacionMinSuccess) {
                try {
                    System.out.print("Preparación en Minutos: ");
                    preparacionEnMin = scanner.nextInt();
                    scanner.nextLine();
                    preparacionMinSuccess = true;
                } catch (Exception e) {
                    scanner.nextLine();
                    System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
                }
            }

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

    public void eliminarArticulo() {
        System.out.println("\u001B[34m" + "Escoge un articulo de la lista de articulos disponibles:" + "\u001B[0m");
        ListaArticulos listaArticulos = datos.getListaArticulos();
        for (Articulo articulo : listaArticulos.getArrayList()) {
            System.out.println("Código:       " + articulo.getCodigo());
            System.out.println("Descripción:  " + articulo.getDescripcion());
            System.out.println("------------------------");
        }

        System.out.print("\u001B[34m" + "Ingrese el código del articulo que desea eliminar: " + "\u001B[0m");
        String codigoArticulo = teclado.nextLine();

        Articulo articulo = listaArticulos.buscarPorCodigo(codigoArticulo);

        if (articulo != null) {
            System.out.println("\u001B[33m" + "¿Está seguro de que desea eliminar al siguiente articulo?" + "\u001B[0m");
            System.out.println("Código:       " + articulo.getCodigo());
            System.out.println("Descripción:  " + articulo.getDescripcion());
            System.out.print("Confirme (Si/No): ");
            String confirmacion = teclado.nextLine();

            if (confirmacion.equalsIgnoreCase("Si")) {
                listaArticulos.borrar(articulo);
                System.out.println("\u001B[33m" + "Articulo eliminado con éxito." + "\u001B[0m");
            } else {
                System.out.println("\u001B[32m" + "Eliminación de articulo cancelada." + "\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31m" + "¡Error! No se encontró un articulo con el código especificado." + "\u001B[0m");
        }
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
            for (Cliente cliente : listaClientes.getArrayList()) {
                System.out.println(cliente.toString());
            }
        }
    }

    public void listarClienteEstandard() {
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

    public void listarClientePremium() {
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

        String nif = "";
        boolean successNif = false;
        while (!successNif) {
            try {
                System.out.print("NIF: ");
                nif = scanner.nextLine();
                successNif = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        ListaClientes listaClientes = datos.getListaClientes();
        try {
            for (Cliente clienteExistente : listaClientes.getArrayList()) {
                if (clienteExistente.getNif().equals(nif)) {
                    throw new CustomException("\u001B[31m" + "¡Error! Ya existe un artículo con el mismo código." + "\u001B[0m");
                }
            }
        } catch (CustomException e) {
                System.out.println(e.getMessage());
        }

        String nombre = "";
        boolean successNombre = false;
        while (!successNombre) {
            try {
                System.out.print("Nombre: ");
                nombre = scanner.nextLine();
                successNombre = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        String email = "";
        boolean successEmail = false;
        while (!successEmail) {
            try {
                System.out.print("Email: ");
                email = scanner.nextLine();
                successEmail = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        String direccion = "";
        boolean successDireccion = false;
        while (!successDireccion) {
            try {
                System.out.print("Dirección de envío: ");
                direccion = scanner.nextLine();
                successDireccion = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        String tipoCliente = "";
        boolean successTipoCliente = false;
        while (!successTipoCliente) {
            try {
                System.out.print("Tipo de cliente (Estandard/Premium): ");
                tipoCliente = scanner.nextLine();
                successTipoCliente = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

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

    public void agregarCliente(String nif, String nombre, String email, String direccion, String tipoCliente) {
        Cliente nuevoCliente;

        if (tipoCliente.equalsIgnoreCase("Estandard")) {
            nuevoCliente = new ClienteEstandard(nif, nombre, email, direccion);
        } else if (tipoCliente.equalsIgnoreCase("Premium")) {
            nuevoCliente = new ClientePremium(nif, nombre, email, direccion);
        } else {
            System.out.println("\u001B[33m" + "Tipo de cliente no válido. Por favor, ingrese 'Estandard' o 'Premium'." + "\u001B[0m");
            return;
        }

        ListaClientes listaClientes = datos.getListaClientes();
        for (Cliente clienteExistente : listaClientes.getArrayList()) {
            if (clienteExistente.getNif().equals(nif)) {
                System.out.println("\u001B[31m" + "¡Error! Ya existe un cliente con el mismo NIF." + "\u001B[0m");
                return;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("\u001B[34m" + "Escoge un cliente de la lista de clientes disponibles:" + "\u001B[0m");
        ListaClientes listaClientes = datos.getListaClientes();
        for (Cliente cliente : listaClientes.getArrayList()) {
            System.out.println("NIF:     " + cliente.getNif());
            System.out.println("Nombre:  " + cliente.getNombre());
            System.out.println("------------------------");
        }

        String nifCliente = "";
        boolean successNifCliente = false;
        while (!successNifCliente) {
            try {
                System.out.print("\u001B[34m" + "Ingrese el NIF del Cliente que desea eliminar: " + "\u001B[0m");
                nifCliente = scanner.nextLine();
                successNifCliente = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        Cliente cliente = listaClientes.buscarPorNif(nifCliente);

        if (cliente != null) {
            System.out.println("\u001B[31m" + "¿Está seguro de que desea eliminar al siguiente cliente?" + "\u001B[0m");
            System.out.println("NIF:     " + cliente.getNif());
            System.out.println("Nombre:  " + cliente.getNombre());


            String confirmacion = "";
            boolean successConfirmar = false;
            while (!successConfirmar) {
                try {
                    System.out.print("Confirme (Si/No): ");
                    confirmacion = teclado.nextLine();
                    if (confirmacion.equalsIgnoreCase("Si") || confirmacion.equalsIgnoreCase("No")) {
                        if (confirmacion.equalsIgnoreCase("Si")) {
                            listaClientes.borrar(cliente);
                            System.out.println("\u001B[33m" + "Cliente eliminado con éxito." + "\u001B[0m");
                            successConfirmar = true;
                        } else {
                            System.out.println("\u001B[32m" + "Eliminación de cliente cancelada." + "\u001B[0m");
                            successConfirmar = true;
                        }
                    } else {
                        throw new CustomException("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
                    }
                } catch (CustomException e) {
                    System.out.println(e.getMessage());
                }
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
            for (Pedido pedido : listaPedidos.getArrayList()) {
                System.out.println(pedido.toString());
            }
        }
    }

    /**
     * Lista y muestra todos los pedidos pendientes presentes en la lista.
     */
    public void listarPedidosPendientes() {
        ListaPedidos listaPedidos = datos.getListaPedidos();
        if (listaPedidos.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay pedidos pendientes registrados.\n" + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de pedidos pendientes:\n" + "\u001B[0m");
            for (Pedido pedido : listaPedidos.getArrayList()) {

                if (!pedido.pedidoEnviado()) {
                    System.out.println(pedido.toString());
                }
            }
        }
    }

    /**
     * Lista y muestra todos los pedidos enviados presentes en la lista.
     */
    public void listarPedidosEnviados() {
        ListaPedidos listaPedidos = datos.getListaPedidos();


        if (listaPedidos.isEmpty()) {
            System.out.println("\u001B[31m" + "No hay pedidos enviados registrados.\n" + "\u001B[0m");
        } else {
            System.out.println("\u001B[34m" + "Lista de pedidos enviados:\n" + "\u001B[0m");
            for (Pedido pedido : listaPedidos.getArrayList()) {

                if (pedido.pedidoEnviado()) {
                    System.out.println(pedido.toString());
                }
            }
        }

    }

    /**
     * Agrega un nuevo pedido a la lista de pedidos solicitando al usuario los datos necesarios.
     */
    public void agregarPedido() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\u001B[34m" + "Agregar un nuevo pedido:" + "\u001B[0m");

        int numeroPedido = 0;
        boolean successNumPedido = false;
        while (!successNumPedido) {
            try {
                System.out.print("Número de Pedido: ");
                numeroPedido = scanner.nextInt();
                scanner.nextLine();
                successNumPedido = true;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        String fechaHoraStr = "";
        boolean successFechaHora = false;
        while (!successFechaHora) {
            try {
                System.out.print("Fecha y Hora (yyyy-MM-dd HH:mm:ss): ");
                fechaHoraStr = scanner.nextLine();
                successFechaHora = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

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
            System.out.println("Tipo cliente: " + cliente.tipoCliente());
            System.out.println("------------------------");
        }

        String nifCliente = "";
        boolean successNifCliente = false;
        while (!successNifCliente) {
            try {
                System.out.print("\u001B[34m" + "Ingrese el NIF del Cliente (o escriba " + "\u001B[33m" + "'nuevo'" + "\u001B[0m" + "\u001B[34m" + " para crear uno nuevo): " + "\u001B[0m");
                nifCliente = scanner.nextLine();
                if (nifCliente.equalsIgnoreCase("nuevo")) {
                    agregarCliente();
                    System.out.print("Ingrese el NIF del Cliente y continua con el pedido: ");
                    nifCliente = scanner.nextLine();
                }
                successNifCliente = true;
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }



        Cliente cliente = listaClientes.buscarPorNif(nifCliente);

        if (cliente == null) {
            System.out.println("\u001B[31m" + "¡Error! No se encontró un cliente con el NIF especificado." + "\u001B[0m");
            return;
        }


        System.out.print("Código del Artículo: ");

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



        String codigoArticulo = "";
        boolean successFindArticulo = false;
        Articulo articulo = null;
        while (!successFindArticulo) {
            try {
                System.out.print("Ingrese el Código del Artículo deseado: (o Escriba 0 para volver)");
                codigoArticulo = scanner.nextLine();

                if (codigoArticulo.equals("0")) return;
                articulo = datos.getListaArticulos().buscarPorCodigo(codigoArticulo);

                if (articulo == null) {
                    throw new CustomException("\u001B[31m" + "¡Error! No se encontró un artículo con el código especificado." + "\u001B[0m");
                }

                successFindArticulo = true;
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }

        int cantidad = 0;
        boolean successCantidad = false;
        while (!successCantidad) {
            try {
                System.out.print("Cantidad: ");
                cantidad = scanner.nextInt();
                scanner.nextLine();
                successCantidad = true;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }
        
        Pedido nuevoPedido = new Pedido(numeroPedido, fechaHora, cliente, articulo, cantidad);

        ListaPedidos listaPedidos = datos.getListaPedidos();
        listaPedidos.add(nuevoPedido);

        System.out.println("\u001B[34m" + "Pedido agregado con éxito:" + "\u001B[0m");
        System.out.println("+---------------------+-----------------------+");
        System.out.println("  Campo               | Valor                 ");
        System.out.println("+---------------------+-----------------------+");
        System.out.println(String.format("  Número de Pedido     | %s", nuevoPedido.getNumeroPedido()));
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
     */
    public void eliminarPedido() {
        Scanner scanner = new Scanner(System.in);
        // Mostrar todos los pedidos existentes al usuario.
        System.out.println("\u001B[34m" + "Elija un pedido de la lista de pedidos disponibles:" + "\u001B[0m");
        ListaPedidos listaPedidos = datos.getListaPedidos();
        for (Pedido pedido : listaPedidos.getArrayList()) {
            System.out.println("Número de Pedido: " + pedido.getNumeroPedido());
            System.out.println("Nombre del Cliente: " + pedido.getCliente().getNombre());
            System.out.println("------------------------");
        }

        int numeroPedido = 0;
        boolean numPedidoSuccess = false;

        while (!numPedidoSuccess) {
            try {
                // Solicitar al usuario que introduzca el número del pedido que desea eliminar.
                System.out.print("\u001B[34m" + "Introduzca el Número de Pedido del pedido que desea eliminar: " + "\u001B[0m");
                numeroPedido = scanner.nextInt();
                scanner.nextLine();  // Consumir el carácter de nueva línea.
                numPedidoSuccess = true;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("\u001B[31m" + "¡Error! Valor inválido." + "\u001B[0m");
            }
        }

        Pedido pedido = null;
        try {
            // Buscar el pedido en la lista de pedidos.
            pedido = listaPedidos.buscarPorNumeropedido(numeroPedido);
            if (pedido == null) throw new CustomException("\u001B[31m" + "¡Error! No se encontró un pedido con el Número de Pedido especificado." + "\u001B[0m");
            // Sí se encuentra el pedido, proceder con la confirmación para eliminarlo.
            System.out.println("\u001B[33m" + "¿Está seguro de que desea eliminar el siguiente pedido?" + "\u001B[0m");
            System.out.println("Número de Pedido: " + pedido.getNumeroPedido());
            System.out.println("Nombre del Cliente: " + pedido.getCliente().getNombre());
            System.out.print("Confirme (Si/No): ");
            String confirmacion = scanner.nextLine();
            // Si el usuario confirma, eliminar el pedido de la lista.

            if (confirmacion.equalsIgnoreCase("Si")) {
                listaPedidos.borrar(pedido);
                System.out.println("\u001B[33m" + "Pedido eliminado con éxito." + "\u001B[0m");
            } else {
                System.out.println("\u001B[32m" + "Eliminación de pedido cancelada." + "\u001B[0m");
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
    }
}