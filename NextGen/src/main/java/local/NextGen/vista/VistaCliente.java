package local.NextGen.vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import local.NextGen.controlador.ControladorCliente;
import local.NextGen.modelo.entidades.Cliente;
import local.NextGen.modelo.entidades.ClienteEstandard;
import local.NextGen.modelo.entidades.ClientePremium;

import java.math.BigDecimal;
import java.util.List;

public class VistaCliente {
    private ControladorCliente controlador;

    public VistaCliente(ControladorCliente controladorCliente) {
        this.controlador = controladorCliente;
    }

    public void gestionClientes() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Clientes");

        TableView<Cliente> tablaClientes = new TableView<>();
        configurarColumnasTabla(tablaClientes);

        Button btnListarClientes = new Button("Listar Todos los Clientes");
        Button btnAgregarCliente = new Button("Agregar Cliente");
        Button btnEliminarCliente = new Button("Eliminar Cliente");
        Button btnActualizarCliente = new Button("Actualizar Cliente");

        btnListarClientes.setOnAction(e -> listarClientes(tablaClientes, controlador.listarClientes()));
        btnAgregarCliente.setOnAction(e -> agregarCliente(tablaClientes));
        btnEliminarCliente.setOnAction(e -> eliminarCliente(tablaClientes));
        btnActualizarCliente.setOnAction(e -> actualizarCliente(tablaClientes));

        VBox layout = new VBox(10, btnListarClientes, btnAgregarCliente, btnEliminarCliente, btnActualizarCliente, tablaClientes);
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void configurarColumnasTabla(TableView<Cliente> tabla) {
        TableColumn<Cliente, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Cliente, String> columnaDomicilio = new TableColumn<>("Domicilio");
        columnaDomicilio.setCellValueFactory(new PropertyValueFactory<>("domicilio"));

        TableColumn<Cliente, String> columnaNIF = new TableColumn<>("NIF");
        columnaNIF.setCellValueFactory(new PropertyValueFactory<>("nif"));

        TableColumn<Cliente, String> columnaEmail = new TableColumn<>("Email");
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Cliente, BigDecimal> columnaCuotaAnual = new TableColumn<>("Cuota Anual");
        columnaCuotaAnual.setCellValueFactory(new PropertyValueFactory<>("cuotaAnual"));
        columnaCuotaAnual.setCellFactory(tc -> new TableCell<Cliente, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal cuotaAnual, boolean empty) {
                super.updateItem(cuotaAnual, empty);
                if (empty || cuotaAnual == null) {
                    setText(null);
                } else {
                    setText(cuotaAnual.toString());
                }
            }
        });

        TableColumn<Cliente, BigDecimal> columnaDescuentoEnvio = new TableColumn<>("Descuento Envío");
        columnaDescuentoEnvio.setCellValueFactory(new PropertyValueFactory<>("descuentoEnvio"));
        columnaDescuentoEnvio.setCellFactory(tc -> new TableCell<Cliente, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal descuentoEnvio, boolean empty) {
                super.updateItem(descuentoEnvio, empty);
                if (empty || descuentoEnvio == null) {
                    setText(null);
                } else {
                    setText(descuentoEnvio.toString());
                }
            }
        });

        tabla.getColumns().addAll(columnaNombre, columnaDomicilio, columnaNIF, columnaEmail, columnaCuotaAnual, columnaDescuentoEnvio);
    }

    private void listarClientes(TableView<Cliente> tabla, List<Cliente> clientes) {
        tabla.getItems().setAll(clientes);
    }

    private void agregarCliente(TableView<Cliente> tabla) {
        Stage dialogStage = createDialogStage("Agregar Nuevo Cliente");
        GridPane grid = createClienteForm();

        // Campos y botones para agregar cliente
        TextField txtNombre = new TextField();
        TextField txtDomicilio = new TextField();
        TextField txtNIF = new TextField();
        TextField txtEmail = new TextField();

        // Añadir campos al grid
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Domicilio:"), 0, 1);
        grid.add(txtDomicilio, 1, 1);
        grid.add(new Label("NIF:"), 0, 2);
        grid.add(txtNIF, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(txtEmail, 1, 3);

        Button btnConfirmar = new Button("Agregar");
        btnConfirmar.setOnAction(e -> {
            Cliente nuevoCliente = new ClienteEstandard(null, txtNombre.getText(), txtDomicilio.getText(), txtNIF.getText(), txtEmail.getText());
            controlador.agregarCliente(nuevoCliente);
            listarClientes(tabla, controlador.listarClientes());
            dialogStage.close();
        });

        grid.add(btnConfirmar, 1, 4);

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
