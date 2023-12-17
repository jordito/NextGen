package local.NextGen.vista;
import local.NextGen.exceptions.CustomException;
import local.NextGen.modelo.*;
import local.NextGen.modelo.DAO.ArticuloDAO;

import java.sql.SQLException;
import java.util.Scanner;
import local.NextGen.controlador.*;
import local.NextGen.modelo.DAO.DAOException;

public class VistaArticulo {
    static Scanner scanner = new Scanner(System.in);

    private static char pedirOpcion() {
        System.out.print("Ingrese la opción deseada: ");
        return scanner.nextLine().charAt(0);
    }
    private static void agregarNuevoArticulo() {
        System.out.println("\u001B[34mIngrese los detalles del nuevo artículo:\u001B[0m");

        System.out.print("\u001B[34mCodigo:  \u001B[0m");
        String codigo = scanner.nextLine();

        System.out.print("\u001B[34mDescripción: \u001B[0m");
        String descripcion = scanner.nextLine();

        System.out.print("\u001B[34mPrecio: \u001B[0m");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("\u001B[34mGastos de envío: \u001B[0m");
        double gastosEnvio = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("\u001B[34mTiempo de preparación: \u001B[0m");
        int tiempoPreparacion = scanner.nextInt();
        scanner.nextLine();

        Articulo nuevoArticulo = new Articulo(codigo, descripcion, precio, gastosEnvio, tiempoPreparacion);

        boolean success = true;
        try {
            Controlador.agregarArticulo(nuevoArticulo);
        } catch (DAOException e) {
            success = false;
        }
        if (success) {
            System.out.println("\u001B[32m\nArtículo introducido con éxito:\u001B[0m");
            System.out.println(nuevoArticulo);
        } else {
            System.out.println("\u001B[31mError al introducir el artículo\u001B[0m");
        }
    }

    private static void eliminarArticulo() {
        System.out.print("\u001B[34mIngrese el código del artículo que desea eliminar: \u001B[0m");
        String codigo = scanner.nextLine();

        if (Controlador.eliminarArticulo(codigo)) {
            System.out.println("\u001B[32mArtículo eliminado con éxito\u001B[0m");
        } else {
            System.out.println("\u001B[31mError al eliminar el artículo\u001B[0m");
        }
    }
    public static void actualizarArticulo() throws SQLException {
        System.out.print("\u001B[34mIngrese el código del artículo a actualizar: \u001B[0m");
        String codigo = scanner.nextLine();

        ArticuloDAO ad = new ArticuloDAO();
        Articulo articulo = ad.obtenerPorCodigo(codigo);
        if (articulo != null) {
            System.out.println("\u001B[34mDetalles actuales del artículo:\u001B[0m");
            System.out.println(articulo);

            System.out.println("\u001B[34mIngrese los nuevos detalles (presione Enter para mantener los actuales):\u001B[0m");

            System.out.print("\u001B[34mNueva descripción: \u001B[0m");
            String nuevaDescripcion = scanner.nextLine().trim();
            if (!nuevaDescripcion.isEmpty()) {
                articulo.setDescripcion(nuevaDescripcion);
            }

            System.out.print("\u001B[34mNuevo precio: \u001B[0m");
            String nuevoPrecioStr = scanner.nextLine().trim();
            if (!nuevoPrecioStr.isEmpty()) {
                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                articulo.setPrecio(nuevoPrecio);
            }

            System.out.print("\u001B[34mNuevos gastos de envío: \u001B[0m");
            String nuevosGastosEnvioStr = scanner.nextLine().trim();
            if (!nuevosGastosEnvioStr.isEmpty()) {
                double nuevosGastosEnvio = Double.parseDouble(nuevosGastosEnvioStr);
                articulo.setGastosEnvio(nuevosGastosEnvio);
            }

            System.out.print("\u001B[34mNuevo tiempo de preparación: \u001B[0m");
            String nuevoTiempoPreparacionStr = scanner.nextLine().trim();
            if (!nuevoTiempoPreparacionStr.isEmpty()) {
                int nuevoTiempoPreparacion = Integer.parseInt(nuevoTiempoPreparacionStr);
                articulo.setTiempoPreparacion(nuevoTiempoPreparacion);
            }

            if (Controlador.actualizarArticulo(articulo)) {
                System.out.println("\u001B[32mArtículo actualizado con éxito\u001B[0m");
                System.out.println(articulo);
            } else {
                System.out.println("\u001B[31mError al actualizar el artículo\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31mNo se encontró ningún artículo con el código proporcionado\u001B[0m");
        }
    }
    public static void gestionArticulos() throws CustomException, SQLException {
        char opcion;
        do {
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║     GESTIÓN ARTICULOS        ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Listar Articulos          ║");
            System.out.println("║ 2. Añadir Articulo           ║");
            System.out.println("║ 3. Actualizar Articulo       ║");
            System.out.println("║ 4. Eliminar Articulo         ║");
            System.out.println("║ 0. Salir                     ║");
            System.out.println("╚══════════════════════════════╝");
            opcion = pedirOpcion();
            switch (opcion) {
                case '1' -> Controlador.listarArticulos();
                case '2' -> agregarNuevoArticulo();
                case '3' -> actualizarArticulo();
                case '4' -> eliminarArticulo();
                case '0' -> GestionOs.iniciar();
                default -> System.out.println("\u001B[31m" + "Opción inválida. Por favor, elija una opción válida." + "\u001B[0m");
            }
        } while (opcion != '0');
    }
}

