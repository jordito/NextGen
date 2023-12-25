package local.NextGen.vista;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import local.NextGen.controlador.*;
import local.NextGen.modelo.entidades.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VistaPedido {
    private ControladorPedido controladorPedido;
    private ControladorCliente controladorCliente;
    private ControladorArticulo controladorArticulo;

    public VistaPedido(ControladorPedido cp, ControladorCliente cc, ControladorArticulo ca) {
        controladorPedido = cp;
        controladorCliente = cc;
        controladorArticulo = ca;
    }

    public void gestionPedidos() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Pedidos");

        TableView<Pedido> tablaPedidos = new TableView<>();
        configurarColumnasTabla(tablaPedidos); // Configura las columnas de la tabla

        Button btnListarPedidos = new Button("Listar Pedidos");
        Button btnAgregarPedido = new Button("Agregar Pedido");
        Button btnEliminarPedido = new Button("Eliminar Pedido");

        btnListarPedidos.setOnAction(e -> listarPedidos(tablaPedidos));
        btnAgregarPedido.setOnAction(e -> agregarPedido(tablaPedidos));
        btnEliminarPedido.setOnAction(e -> eliminarPedido(tablaPedidos));

        VBox layout = new VBox(10, btnListarPedidos, btnAgregarPedido, btnEliminarPedido, tablaPedidos);
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void configurarColumnasTabla(TableView<Pedido> tabla) {
        TableColumn<Pedido, Integer> columnaNumero = new TableColumn<>("Número de Pedido");
        columnaNumero.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));

        TableColumn<Pedido, String> columnaCliente = new TableColumn<>("Cliente");
        columnaCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));

        TableColumn<Pedido, String> columnaFecha = new TableColumn<>("Fecha de Pedido");
        columnaFecha.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaHoraPedido().toString()));

        TableColumn<Pedido, String> columnaEstado = new TableColumn<>("Estado");
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<Pedido, String> columnaDetalles = new TableColumn<>("Detalles");
        columnaDetalles.setCellValueFactory(cellData ->
                new SimpleStringProperty(resumenDetallesPedido(cellData.getValue())));

        tabla.getColumns().addAll(columnaNumero, columnaCliente, columnaFecha, columnaEstado, columnaDetalles);
    }
    private String resumenDetallesPedido(Pedido pedido) {
        if (pedido == null) {
            return "No hay detalles";
        }

        Set<DetallePedido> detalles = controladorPedido.obtenerDetallesDePedido(pedido.getNumeroPedido());
        if (detalles.isEmpty()) {
            return "No hay detalles";
        }

        // Calcular el total de artículos y el costo total del pedido
        int totalArticulos = 0;
        double costoTotal = 0.0;
        for (DetallePedido detalle : detalles) {
            totalArticulos += detalle.getCantidad();
            costoTotal += detalle.getPrecioVenta().doubleValue() * detalle.getCantidad();
        }

        return String.format("Artículos: %d, Costo Total: %.2f", totalArticulos, costoTotal);
    }

    private void listarPedidos(TableView<Pedido> tabla) {
        List<Pedido> pedidos = controladorPedido.listarPedidos();
        tabla.getItems().setAll(pedidos);
    }

    private void agregarPedido(TableView<Pedido> tabla) {
        Stage dialogStage = createDialogStage("Agregar Nuevo Pedido");
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        // Campos para ingresar los datos del pedido
        TextField txtNifCliente = new TextField();
        txtNifCliente.setPromptText("NIF del Cliente");
        Button btnBuscarCliente = new Button("Buscar Cliente");
        Label lblNombreCliente = new Label();

        btnBuscarCliente.setOnAction(e -> {
            Cliente cliente = controladorCliente.obtenerClientePorNif(txtNifCliente.getText());
            if (cliente != null) {
                lblNombreCliente.setText("Cliente: " + cliente.getNombre());
            } else {
                lblNombreCliente.setText("Cliente no encontrado");
            }
        });

        grid.add(new Label("NIF Cliente:"), 0, 0);
        grid.add(txtNifCliente, 1, 0);
        grid.add(btnBuscarCliente, 2, 0);
        grid.add(lblNombreCliente, 1, 1);

        // Lista para agregar detalles del pedido
        ListView<DetallePedido> listaDetalles = new ListView<>();
        Button btnAgregarDetalle = new Button("Agregar Detalle");
        btnAgregarDetalle.setOnAction(e -> {
            agregarDetallePedido(listaDetalles, txtNifCliente.getText());
        });

        Button btnConfirmarPedido = new Button("Confirmar Pedido");
        btnConfirmarPedido.setOnAction(e -> {
            Cliente cliente = controladorCliente.obtenerClientePorNif(txtNifCliente.getText());
            if (cliente != null) {
                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setCliente(cliente);
                Set<DetallePedido> detalles = new HashSet<>(listaDetalles.getItems());
                controladorPedido.agregarPedido(nuevoPedido, detalles);
                listarPedidos(tabla);
                dialogStage.close();
            }
        });

        grid.add(btnAgregarDetalle, 1, 2);
        grid.add(listaDetalles, 1, 3);
        grid.add(btnConfirmarPedido, 1, 4);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void agregarDetallePedido(ListView<DetallePedido> listaDetalles, String nifCliente) {
        Stage dialogStage = createDialogStage("Agregar Detalle del Pedido");
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField txtCodigoArticulo = new TextField();
        txtCodigoArticulo.setPromptText("Código del Artículo");
        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");

        Button btnConfirmarDetalle = new Button("Agregar Detalle");
        btnConfirmarDetalle.setOnAction(e -> {
            try {
                Articulo articulo = controladorArticulo.obtenerArticuloPorCodigo(txtCodigoArticulo.getText());
                int cantidad = Integer.parseInt(txtCantidad.getText());
                if (articulo != null && cantidad > 0) {
                    DetallePedido detalle = new DetallePedido(new DetallePedido.DetallePedidoId(0, articulo.getCodigo()), null, articulo, cantidad, articulo.getPrecioVenta());
                    listaDetalles.getItems().add(detalle);
                    dialogStage.close();
                } else {
                    // Mostrar mensaje de error si el artículo no se encuentra o la cantidad es inválida
                    mostrarAlerta("Error", "Artículo no encontrado o cantidad inválida.");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor, ingrese una cantidad válida.");
            }
        });

        grid.add(new Label("Código del Artículo:"), 0, 0);
        grid.add(txtCodigoArticulo, 1, 0);
        grid.add(new Label("Cantidad:"), 0, 1);
        grid.add(txtCantidad, 1, 1);
        grid.add(btnConfirmarDetalle, 1, 2);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.show();

    }

    // Método auxiliar para mostrar alertas
  private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void eliminarPedido(TableView<Pedido> tabla) {
        Pedido pedidoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {
            controladorPedido.eliminarPedido(pedidoSeleccionado.getNumeroPedido());
            listarPedidos(tabla);
        } else {
            // Mostrar mensaje de error o indicación
            System.out.println("Seleccione un pedido para eliminar.");
        }
    }

    private Stage createDialogStage(String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        return dialogStage;
    }


}
