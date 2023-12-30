package local.NextGen.vista;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import local.NextGen.controlador.ControladorArticulo;
import local.NextGen.controlador.ControladorCliente;
import local.NextGen.controlador.ControladorPedido;

/**
 * Clase principal que lanza la interfaz gráfica de usuario de la aplicación Online Store.
 * Esta clase se encarga de configurar la ventana principal, la barra de menú y las vistas
 * asociadas a las acciones de gestión de artículos, pedidos y clientes.
 */
public class GestionOs extends Application {

    // Declaración de controladores
    private ControladorArticulo controladorArticulo;
    private ControladorCliente controladorCliente;
    private ControladorPedido controladorPedido;
    private BorderPane rootLayout;

    /**
     * Inicia y muestra la ventana principal de la aplicación.
     * Configura la barra de menú superior y el contenido inicial del panel central.
     *
     * @param primaryStage el escenario principal sobre el cual se construye la interfaz de usuario.
     */
    public void start(Stage primaryStage) {
        controladorArticulo = new ControladorArticulo();
        controladorCliente = new ControladorCliente();
        controladorPedido = new ControladorPedido();

        rootLayout = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu menuInicio = new Menu("INICIO");
        MenuItem inicio = new MenuItem("Inicio");
        inicio.setOnAction(e -> mostrarPantallaInicio());
        menuInicio.getItems().add(inicio);

        Menu menuArticulos = new Menu("ARTÍCULOS");
        MenuItem articulos = new MenuItem("Gestión Artículos");
        articulos.setOnAction(e -> mostrarVistaArticulos());
        menuArticulos.getItems().add(articulos);

        Menu menuPedidos = new Menu("PEDIDOS");
        MenuItem pedidos = new MenuItem("Gestión Pedidos");
        //pedidos.setOnAction(e -> mostrarVistaPedidos());
        menuPedidos.getItems().add(pedidos);

        Menu menuClientes = new Menu("CLIENTES");
        MenuItem clientes = new MenuItem("Gestión Clientes");
        clientes.setOnAction(e -> mostrarVistaClientes());
        menuClientes.getItems().add(clientes);

        menuBar.getMenus().addAll(menuInicio, menuArticulos, menuPedidos, menuClientes);

        rootLayout.setTop(menuBar);

        mostrarPantallaInicio();

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setTitle("Online Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la pantalla de inicio en el panel central de la ventana principal.
     * Esta pantalla incluye un mensaje de bienvenida y botones para navegar a las diferentes
     * secciones de la aplicación.
     */
    private void mostrarPantallaInicio() {
        Label bienvenida = new Label("BIENVENIDO A ONLINE STORE!!!");
        bienvenida.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 24));

        Button btnGestionArticulos = new Button("GESTIÓN ARTÍCULOS");
        btnGestionArticulos.setOnAction(event -> mostrarVistaArticulos());

        Button btnGestionPedidos = new Button("GESTIÓN PEDIDOS");
        //btnGestionPedidos.setOnAction(event -> mostrarVistaPedidos());

        Button btnGestionClientes = new Button("GESTIÓN CLIENTES");
        btnGestionClientes.setOnAction(event -> mostrarVistaClientes());

        Button btnSalir = new Button("SALIR");
        btnSalir.setOnAction(event -> System.exit(0));

        VBox vbox = new VBox(20, bienvenida, btnGestionArticulos, btnGestionPedidos, btnGestionClientes, btnSalir);
        vbox.setAlignment(Pos.CENTER);
        rootLayout.setCenter(vbox);
    }

    /**
     * Muestra la vista de gestión de articulos en el panel central de la ventana principal.
     * Esta vista proporciona una interfaz para listar, agregar, eliminar y actualizar articulos.
     */
    private void mostrarVistaArticulos() {
        VistaArticulo vistaArticulo = new VistaArticulo(controladorArticulo);
        rootLayout.setCenter(vistaArticulo.getVistaArticuloNode());
    }

    /**
     * Muestra la vista de gestión de pedidos en el panel central de la ventana principal.
     * Esta vista proporciona una interfaz para listar, agregar y eliminar pedidos.
     *//*
    private void mostrarVistaPedidos() {
        VistaPedido vistaPedido = new VistaPedido(controladorPedido, controladorCliente, controladorArticulo);
        rootLayout.setCenter(vistaPedido.getVistaPedidoNode());
    }*/

    /**
     * Muestra la vista de gestión de clientes en el panel central de la ventana principal.
     * Esta vista proporciona una interfaz para listar, agregar, eliminar y actualizar clientes.
     */
    private void mostrarVistaClientes() {
        VistaCliente vistaCliente = new VistaCliente(controladorCliente);
        rootLayout.setCenter(vistaCliente.getVistaClienteNode());
    }

    /**
     * Punto de entrada principal para la aplicación.
     *
     * @param args argumentos de línea de comandos pasados al programa.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
