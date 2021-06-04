USE EcoLanda;

START TRANSACTION;

--
-- Dumping data for table HUERTO
--
INSERT INTO huerto (id, dimension, calle, pueblo, codigo_postal) VALUES
(1, 20, 'Fueros', 'Agurain', 01200),
(2, 15, 'Nagusia', 'Opakua', 01201),
(3, 45, 'Maeztu', 'Izki kalea', 01202),
(4, 65, 'Arrasate', 'Loramendi', 012003);
--
SAVEPOINT huerto;
--
-- Dumping data for table tipo_producto
--
INSERT INTO tipo_producto (id, nombre, t_crecimiento, T_ideal, H_ideal) VALUES
(1, 'Tomate', 60, 30, 70),
(2, 'Patata', 95, 23, 90),
(3, 'Lechuga', 45, 18, 70),
(4, 'Cebollas', 120, 23, 75),
(5, 'Zanahorias',55, 20, 98),
(6, 'Guisantes',105, 18, 92),
(7, 'Alubias', 60, 25, 65)
;
--
SAVEPOINT tipo_producto;
--
-- Dumping data for table usuario
--
INSERT INTO usuario (USERNAME, NOMBRE, APELLIDO, CORREO, TELEFONO, PISO, CALLE, PUEBLO, CODIGO_POSTAL, TIPO, HUERTO_ID) values
('jon.na', 'jon', 'navaridas', 'jon.na@gmail.com', '666666666', '3','Fueros', 'Bilbo', '01300', 'cliente', 1),
('tornike.shengelia', 'tornike', 'shengelia', 'tornike.shengelia@bkn.eus','232323232', '6', 'Salburua','Vitoria-Gasteiz', '01000', 'cliente', 2),
('mike.james', 'mike', 'james', 'mike.james@bkn.eus','333333333', '3','Nagusia' ,'Argomaniz', '01002', 'cliente', 3),
('adam.hanga', 'adam', 'hanga', 'adam.hanga@bkn.eus','888888888', '8','Garaia', 'Luko', '01003', 'cliente', 4);
--
SAVEPOINT usuario;
--
-- Dumping data for table tipo_medicion
--
INSERT INTO tipo_medicion (id, nombre, unidad_medicion) VALUES
(1, 'Temperatura', 'ºC'),
(2, 'Humedad', '%');
--
SAVEPOINT tipo_medicion;
--
-- Dumping data for table medicion
--
INSERT INTO medicion (id, fecha, hora, valor, huerto_id, tipo_id) VALUES
(1, NOW(), NOW(), 22, 1, 1),
(2, NOW(), NOW(), 70, 1, 2);
--
SAVEPOINT medicion;
--
-- Dumping data for table lote_producto
--
INSERT INTO lote_producto (id, fecha_plantar, fecha_recoger, cantidad_plantada, cantidad_recogida, precio_kilo, huerto_id, tipo_id) VALUES
(1, NOW(), '2020-03-28 11:08:57', 5, 500, 4, 1, 1);
--
SAVEPOINT lote_producto;
--
-- Dumping data for table pedido
--
INSERT INTO pedido (id, username) VALUES
(1, 'tornike.shengelia');
--
SAVEPOINT pedido;
--
-- Dumping data for table hacer_pedido
--
INSERT INTO hacer_pedido (pedido_id, lote_id, tipo_entrega, cantidad) VALUES
(1,1, 'a domicilio', 500);
--
SAVEPOINT hacer_pedido;
--
-- Dumping data for agricultor jon.na
--
INSERT INTO lote_producto (id, fecha_plantar, fecha_recoger, cantidad_plantada, cantidad_recogida, precio_kilo, huerto_id, tipo_id)
VALUES (2, '2021-05-22 11:08:57', CURDATE(), '30', '500', null, 1, 1),
       (3, '2021-05-12 15:08:57', CURDATE(), '21', '377', null, 1, 3),
       (4, '2021-03-20 11:08:57', CURDATE(), '6.5', '58', null, 1, 5),
       (5, '2021-05-9 10:00:00', CURDATE(), '45', '125', null, 1, 2),
       (6, '2020-05-30 15:08:32', CURDATE(), '66', '432', null, 1, 3),
       (7, '2020-12-23 11:08:57', CURDATE(), '52.7', '670', null, 1, 4),
       (8, '2019-07-2 8:08:57', CURDATE(), '21.2', '223', null, 1, 5),
       (9, '2019-10-13 11:08:57', CURDATE(), '22', '99', null, 1, 7),
       (10, '2020-05-22 11:08:57', CURDATE(), '35', '50', null, 1, 5),
       (11, '2021-11-11 11:08:57', CURDATE(), '21', '678', null, 1, 6),
       (12, '2021-09-7 11:08:57', CURDATE(), '43', '81', null, 1, 4);
COMMIT ;