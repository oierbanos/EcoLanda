USE ecolanda;
--
-- Dumping data for table HUERTO
--
INSERT INTO huerto (id, dimension, calle, pueblo, codigo_postal) VALUES
(1, 20, 'Fueros', 'Agurain', 01200),
(2, 15, 'Nagusia', 'Opakua', 01201),
(3, 45, 'Maeztu', 'Izki kalea', 01202),
(4, 65, 'Arrasate', 'Loramendi', 012003);
--
-- Dumping data for table tipo_producto
--
INSERT INTO tipo_producto (id, nombre, t_crecimiento, T_ideal, H_ideal) VALUES
(1, 'Tomate', 37, 20, 70),
(2, 'Patata', 45, 18, 85),
(3, 'Lechuga', 44, 16, 76);
--
-- Dumping data for table usuario
--
INSERT INTO usuario (USERNAME, NOMBRE, APELLIDO, CORREO, TELEFONO, PISO, CALLE, PUEBLO, CODIGO_POSTAL, TIPO) values
('jon.na', 'jon', 'navaridas', 'jon.na@gmail.com', '666666666', '3','Fueros', 'Bilbo', '01300', 'cliente'),
('tornike.shengelia', 'tornike', 'shengelia', 'tornike.shengelia@bkn.eus','232323232', '6', 'Salburua','Vitoria-Gasteiz', '01000', 'cliente'),
('mike.james', 'mike', 'james', 'mike.james@bkn.eus','333333333', '3','Nagusia' ,'Argomaniz', '01002', 'cliente'),
('adam.hanga', 'adam', 'hanga', 'adam.hanga@bkn.eus','888888888', '8','Garaia', 'Luko', '01003', 'cliente');
--
-- Dumping data for table tipo_medicion
--
INSERT INTO tipo_medicion (id, nombre, unidad_medicion) VALUES
(1, 'temperatura', 'ºC'),
(2, 'Humedad', '%');
--
-- Dumping data for table medicion
--
INSERT INTO medicion (id, fecha, hora, valor, huerto_id, tipo_id) VALUES
(1, NOW(), NOW(), 22, 1, 1),
(2, NOW(), NOW(), 70, 1, 2);
--
-- Dumping data for table lote_producto
--
INSERT INTO lote_producto (id, fecha_plantar, fecha_recoger, cantidad_plantada, cantidad_recogida, precio_kilo, huerto_id, tipo_id) VALUES
(1, NOW(), '2020-03-28 11:08:57', 5, 500, 4, 1, 1);
--
-- Dumping data for table pedido
--
INSERT INTO pedido (id, username) VALUES
(1, 'tornike.shengelia');
--
-- Dumping data for table hacer_pedido
--
INSERT INTO hacer_pedido (pedido_id, lote_id, tipo_entrega, cantidad) VALUES
(1,1, 'a domicilio', 500);
