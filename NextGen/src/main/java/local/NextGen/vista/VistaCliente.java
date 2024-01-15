package local.NextGen.vista;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import local.NextGen.controlador.ControladorCliente;
import local.NextGen.modelo.entidades.Cliente;
import local.NextGen.modelo.entidades.ClienteEstandard;
import local.NextGen.modelo.entidades.ClientePremium;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Clase VistaCliente que representa la interfaz de usuario para la gestión de clientes.
 * Ofrece funcionalidades para listar, agregar, actualizar y eliminar clientes, así como para mostrar diferentes tipos de clientes.
 */
public class VistaCliente {
    private ControladorCliente controlador;

    /**
     * Construye una instancia de VistaCliente con un controlador específico.
     *
     * @param controladorCliente Controlador que gestiona la lógica de negocio de clientes.
     */
    public VistaCliente(ControladorCliente controladorCliente) {
        this.controlador = controladorCliente;
    }

    /**
     * Crea y devuelve un nodo de la interfaz de usuario para la gestión de clientes.
     * Este nodo incluye una tabla para mostrar los clientes y botones para las operaciones de gestión.
     *
     * @return Un Node que contiene la interfaz de usuario para la gestión de clientes.
     */
    public Node getVistaClienteNode() {
        TableView<Cliente> tablaClientes = new TableView<>();
        configurarColumnasTabla(tablaClientes);

        Button btnListarTodosClientes = new Button("Listar Todos los Clientes");
        Button btnListarClientesEstandar = new Button("Listar Clientes Estándar");
        Button btnListarClientesPremium = new Button("Listar Clientes Premium");
        Button btnAgregarCliente = new Button("Agregar Cliente");
        Button btnEliminarCliente = new Button("Eliminar Cliente");
        Button btnActualizarCliente = new Button("Actualizar Cliente");

        btnListarTodosClientes.setOnAction(e -> listarClientes(tablaClientes, controlador.listarClientes()));
        btnListarClientesEstandar.setOnAction(e -> listarClientesEstandar(tablaClientes));
        btnListarClientesPremium.setOnAction(e -> listarClientesPremium(tablaClientes));
        btnAgregarCliente.setOnAction(e -> agregarCliente(tablaClientes));
        btnEliminarCliente.setOnAction(e -> eliminarCliente(tablaClientes));
        btnActualizarCliente.setOnAction(e -> actualizarCliente(tablaClientes));

        HBox filaBotones1 = new HBox(10, btnListarTodosClientes, btnListarClientesEstandar, btnListarClientesPremium);
        filaBotones1.setAlignment(Pos.CENTER);

        HBox filaBotones2 = new HBox(10, btnAgregarCliente, btnEliminarCliente, btnActualizarCliente);
        filaBotones2.setAlignment(Pos.CENTER);

        Stream.of(filaBotones1, filaBotones2).flatMap(hbox -> hbox.getChildren().stream()).forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(button, Priority.ALWAYS);
            }
        });

        VBox menuContainer = new VBox(10);
        menuContainer.getChildren().addAll(filaBotones1, filaBotones2, tablaClientes);

        return menuContainer;
    }

    /**
     * Configura las columnas de la tabla de clientes.
     * Define cómo se muestran los datos de los clientes en la tabla.
     *
     * @param tabla La TableView que se configurará.
     */
    private void configurarColumnasTabla(TableView<Cliente> tabla) {
        TableColumn<Cliente, Integer> columnaIdCliente = new TableColumn<>("ID");
        columnaIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnaIdCliente.setMinWidth(3);

        TableColumn<Cliente, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombre.setMinWidth(50);

        TableColumn<Cliente, String> columnaDomicilio = new TableColumn<>("Domicilio");
        columnaDomicilio.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
        columnaDomicilio.setMinWidth(50);

        TableColumn<Cliente, String> columnaNIF = new TableColumn<>("NIF");
        columnaNIF.setCellValueFactory(new PropertyValueFactory<>("nif"));
        columnaNIF.setMinWidth(10);

        TableColumn<Cliente, String> columnaEmail = new TableColumn<>("Email");
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnaEmail.setMinWidth(20);

        TableColumn<Cliente, BigDecimal> columnaCuotaAnual = new TableColumn<>("Cuota Anual");
        columnaCuotaAnual.setCellValueFactory(new PropertyValueFactory<>("cuotaAnual"));
        columnaCuotaAnual.setMinWidth(11);

        TableColumn<Cliente, BigDecimal> columnaDescuentoEnvio = new TableColumn<>("Dto.");
        columnaDescuentoEnvio.setCellValueFactory(new PropertyValueFactory<>("descuentoEnvio"));
        columnaDescuentoEnvio.setMinWidth(5);

        TableColumn<Cliente, String> columnaTipoCliente = new TableColumn<>("Tipo");
        columnaTipoCliente.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            String tipoCliente = "Estándar";
            if (cliente instanceof ClientePremium) {
                tipoCliente = "Premium";
            }
            return new SimpleStringProperty(tipoCliente);
        });

        tabla.getColumns().addAll(columnaIdCliente, columnaNombre, columnaDomicilio, columnaNIF, columnaEmail, columnaCuotaAnual, columnaDescuentoEnvio, columnaTipoCliente);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Lista todos los clientes en la tabla.
     *
     * @param tabla La TableView donde se listarán los clientes.
     * @param clientes La lista de clientes a mostrar.
     */
    private void listarClientes(TableView<Cliente> tabla, List<Cliente> clientes) {
        tabla.getItems().setAll(clientes);
    }

    /**
     * Lista solo los clientes estándar en la tabla.
     *
     * @param tabla La TableView donde se listarán los clientes estándar.
     */
    private void listarClientesEstandar(TableView<Cliente> tabla) {
        List<Cliente> clientesEstandar = controlador.listarClientesEstandard();
        tabla.getItems().setAll(clientesEstandar);
    }

    /**
     * Lista solo los clientes premium en la tabla.
     *
     * @param tabla La TableView donde se listarán los clientes premium.
     */
    private void listarClientesPremium(TableView<Cliente> tabla) {
        List<Cliente> clientesPremium = controlador.listarClientesPremium();
        tabla.getItems().setAll(clientesPremium);
    }

    /**
     * Presenta una interfaz para agregar un nuevo cliente.
     * Incluye campos para los detalles del cliente y un botón para confirmar la adición.
     *
     * @param tabla La TableView para reflejar los cambios una vez agregado el cliente.
     */
    private void agregarCliente(TableView<Cliente> tabla) {
        Stage dialogStage = createDialogStage("Agregar Nuevo Cliente");
        GridPane grid = createClienteForm();
        grid.setVgap(10);

        // Campos y botones para agregar cliente
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        TextField txtDomicilio = new TextField();
        txtDomicilio.setPromptText("Domicilio");
        TextField txtNIF = new TextField();
        txtNIF.setPromptText("NIF");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        // Selector de tipo de cliente
        ComboBox<String> tipoClienteComboBox = new ComboBox<>();
        tipoClienteComboBox.getItems().addAll("Estándar", "Premium");

        // Campos específicos para ClientePremium
        Label lblCuotaAnualInfo = new Label("Cuota Anual:                    30€");
        Label lblDescuentoEnvioInfo = new Label("Dto. en Gastos de Envío:  20%");
        lblCuotaAnualInfo.setVisible(false);
        lblDescuentoEnvioInfo.setVisible(false);

        // Mostrar campos de ClientePremium cuando sea necesario
        tipoClienteComboBox.setOnAction(e -> {
            boolean esPremium = tipoClienteComboBox.getValue().equals("Premium");
            lblCuotaAnualInfo.setVisible(esPremium);
            lblDescuentoEnvioInfo.setVisible(esPremium);
        });

        // Añadir campos al grid
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Domicilio:"), 0, 1);
        grid.add(txtDomicilio, 1, 1);
        grid.add(new Label("NIF:"), 0, 2);
        grid.add(txtNIF, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(txtEmail, 1, 3);
        grid.add(new Label("Tipo de Cliente:"), 0, 4);
        grid.add(tipoClienteComboBox, 1, 4);
        grid.add(lblCuotaAnualInfo, 0, 5);
        grid.add(lblDescuentoEnvioInfo, 0, 6);

        // Botón de confirmación
        Button btnConfirmar = new Button("Agregar");
        btnConfirmar.setOnAction(e -> {
            if (txtNombre.getText().isEmpty() || txtDomicilio.getText().isEmpty() || txtNIF.getText().isEmpty() || txtEmail.getText().isEmpty()) {
                mostrarError("Error de Validación", "Por favor, completa todos los campos." );
                return;
            }
            Cliente nuevoCliente;
            if (tipoClienteComboBox.getValue().equals("Premium")) {
                nuevoCliente = new ClientePremium(null, txtNombre.getText(), txtDomicilio.getText(), txtNIF.getText(), txtEmail.getText(), new BigDecimal("30.00"), new BigDecimal("0.2"));
            } else {
                nuevoCliente = new ClienteEstandard(null, txtNombre.getText(), txtDomicilio.getText(), txtNIF.getText(), txtEmail.getText());
            }
            controlador.agregarCliente(nuevoCliente);
            listarClientes(tabla, controlador.listarClientes());
            dialogStage.close();
        });

        grid.add(btnConfirmar, 1, 8);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    /**
     * Presenta una interfaz para eliminar un cliente seleccionado.
     * Elimina el cliente de la tabla y de la base de datos si se confirma.
     *
     * @param tabla La TableView de donde se selecciona el cliente a eliminar.
     */
    private void eliminarCliente(TableView<Cliente> tabla) {
        Cliente clienteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null && controlador.eliminarCliente(clienteSeleccionado.getNif())) {
            listarClientes(tabla, controlador.listarClientes());
        } else {
            mostrarError("Selección de Cliente Requerida", "Por favor, selecciona un cliente de la lista antes de continuar.");
        }
    }

    /**
     * Presenta una interfaz para actualizar un cliente seleccionado.
     * Permite modificar los detalles del cliente seleccionado.
     *
     * @param tabla La TableView donde se selecciona el cliente a actualizar.
     */
    private void actualizarCliente(TableView<Cliente> tabla) {
        Cliente clienteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarError("Selección de Cliente Requerida", "Por favor, selecciona un cliente de la lista antes de continuar.");
            return;
        }

        Stage dialogStage = createDialogStage("Actualizar Cliente");
        GridPane grid = createClienteForm();

        // Campos y botones para actualizar cliente
        TextField txtNombre = new TextField(clienteSeleccionado.getNombre());
        TextField txtDomicilio = new TextField(clienteSeleccionado.getDomicilio());
        TextField txtNIF = new TextField(clienteSeleccionado.getNif());
        TextField txtEmail = new TextField(clienteSeleccionado.getEmail());

        // Añadir campos al grid
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Domicilio:"), 0, 1);
        grid.add(txtDomicilio, 1, 1);
        grid.add(new Label("NIF:"), 0, 2);
        grid.add(txtNIF, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(txtEmail, 1, 3);

        Button btnConfirmar = new Button("Actualizar");
        btnConfirmar.setOnAction(e -> {
            clienteSeleccionado.setNombre(txtNombre.getText());
            clienteSeleccionado.setDomicilio(txtDomicilio.getText());
            clienteSeleccionado.setNif(txtNIF.getText());
            clienteSeleccionado.setEmail(txtEmail.getText());
            controlador.actualizarCliente(clienteSeleccionado);
            listarClientes(tabla, controlador.listarClientes());
            dialogStage.close();
        });

        grid.add(btnConfirmar, 1, 4);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    /**
     * Crea un diálogo para agregar o actualizar clientes.
     *
     * @param title El título del diálogo.
     * @return Un Stage que representa el diálogo.
     */
    private Stage createDialogStage(String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        return dialogStage;
    }

    /**
     * Crea un formulario para agregar o actualizar clientes.
     *
     * @return Un GridPane que contiene los campos del formulario.
     */
    private GridPane createClienteForm() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));
        return grid;
    }
    /**
     * Muestra un mensaje de error en una ventana de diálogo.
     *
     * @param titulo El título de la ventana de diálogo.
     * @param mensaje El mensaje de error a mostrar.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
