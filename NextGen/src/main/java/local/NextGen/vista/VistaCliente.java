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

public class VistaCliente {
    private ControladorCliente controlador;

    public VistaCliente(ControladorCliente controladorCliente) {
        this.controlador = controladorCliente;
    }

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

        HBox botonesHBox = new HBox(10);
        botonesHBox.setAlignment(Pos.CENTER);
        botonesHBox.getChildren().addAll(
                btnListarTodosClientes, btnListarClientesEstandar, btnListarClientesPremium,
                btnAgregarCliente, btnEliminarCliente, btnActualizarCliente
        );

        botonesHBox.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(button, Priority.ALWAYS);
            }
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(botonesHBox, tablaClientes);

        return layout;
    }

    private void configurarColumnasTabla(TableView<Cliente> tabla) {
        TableColumn<Cliente, Integer> columnaIdCliente = new TableColumn<>("ID");
        columnaIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

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

        TableColumn<Cliente, BigDecimal> columnaDescuentoEnvio = new TableColumn<>("Dto.");
        columnaDescuentoEnvio.setCellValueFactory(new PropertyValueFactory<>("descuentoEnvio"));

        TableColumn<Cliente, String> columnaTipoCliente = new TableColumn<>("Tipo");
        columnaTipoCliente.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            String tipoCliente = "Estándar"; // Valor por defecto
            if (cliente instanceof ClientePremium) {
                tipoCliente = "Premium";
            }
            return new SimpleStringProperty(tipoCliente);
        });

        tabla.getColumns().addAll(columnaIdCliente, columnaNombre, columnaDomicilio, columnaNIF, columnaEmail, columnaCuotaAnual, columnaDescuentoEnvio, columnaTipoCliente);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    private void listarClientes(TableView<Cliente> tabla, List<Cliente> clientes) {
        tabla.getItems().setAll(clientes);
    }
    private void listarClientesEstandar(TableView<Cliente> tabla) {
        List<Cliente> clientesEstandar = controlador.listarClientesEstandard();
        tabla.getItems().setAll(clientesEstandar);
    }

    private void listarClientesPremium(TableView<Cliente> tabla) {
        List<Cliente> clientesPremium = controlador.listarClientesPremium();
        tabla.getItems().setAll(clientesPremium);
    }

    private void agregarCliente(TableView<Cliente> tabla) {
        Stage dialogStage = createDialogStage("Agregar Nuevo Cliente");
        GridPane grid = createClienteForm();
        grid.setVgap(10);

        // Campos y botones para agregar cliente
        TextField txtNombre = new TextField();
        TextField txtDomicilio = new TextField();
        TextField txtNIF = new TextField();
        TextField txtEmail = new TextField();

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
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error de Validación");
                alerta.setHeaderText("Campos Obligatorios Faltantes");
                alerta.setContentText("Por favor, completa todos los campos.");
                alerta.showAndWait();
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

    private void eliminarCliente(TableView<Cliente> tabla) {
        Cliente clienteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null && controlador.eliminarCliente(clienteSeleccionado.getNif())) {
            listarClientes(tabla, controlador.listarClientes());
        } else {
            // Mostrar mensaje de error o indicación
        }
    }

    private void actualizarCliente(TableView<Cliente> tabla) {
        Cliente clienteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            // Mostrar mensaje de error o indicación
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

    private Stage createDialogStage(String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        return dialogStage;
    }

    private GridPane createClienteForm() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));
        return grid;
    }
}
