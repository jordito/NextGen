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
import java.util.stream.Stream;

/**
 * Clase que representa la vista de gestión de artículos en la interfaz de usuario.
 * Proporciona métodos para listar, agregar, actualizar y eliminar artículos.
 */
public class VistaArticulo {

    private ControladorArticulo controladorArticulo;

    /**
     * Constructor para crear una instancia de VistaArticulo.
     *
     * @param controlador Controlador asociado que maneja la lógica de negocio.
     */
    public VistaArticulo(ControladorArticulo controlador) {
        this.controladorArticulo = controlador;
    }

    /**
     * Crea y devuelve el nodo de la interfaz de usuario para la gestión de artículos.
     * Incluye botones para operaciones como listar, agregar, actualizar y eliminar artículos,
     * y una tabla para mostrar los artículos.
     *
     * @return Node que representa la interfaz de usuario para la gestión de artículos.
     */
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

        HBox filaBotones1 = new HBox(10, btnListar, btnAgregar);
        filaBotones1.setAlignment(Pos.CENTER);

        HBox filaBotones2 = new HBox(10, btnActualizar, btnEliminar);
        filaBotones2.setAlignment(Pos.CENTER);

        Stream.of(filaBotones1, filaBotones2).flatMap(hbox -> hbox.getChildren().stream()).forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(button, Priority.ALWAYS);
            }
        });

        VBox menuContainer = new VBox(10);
        menuContainer.getChildren().addAll(filaBotones1, filaBotones2, tablaArticulos);

        return menuContainer;
    }

    /**
     * Configura las columnas de la tabla de artículos.
     * Define cómo se deben mostrar los datos de los artículos en la tabla.
     *
     * @param tabla Tabla en la que se configurarán las columnas.
     */
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

    /**
     * Lista todos los artículos en la tabla.
     *
     * @param tabla Tabla en la que se listarán los artículos.
     */
    private void listarArticulos(TableView<Articulo> tabla) {
        List<Articulo> articulos = controladorArticulo.listarArticulos();
        tabla.getItems().setAll(articulos);
    }

    /**
     * Presenta una interfaz de usuario para agregar un nuevo artículo.
     * Incluye campos para ingresar detalles del artículo y un botón para confirmar la adición.
     *
     * @param tabla Tabla para reflejar los cambios una vez que se agrega un artículo.
     */
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
                mostrarError("Error de Formato", "Por favor, ingrese números válidos para precio y gastos de envío.");
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
    /**
     * Presenta una interfaz de usuario para actualizar un artículo existente.
     * Permite modificar los detalles del artículo seleccionado.
     *
     * @param tabla Tabla donde se selecciona el artículo a actualizar.
     */
    private void actualizarArticulo(TableView<Articulo> tabla) {
        Articulo articuloSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (articuloSeleccionado == null) {
            mostrarError("Selección de Artículo Requerida", "Por favor, selecciona un artículo de la lista antes de continuar.");
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
                mostrarError("Error de Formato", "Por favor, ingrese números válidos para precio y gastos de envío.");
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
    /**
     * Presenta una confirmación para eliminar un artículo seleccionado.
     * Elimina el artículo de la tabla y de la base de datos si se confirma.
     *
     * @param tabla Tabla de donde se selecciona el artículo a eliminar.
     */
    private void eliminarArticulo(TableView<Articulo> tabla) {
        Articulo articuloSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (articuloSeleccionado == null) {
            mostrarError("Selección de Artículo Requerida", "Por favor, selecciona un artículo de la lista antes de continuar.");
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


