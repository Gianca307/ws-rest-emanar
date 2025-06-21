INSERT INTO categoria_producto (categoria_producto)
VALUES
('GASEOSA'),
('CERVEZA'),
('VINO'),
('VODKA');

INSERT INTO cliente (direccion, nombre, numero_de_celular)
VALUES
('calle 1234', 'Carlos Alberto Furnes', '2222222222'),
('calle 147', 'Franco Segovia', '3333333333'),
('calle 5', 'Ana del Valle', '4444444444');

INSERT INTO producto (activo, costo, disponible, precio, stock, categoria_producto_id, ean, capacidad, img_url, marca, variedad)
VALUES 
(1, 1235.1, 1, 1800, 24, 2, 1234567891012, '473 cm3', 'imagen_url', 'quilmes', 'stout'),
(1, 1235.1, 1, 1800, 24, 2, 5432167891012, '473 cm3', 'imagen_url', 'quilmes', 'rubia'),
(1, 2005.91, 1, 2300, 12, 1, 9876543211012, '2 1/2 lts', 'imagen_url', 'coca cola', 'sabor original'),
(1, 3045.24, 1, 4500, 6, 3, 2101987654321, '750 cm3', 'imagen_url', 'Talacasto', 'tinto'),
(1, 7075.21, 1, 8900, 2, 4, 1111111111111, '750 cm3', 'imagen_url', 'Smirnoff', 'lemon grass'),
(1, 7075.21, 1, 8900, 24, 4, 2222222222222, '750 cm3', 'imagen_url', 'Smirnoff', 'watermelon');

INSERT INTO proveedor (dia_de_entrega, dia_de_visita, empresa, nombre_del_contacto, numero_de_cliente, numero_de_contacto, rol_del_contacto)
VALUES
('viernes', 'miercoles', 'Joel', 'Gabriel', '784596', '1234567891', 'promotor'),
('viernes', 'miercoles', 'Coca cola', 'Alexis', '198523', '9874563210', 'preventista');

INSERT INTO venta (fecha_de_venta, total, cliente_id)
VALUES
('2025-04-19', 18000, 1),
('2025-05-22', 23000, 2);

INSERT INTO gasto (costo_total, fecha_de_compra, proveedor_id, forma_de_pago)
VALUES
(12351, '2025-01-11', 1, 'EFECTIVO'),
(12351, '2025-02-10', 1, 'TARJETA_CREDITO'),
(70752.1, '2025-03-14', 1, 'EFECTIVO');

INSERT INTO producto_comprado (cantidad, precio_establecido, gasto_id, producto_id)
VALUES
(10, 1235.1, 1, 1),
(10, 1235.1, 2, 2),
(10, 7075.21, 3, 5);

INSERT INTO producto_vendido (cantidad, precio_establecido, producto_id, venta_id)
VALUES
(5, 1800, 1, 1),
(5, 1800, 2, 1),
(10, 2300, 3, 2);