--
-- Administrador del sistema
--
CREATE USER IF NOT EXISTS 'Admin'@'%' IDENTIFIED BY 'adpass69';

--
-- Usuarios: Agricultores
--
CREATE USER IF NOT EXISTS 'adam.hanga'@'%' IDENTIFIED BY 'PassAH21';
CREATE USER IF NOT EXISTS 'jon.na'@'%' IDENTIFIED BY 'PassJN37';
CREATE USER IF NOT EXISTS 'mike.james'@'%' IDENTIFIED BY 'PassMJ23';
CREATE USER IF NOT EXISTS 'tornike.shengelia'@'%' IDENTIFIED BY 'PassTS99';
CREATE USER IF NOT EXISTS 'prueba.agricultor'@'%' IDENTIFIED BY '1234';

--
-- Usuarios: Clientes
--
CREATE USER IF NOT EXISTS 'prueba.cliente'@'%' IDENTIFIED BY '1234';

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

SET DEFAULT ROLE ALL TO 'jon.na'@'%';

GRANT 'Agricultor_Role' TO 'adam.hanga'@'%';
GRANT 'Agricultor_Role' TO 'jon.na'@'%';
GRANT 'Agricultor_Role' TO 'mike.james'@'%';
GRANT 'Agricultor_Role' TO 'tornike.shengelia'@'%';
GRANT 'Agricultor_Role' TO 'prueba.agricultor'@'%';

SET DEFAULT ROLE ALL TO 'jon.na'@'%';
SET DEFAULT ROLE ALL TO 'adam.hanga'@'%';
SET DEFAULT ROLE ALL TO 'jon.na'@'%';
SET DEFAULT ROLE ALL TO 'mike.james'@'%';
SET DEFAULT ROLE ALL TO 'tornike.shengelia'@'%';
SET DEFAULT ROLE ALL TO 'prueba.agricultor'@'%';

GRANT 'Cliente_Role' TO 'prueba.cliente'@'%';

SET DEFAULT ROLE ALL TO 'prueba.cliente'@'%';

FLUSH PRIVILEGES;
