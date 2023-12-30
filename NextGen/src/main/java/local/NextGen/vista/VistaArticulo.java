package local.NextGen.vista;

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
import local.NextGen.controlador.ControladorArticulo;
import local.NextGen.modelo.entidades.Articulo;

import java.math.BigDecimal;
import java.util.List;

public class VistaArticulo {

    private ControladorArticulo controladorArticulo;

    public VistaArticulo(ControladorArticulo controlador) {
        this.controladorArticulo = controlador;
    }

    public Node getVistaArticuloNode() {
        TableView<Articulo> tablaArticulos = new TableView<>();
        configurarColumnasTabla(tablaArticulos);

        Button btnListar = new Button("Listar Artículos");
        Button btnAgregar = new Button("Agregar Artículo");
        Button btnActualizar = new Button("Actualizar Artículo");
        Button btnEliminar = new Button("Eliminar Artículo");

        btnListar.setOnAction(e -> listarArticulos(tablaArticulos));
        btnAgregar.setOnAction(e -> agregarNuevoArticulo(tablaArticulos));
        btnActualizar.setOnAction(e -> actualizarArticulo(tablaArticulos));
        btnEliminar.setOnAction(e -> eliminarArticulo(tablaArticulos));

        HBox botonesHBox = new HBox(10);
        botonesHBox.setAlignment(Pos.CENTER);
        botonesHBox.getChildren().addAll(btnListar, btnAgregar, btnActualizar, btnEliminar);

        // Asegurarse de que los botones se expandan uniformemente
        botonesHBox.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(button, Priority.ALWAYS);
            }
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(botonesHBox, tablaArticulos);

        return layout;
    }

    private void configurarColumnasTabla(TableView<Articulo> tabla) {
        TableColumn<Articulo, String> columnaCodigo = new TableColumn<>("Código");
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnaCodigo.setMinWidth(6);

        TableColumn<Articulo, String> columnaDescripcion = new TableColumn<>("Descripción");
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnaDescripcion.setMinWidth(20);

        TableColumn<Articulo, BigDecimal> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        columnaPrecio.setMinWidth(6);

        TableColumn<Articulo, BigDecimal> columnaGastosEnvio = new TableColumn<>("Gastos de Envío");
        columnaGastosEnvio.setCellValueFactory(new PropertyValueFactory<>("gastosEnvio"));
        columnaGastosEnvio.setMinWidth(15);

        TableColumn<Articulo, Integer> columnaTiempoPreparacion = new TableColumn<>("Tiempo de Preparación");
        columnaTiempoPreparacion.setCellValueFactory(new PropertyValueFactory<>("tiempoPreparacion"));
        columnaTiempoPreparacion.setMinWidth(22);

        tabla.getColumns().addAll(columnaCodigo, columnaDescripcion, columnaPrecio, columnaGastosEnvio, columnaTiempoPreparacion);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void listarArticulos(TableView<Articulo> tabla) {
        List<Articulo> articulos = controladorArticulo.listarArticulos();
        tabla.getItems().setAll(articulos);
    }

    private void agregarNuevoArticulo(TableView<Articulo> tabla) {
        Stage dialogStage = createDialogStage("Agregar Nuevo Artículo");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código");
        TextField txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción");
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        TextField txtGastosEnvio = new TextField();
        txtGastosEnvio.setPromptText("Gastos de Envío");
        TextField txtTiempoPreparacion = new TextField();
        txtTiempoPreparacion.setPromptText("Tiempo de Preparación");

        Button btnConfirmar = new Button("Agregar");
        btnConfirmar.setOnAction(e -> {
            try {
                Articulo nuevoArticulo = new Articulo(
                        txtCodigo.getText(),
                        txtDescripcion.getText(),
                        new BigDecimal(txtPrecio.getText()),
                        new BigDecimal(txtGastosEnvio.getText()),
                        Integer.parseInt(txtTiempoPreparacion.getText())
                );
                controladorArticulo.agregarArticulo(nuevoArticulo);
                listarArticulos(tabla);
                dialogStage.close();
            } catch (NumberFormatException ex) {
                // Manejar error de formato de números
            }
        });

        grid.add(new Label("Código:"), 0, 0);
        grid.add(txtCodigo, 1, 0);
        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(txtDescripcion, 1, 1);
        grid.add(new Label("Precio:"), 0, 2);
        grid.add(txtPrecio, 1, 2);
        grid.add(new Label("Gastos de Envío:"), 0, 3);
        grid.add(txtGastosEnvio, 1, 3);
        grid.add(new Label("Tiempo de Preparación:"), 0, 4);
        grid.add(txtTiempoPreparacion, 1, 4);
        grid.add(btnConfirmar, 1, 5);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
    private void actualizarArticulo(TableView<Articulo> tabla) {
        Articulo articuloSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (articuloSeleccionado == null) {
            // Mostrar mensaje de error o indicación para seleccionar un artículo
            return;
        }

        Stage dialogStage = createDialogStage("Actualizar Artículo");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField txtDescripcion = new TextField(articuloSeleccionado.getDescripcion());
        TextField txtPrecio = new TextField(articuloSeleccionado.getPrecioVenta().toString());
        TextField txtGastosEnvio = new TextField(articuloSeleccionado.getGastosEnvio().toString());
        TextField txtTiempoPreparacion = new TextField(String.valueOf(articuloSeleccionado.getTiempoPreparacion()));

        Button btnConfirmar = new Button("Actualizar");
        btnConfirmar.setOnAction(e -> {
            try {
                articuloSeleccionado.setDescripcion(txtDescripcion.getText());
                articuloSeleccionado.setPrecioVenta(new BigDecimal(txtPrecio.getText()));
                articuloSeleccionado.setGastosEnvio(new BigDecimal(txtGastosEnvio.getText()));
                articuloSeleccionado.setTiempoPreparacion(Integer.parseInt(txtTiempoPreparacion.getText()));

                controladorArticulo.actualizarArticulo(articuloSeleccionado);
                listarArticulos(tabla);
                dialogStage.close();
            } catch (NumberFormatException ex) {
                // Manejar error de formato de números
            }
        });

        grid.add(new Label("Descripción:"), 0, 0);
        grid.add(txtDescripcion, 1, 0);
        grid.add(new Label("Precio:"), 0, 1);
        grid.add(txtPrecio, 1, 1);
        grid.add(new Label("Gastos de Envío:"), 0, 2);
        grid.add(txtGastosEnvio, 1, 2);
        grid.add(new Label("Tiempo de Preparación:"), 0, 3);
        grid.add(txtTiempoPreparacion, 1, 3);
        grid.add(btnConfirmar, 1, 4);

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void eliminarArticulo(TableView<Articulo> tabla) {
        Articulo articuloSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (articuloSeleccionado == null) {
            // Mostrar mensaje de error o indicación para seleccionar un artículo
            return;
        }

        Stage dialogStage = createDialogStage("Eliminar Artículo");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        Label lblConfirmacion = new Label("¿Está seguro de que desea eliminar el artículo seleccionado?");
        Button btnConfirmar = new Button("Eliminar");
        btnConfirmar.setOnAction(e -> {
            controladorArticulo.eliminarArticulo(articuloSeleccionado.getCodigo());
            listarArticulos(tabla);
            dialogStage.close();
        });

        grid.add(lblConfirmacion, 0, 0);
        grid.add(btnConfirmar, 0, 1);

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
}


