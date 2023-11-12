-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: onlinestore
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articulos`
--

DROP TABLE IF EXISTS `articulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulos` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `precioVenta` double NOT NULL,
  `gastosEnvio` double NOT NULL,
  `tiempoPreparacion` int NOT NULL,
  `idOS` int NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `idOnlineStore_idx` (`idOS`),
  CONSTRAINT `idOS` FOREIGN KEY (`idOS`) REFERENCES `onlinestore` (`idOnlineStore`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulos`
--

LOCK TABLES `articulos` WRITE;
/*!40000 ALTER TABLE `articulos` DISABLE KEYS */;
/*!40000 ALTER TABLE `articulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `email` varchar(50) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `tipoCliente` enum('Premium','Estandar') NOT NULL,
  `idOnlineStore` int NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  KEY `idOnlineStore_idx` (`idOnlineStore`),
  CONSTRAINT `idOnlineStore` FOREIGN KEY (`idOnlineStore`) REFERENCES `onlinestore` (`idOnlineStore`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientesestandar`
--

DROP TABLE IF EXISTS `clientesestandar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientesestandar` (
  `emailClienteEstandar` varchar(50) NOT NULL,
  `osId` int NOT NULL,
  PRIMARY KEY (`emailClienteEstandar`),
  KEY `storeId_idx` (`osId`),
  CONSTRAINT `emailClienteEstandar` FOREIGN KEY (`emailClienteEstandar`) REFERENCES `clientes` (`email`),
  CONSTRAINT `osId` FOREIGN KEY (`osId`) REFERENCES `onlinestore` (`idOnlineStore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientesestandar`
--

LOCK TABLES `clientesestandar` WRITE;
/*!40000 ALTER TABLE `clientesestandar` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientesestandar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientespremium`
--

DROP TABLE IF EXISTS `clientespremium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientespremium` (
  `emailClientesPremium` varchar(50) NOT NULL,
  `cuotaAnual` decimal(8,2) NOT NULL,
  `descuentoEnvio` decimal(5,2) NOT NULL,
  `onlineStoreId` int NOT NULL,
  PRIMARY KEY (`emailClientesPremium`),
  KEY `onlineStoreId_idx` (`onlineStoreId`),
  CONSTRAINT `emailClientes` FOREIGN KEY (`emailClientesPremium`) REFERENCES `clientes` (`email`),
  CONSTRAINT `onlineStoreId` FOREIGN KEY (`onlineStoreId`) REFERENCES `onlinestore` (`idOnlineStore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientespremium`
--

LOCK TABLES `clientespremium` WRITE;
/*!40000 ALTER TABLE `clientespremium` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientespremium` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `onlinestore`
--

DROP TABLE IF EXISTS `onlinestore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `onlinestore` (
  `idOnlineStore` int NOT NULL,
  `Nombre` varchar(45) NOT NULL,
  `Direccion` varchar(100) NOT NULL,
  PRIMARY KEY (`idOnlineStore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `onlinestore`
--

LOCK TABLES `onlinestore` WRITE;
/*!40000 ALTER TABLE `onlinestore` DISABLE KEYS */;
/*!40000 ALTER TABLE `onlinestore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `numero_pedido` int NOT NULL AUTO_INCREMENT,
  `cliente_email` varchar(50) NOT NULL,
  `articulo_codigo` int NOT NULL,
  `cantidad` int NOT NULL,
  `fecha_hora_pedido` datetime NOT NULL,
  `store_id` int NOT NULL,
  PRIMARY KEY (`numero_pedido`),
  KEY `cliente_email_idx` (`cliente_email`),
  KEY `articulo_codigo_idx` (`articulo_codigo`),
  KEY `store_id_idx` (`store_id`),
  CONSTRAINT `articuloCodigo` FOREIGN KEY (`articulo_codigo`) REFERENCES `articulos` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `clienteEmail` FOREIGN KEY (`cliente_email`) REFERENCES `clientes` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `storeId` FOREIGN KEY (`store_id`) REFERENCES `onlinestore` (`idOnlineStore`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-11 18:45:32
