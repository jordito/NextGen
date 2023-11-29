CREATE DATABASE tienda;
USE tienda;

-- Tabla Principal Clientes
CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    domicilio VARCHAR(255) NOT NULL,
    NIF VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla ClientesEstandard (Herencia de Clientes)
CREATE TABLE ClientesEstandard (
    id_cliente INT PRIMARY KEY,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

-- Tabla ClientesPremium (Herencia de Clientes)
CREATE TABLE ClientesPremium (
    id_cliente INT PRIMARY KEY,
    cuota_anual DECIMAL(10, 2) NOT NULL,
    descuento_envio DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

-- Tabla Articulos
CREATE TABLE Articulos (
    codigo VARCHAR(50) PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    precio_venta DECIMAL(10, 2) NOT NULL,
    gastos_envio DECIMAL(10, 2) NOT NULL,
    tiempo_preparacion INT NOT NULL
);

-- Tabla Pedidos
CREATE TABLE Pedidos (
    numero_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha_hora_pedido DATETIME NOT NULL,
    estado_pedido ENUM('Pendiente', 'Enviado') NOT NULL DEFAULT 'Pendiente',
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

-- Tabla DetallePedido para manejar relación muchos a muchos entre Pedidos y Articulos
CREATE TABLE DetallePedido (
    numero_pedido INT,
    codigo_articulo VARCHAR(50),
    cantidad INT CHECK (cantidad > 0),
    precio_venta DECIMAL(10, 2),
    PRIMARY KEY (numero_pedido, codigo_articulo),
    FOREIGN KEY (numero_pedido) REFERENCES Pedidos(numero_pedido),
    FOREIGN KEY (codigo_articulo) REFERENCES Articulos(codigo)
);

-- Trigger para actualizar estado del pedido
DELIMITER //
CREATE TRIGGER actualizar_estado_pedido
BEFORE UPDATE ON Pedidos
FOR EACH ROW
BEGIN
    IF NEW.estado_pedido = 'Enviado' THEN
        SET NEW.fecha_hora_pedido = CURRENT_TIMESTAMP;
    END IF;
END;
//
DELIMITER ;

-- Trigger para evitar que se actualice la fecha y hora del pedido una vez enviado
DELIMITER //
CREATE TRIGGER evitar_actualizacion_pedido_enviado
BEFORE UPDATE ON Pedidos
FOR EACH ROW
BEGIN
    IF OLD.estado_pedido = 'Enviado' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede modificar un pedido ya enviado';
    END IF;
END;
//
DELIMITER ;

ALTER TABLE DetallePedido ADD CONSTRAINT uk_detallepedido UNIQUE (numero_pedido, codigo_articulo);
