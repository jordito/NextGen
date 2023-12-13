
package local.NextGen.vista;
import local.NextGen.controlador.Controlador;
import local.NextGen.exceptions.CustomException;

import java.sql.SQLException;
import java.util.Scanner;

public class  GestionOs {
    private static final Scanner teclado = new Scanner(System.in);
    private final Controlador controlador;
    public GestionOs() throws SQLException {
        controlador = new Controlador();
    }

    private static char pedirOpcion() {
        String resp;
        System.out.println("Elige una opción del menú: ");
        resp = teclado.nextLine();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }

    public static void iniciar() {
        try {
            boolean salir = false;
            char opcion;

            do {
                System.out.println("╔══════════════════════════════╗");
                System.out.println("║         MENÚ PRINCIPAL       ║");
                System.out.println("╠══════════════════════════════╣");
                System.out.println("║ 1. Gestión de Artículos      ║");
                System.out.println("║ 2. Gestión de Pedidos        ║");
                System.out.println("║ 3. Gestión de Clientes       ║");
                System.out.println("║ 0. Salir                     ║");
                System.out.println("╚══════════════════════════════╝");

                opcion = pedirOpcion();

                switch (opcion) {
                    case '1' -> VistaArticulo.gestionArticulos();
                    case '2' -> VistaPedido.gestionPedidos();
                    case '3' -> VistaCliente.gestionClientes();
                    case '0' -> salir = true;
                    default ->
                            System.out.println("\u001B[31m" + "Opción inválida. Por favor, elija una opción válida." + "\u001B[0m");
                }
            } while (!salir);

        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
    }
}
