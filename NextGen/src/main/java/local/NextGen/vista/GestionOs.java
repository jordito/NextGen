package local.NextGen.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import local.NextGen.controlador.ControladorArticulo;
import local.NextGen.controlador.ControladorCliente;
import local.NextGen.controlador.ControladorPedido;

public class GestionOs extends Application {

    // Declaración de controladores
    private ControladorArticulo controladorArticulo;
    private ControladorCliente controladorCliente;
    private ControladorPedido controladorPedido;

    @Override
    public void start(Stage primaryStage) {
        // Inicialización de controladores
        controladorArticulo = new ControladorArticulo();
        controladorCliente = new ControladorCliente();
        controladorPedido = new ControladorPedido();

        // Crear botones para las opciones del menú
        Button btnGestionArticulos = new Button("Gestión de Artículos");
        Button btnGestionPedidos = new Button("Gestión de Pedidos");
        Button btnGestionClientes = new Button("Gestión de Clientes");
        Button btnSalir = new Button("Salir");

        // Asignar eventos a los botones
        btnGestionArticulos.setOnAction(event -> gestionarArticulos());
        btnGestionPedidos.setOnAction(event -> gestionarPedidos());
        btnGestionClientes.setOnAction(event -> gestionarClientes());
        btnSalir.setOnAction(event -> primaryStage.close());

        // Organizar botones en un VBox
        VBox layout = new VBox(10, btnGestionArticulos, btnGestionPedidos, btnGestionClientes, btnSalir);

        // Configurar la escena y el escenario (Stage)
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Gestión del Sistema");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void gestionarArticulos() {
        VistaArticulo vistaArticulo = new VistaArticulo(controladorArticulo);
        vistaArticulo.gestionArticulos();
    }

    private void gestionarPedidos() {
        VistaPedido vistaPedido = new VistaPedido(controladorPedido, controladorCliente, controladorArticulo);
        vistaPedido.gestionPedidos();
    }

    private void gestionarClientes() {
        VistaCliente vistaCliente = new VistaCliente(controladorCliente);
        vistaCliente.gestionClientes();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
