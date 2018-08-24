CREATE DATABASE crudjava;
USE crudjava;

CREATE TABLE Producto(
	id_producto INT AUTO_INCREMENT,
	nombre VARCHAR(100) NOT NULL,
	precio int(10) NOT NULL,
	cantidad int(10) NOT NULL,
	preveedor VARCHAR(100),
	PRIMARY KEY(id_producto)
);
