package local.NextGen.vista;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import local.NextGen.controlador.*;
import local.NextGen.modelo.entidades.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class VistaPedido {
    private ControladorPedido controladorPedido;
    private ControladorCliente controladorCliente;
    private ControladorArticulo controladorArticulo;

    public VistaPedido(ControladorPedido cp, ControladorCliente cc, ControladorArticulo ca) {
        controladorPedido = cp;
        controladorCliente = cc;
        controladorArticulo = ca;
    }

    public Node getVistaPedidoNode() {
        TableView<Pedido> tablaPedidos = new TableView<>();
        configurarColumnasTabla(tablaPedidos);

        Button btnListarPedidos = new Button("Listar Pedidos");
        Button btnListarPedidosEnviados = new Button("Listar Pedidos Enviados");
        Button btnListarPedidosPendientes = new Button("Listar Pedidos Pendientes");
        Button btnAgregarPedido = new Button("Agregar Pedido");
        Button btnEliminarPedido = new Button("Eliminar Pedido");

        btnListarPedidos.setOnAction(e -> listarPedidos(tablaPedidos));
        btnListarPedidosEnviados.setOnAction(e -> listarPedidosEnviados(tablaPedidos));
        btnListarPedidosPendientes.setOnAction(e -> listarPedidosPendientes(tablaPedidos));
        btnAgregarPedido.setOnAction(e -> agregarPedido(tablaPedidos));
        btnEliminarPedido.setOnAction(e -> eliminarPedido(tablaPedidos));

        HBox filaBotones1 = new HBox(10, btnListarPedidos, btnListarPedidosEnviados, btnListarPedidosPendientes);
        filaBotones1.setAlignment(Pos.CENTER);

        HBox filaBotones2 = new HBox(10, btnAgregarPedido, btnEliminarPedido);
        filaBotones2.setAlignment(Pos.CENTER);

        Stream.of(filaBotones1, filaBotones2).flatMap(hbox -> hbox.getChildren().stream()).forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(button, Priority.ALWAYS);
            }
        });

        VBox menuContainer = new VBox(10);
        menuContainer.getChildren().addAll(filaBotones1, filaBotones2, tablaPedidos);

        return menuContainer;
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
        columnaFecha.setPrefWidth(150);

        TableColumn<Pedido, String> columnaEstado = new TableColumn<>("Estado");
        columnaEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstadoPedido().toString()));

        TableColumn<Pedido, String> columnaResumen = new TableColumn<>("Resumen");
        columnaResumen.setCellValueFactory(cellData ->
                new SimpleStringProperty(resumenDetallesPedido(cellData.getValue())));
        columnaResumen.setPrefWidth(180);

        TableColumn<Pedido, String> columnaDetalles = new TableColumn<>("Detalles");
        columnaDetalles.setPrefWidth(200);
        tabla.getColumns().addAll(columnaNumero, columnaCliente, columnaFecha, columnaEstado, columnaResumen, columnaDetalles);

        columnaResumen.setCellFactory(param -> new TableCell<Pedido, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getItem());
            }
        });

        columnaDetalles.setCellFactory(param -> new TableCell<Pedido, String>() {
            private final TableView<DetallePedido> nestedTable = createNestedTable();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Pedido pedido = getTableView().getItems().get(getIndex());
                    nestedTable.getItems().setAll(pedido.getDetallePedidosList());
                    nestedTable.setPrefHeight(calculateNestedTableHeight(pedido.getDetallePedidosList()));
                    setGraphic(nestedTable);
                }
            }
        });
    }

    private TableView<DetallePedido> createNestedTable() {
        TableView<DetallePedido> nestedTable = new TableView<>();

        TableColumn<DetallePedido, String> columnaCodigoArticulo = new TableColumn<>("Artículo");
        columnaCodigoArticulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getCodigo()));
        columnaCodigoArticulo.setPrefWidth(30);

        TableColumn<DetallePedido, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaCantidad.setPrefWidth(10);

        TableColumn<DetallePedido, BigDecimal> columnaPrecioVenta = new TableColumn<>("Precio");
        columnaPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        columnaPrecioVenta.setPrefWidth(30);

        nestedTable.getColumns().addAll(columnaCantidad, columnaPrecioVenta, columnaCodigoArticulo);
        nestedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return nestedTable;
    }

    /* Calcular la altura de la tabla a partir del número de filas */
    private double calculateNestedTableHeight(List<?> items) {
        int rows = items.size();
        double rowHeight = 24;
        double headerHeight = 24;
        return headerHeight + (rows * rowHeight);
    }

    private String detallePedidoInfo(List<DetallePedido> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return "No hay detalles";
        }

        StringBuilder detallesInfo = new StringBuilder();
        for (DetallePedido detalle : detalles) {
            detallesInfo.append(String.format("Cantidad: %d, Precio Venta: %.2f, Código Artículo: %s\n",
                    detalle.getCantidad(), detalle.getPrecioVenta(), detalle.getArticulo().getCodigo()));
        }

        return detallesInfo.toString();
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

    private void listarPedidosEnviados(TableView<Pedido> tabla){
        List<Pedido> pedidos = controladorPedido.listarPedidosEnviados();
        tabla.getItems().setAll(pedidos);
    }

    private void listarPedidosPendientes(TableView<Pedido> tabla){
        List<Pedido> pedidos = controladorPedido.listarPedidosPendientes();
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

        // Set a custom cell factory for the ListView
        listaDetalles.setCellFactory(param -> new ListCell<DetallePedido>() {
            @Override
            protected void updateItem(DetallePedido detallePedido, boolean empty) {
                super.updateItem(detallePedido, empty);

                if (empty || detallePedido == null) {
                    setText(null);
                } else {
                    // Customize the display for each item
                    setText("Código: " + detallePedido.getArticulo().getCodigo() + ", Precio: " + detallePedido.getArticulo().getPrecioVenta() + ", Cantidad: " + detallePedido.getCantidad());
                }
            }
        });

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
                    mostrarError("Error", "Artículo no encontrado o cantidad inválida.");
                }
            } catch (NumberFormatException ex) {
                mostrarError("Error", "Por favor, ingrese una cantidad válida.");
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

    private void eliminarPedido(TableView<Pedido> tabla) {
        Pedido pedidoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {
            controladorPedido.eliminarPedido(pedidoSeleccionado.getNumeroPedido());
            listarPedidos(tabla);
        } else {
            mostrarError("Selección de Pedido Requerida", "Por favor, selecciona un pedido de la lista antes de continuar.");

        }
    }

    private Stage createDialogStage(String title) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        return dialogStage;
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
