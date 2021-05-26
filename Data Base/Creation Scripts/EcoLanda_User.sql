--
-- Administrador del sistema
--
CREATE USER IF NOT EXISTS 'Admin'@'%' IDENTIFIED BY 'adpass69';

--
-- Usuarios: Agricultores
--
CREATE USER IF NOT EXISTS 'adam.hanga'@'127.0.0.1' IDENTIFIED BY 'PassAH21';
CREATE USER IF NOT EXISTS 'jon.na'@'127.0.0.1' IDENTIFIED BY 'PassJN37';
CREATE USER IF NOT EXISTS 'mike.james'@'127.0.0.1' IDENTIFIED BY 'PassMJ23';
CREATE USER IF NOT EXISTS 'tornike.shengelia'@'127.0.0.1' IDENTIFIED BY 'PassTS99';
CREATE USER IF NOT EXISTS 'prueba.agricultor'@'127.0.0.1' IDENTIFIED BY '1234';

--
-- Usuarios: Clientes
--
CREATE USER IF NOT EXISTS 'prueba.cliente'@'127.0.0.1' IDENTIFIED BY '1234';

--
-- Roles de los usuarios
--
CREATE ROLE IF NOT EXISTS 'Admin_Role';
CREATE ROLE IF NOT EXISTS 'Agricultor_Role';
CREATE ROLE IF NOT EXISTS 'Cliente_Role';

--
-- Grant privileges to the different role
--
GRANT ALL ON EcoLanda.* TO 'Admin_Role';

GRANT SELECT ON EcoLanda.* TO 'Agricultor_Role';
GRANT INSERT ON EcoLanda.medicion TO 'Agricultor_Role';
GRANT INSERT ON EcoLanda.lote_producto TO 'Agricultor_Role';

GRANT SELECT ON EcoLanda.* TO 'Cliente_Role';
GRANT INSERT ON EcoLanda.pedido TO 'Cliente_Role';
GRANT INSERT ON EcoLanda.hacer_pedido TO 'Cliente_Role';

--
-- Grant the roles to the users
--
GRANT 'Admin_Role' TO 'Admin'@'%';

GRANT 'Agricultor_Role' TO 'adam.hanga'@'127.0.0.1';
GRANT 'Agricultor_Role' TO 'jon.na'@'127.0.0.1';
GRANT 'Agricultor_Role' TO 'mike.james'@'127.0.0.1';
GRANT 'Agricultor_Role' TO 'tornike.shengelia'@'127.0.0.1';
GRANT 'Agricultor_Role' TO 'prueba.agricultor'@'127.0.0.1';

GRANT 'Cliente_Role' TO 'prueba.cliente'@'127.0.0.1';

-- Prueba
CREATE USER IF NOT EXISTS 'pepe'@'127.0.0.1' IDENTIFIED BY '1234';
GRANT SELECT ON EcoLanda.* TO 'pepe'@'127.0.0.1';
