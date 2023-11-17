README Refactorizacion de NextGen



Punto de situacion del proyecto despues de las modificaciones importantes realizadas sobre la anterior entrega.

Implementado con el patrón Modelo-Vista-Controlador (MVC) de una forma mas estricta despues del feedback de la consultora
separando complentamente la interfaz de usuario y la interacción con logica del programa.

Implementacion del programa siguiendo los patrones DAO y Factory.

Migracion de todo el proyecto a la utilizacion de Maven como gestor de dependecias.



Componentes Clave

    Modelos: Representan las entidades Articulo, Cliente, Pedido, y DetallePedido.
    Vistas: VistaCliente, VistaArticulo, y VistaPedido (pendientes de implementación) serán las interfaces de usuario gestionadas a traves de un menu principal en GestionOs.
    Controladores: La clase Controlador gestiona la lógica del negocio, coordinando operaciones entre modelos y vistas.
    DAOs (Data Access Objects): ArticuloDAO, ClienteDAO, PedidoDAO, y DetallePedidoDAO manejan el acceso a la base de datos.
    Factory : La clase Datos proporciona métodos estáticos para obtener instancias de diferentes DAOs, facilitando la creación y gestión de estos objetos.

Descripciones Detalladas

    ArticuloDAO.java:
        Conexión a la Base de Datos: Uso de Connection para interactuar con la base de datos.
        Operaciones CRUD:
            Listar Artículos: Recupera todos los artículos.
            Agregar Artículo: Inserta nuevos artículos.
            Actualizar Artículo: Modifica artículos existentes.
            Eliminar Artículo: Elimina artículos por código.
        Manejo de Excepciones: Gestión de SQLExceptions.

    ClienteDAO.java:
        Conexión a la Base de Datos: Utiliza Connection.
        Operaciones CRUD:
            Listar Clientes: Obtiene todos los clientes.
            Agregar Cliente: Inserta nuevos clientes.
            Actualizar Cliente: Actualiza clientes existentes.
            Eliminar Cliente: Elimina clientes por NIF.
        Manejo de Excepciones: Manejo adecuado de SQLExceptions.

    PedidoDAO.java y DetallePedidoDAO.java:

        Conexión a la Base de Datos: Uso de Connection.
        Operaciones CRUD:
            PedidoDAO: Gestiona pedidos.
            DetallePedidoDAO: Maneja detalles de pedidos.
        Relación y Coordinación: Trabajan juntos para manejar pedidos completos.
        Manejo de Excepciones: Gestión efectiva de SQLExceptions.

        PedidoDAO.java

    Conexión a la Base de Datos:
        Usa una conexión (Connection) pasada en el constructor para interactuar con la base de datos.

    Operaciones CRUD:
        Listar Pedidos: Recupera todos los pedidos o pedidos filtrados (por ejemplo, pedidos pendientes o enviados).
        Agregar Pedido: Inserta un nuevo pedido en la base de datos.
        Actualizar Pedido: Modifica un pedido existente.
        Eliminar Pedido: Elimina un pedido por su número.

    Manejo de Excepciones:
        Maneja adecuadamente las SQLExceptions.

DetallePedidoDAO.java

    Conexión a la Base de Datos:
        Similar a PedidoDAO, utiliza una conexión a la base de datos para sus operaciones.

    Operaciones CRUD para Detalles de Pedido:
        Listar Detalles por Pedido: Obtiene todos los detalles asociados a un número de pedido específico.
        Agregar Detalle: Inserta un nuevo detalle de pedido.
        Eliminar Detalle: Elimina un detalle de pedido, identificado por el número de pedido y el código del artículo.

    Relación con PedidoDAO:
        DetallePedidoDAO complementa a PedidoDAO en la gestión de pedidos. Mientras PedidoDAO maneja los pedidos en sí,
        DetallePedidoDAO se ocupa de los detalles de cada pedido.

Métodos Clave en PedidoDAO que Utilizan DetallePedidoDAO

    Método insertar(Pedido pedido):
        Tras insertar un nuevo pedido en la base de datos, este método itera sobre los DetallePedido incluidos en el pedido.
        Para cada DetallePedido, llama a detallePedidoDAO.agregarDetalle(detalle), lo que asegura que cada detalle del pedido se inserte en la base de datos.
        Este es un ejemplo claro de cómo PedidoDAO y DetallePedidoDAO trabajan juntos para manejar un pedido completo.

    Método crearPedidoDesdeResultSet(ResultSet rs):
        Este método auxiliar se utiliza para construir un objeto Pedido a partir de un ResultSet.
        Importante aquí es la llamada a detallePedidoDAO.listarPorPedido(numeroPedido), que recupera todos los detalles asociados a un pedido específico.
        Esto significa que cuando se obtiene un pedido de la base de datos, se recuperan también todos sus detalles correspondientes, integrando así la información completa del pedido.



Observaciones y Recomendaciones

    Transacciones: Idealmente, las operaciones que involucran pedidos y sus detalles deberían ser atómicas, es decir, si una parte de la operación falla (como la inserción de un detalle),
    toda la operación debería revertirse. Esto se logra mediante el uso de transacciones en la base de datos.

    Eliminación de Pedidos: En el método eliminar, no se observa una eliminación explícita de los detalles del pedido.
    Sería recomendable agregar lógica para manejar también la eliminación de DetallePedido asociados al pedido que se está eliminando, para evitar la presencia de datos huérfanos.
