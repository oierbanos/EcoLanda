USE EcoLanda;

/*PROCEDURE para calcular la media del precio de venta de un producto*/
delimiter //

CREATE PROCEDURE mediaProducto (IN producto VARCHAR(50), OUT media FLOAT)
       BEGIN
         SELECT AVG(IFNULL(lp.precio_kilo,0)) AS 'Media'
         FROM lote_producto lp
            JOIN tipo_producto tp on tp.id = lp.tipo_id
         WHERE tp.nombre = producto
;
       END//

delimiter ;

/*
CALL mediaProducto('Tomate', @media); -- media de Tomate


SELECT @media;/*
+---------+
| @media |
+---------+
|  0.333 |
+---------+*/

/* VIEW USUARIOS*/
CREATE VIEW usuarios_vista AS SELECT nombre, apellido FROM usuario;
/*VIEW HUERTOS*/
CREATE VIEW huertos_vista AS SELECT id,eco FROM huerto;