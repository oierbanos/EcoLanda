DROP DATABASE IF EXISTS EcoLanda;
CREATE DATABASE IF NOT EXISTS EcoLanda;
USE EcoLanda;

CREATE TABLE IF NOT EXISTS huerto (
    id              INT                 NOT NULL,
    dimension       INT                 NOT NULL,
    eco             VARBINARY(100),
    calle           VARCHAR(50)         NOT NULL,
    pueblo          VARCHAR(25)         NOT NULL,
    codigo_postal   VARCHAR(5)          NOT NULL
);

CREATE TABLE IF NOT EXISTS tipo_medicion (
    id                  INT             NOT NULL,
    nombre              VARCHAR(30)     NOT NULL,
    unidad_medicion     VARCHAR(5)      NOT NULL
);

CREATE TABLE IF NOT EXISTS tipo_producto (
    id              INT             NOT NULL,
    nombre          VARCHAR(50)     NOT NULL,
    t_crecimiento   INT             NOT NULL,
    T_ideal         INT             NOT NULL,
    H_ideal         INT             NOT NULL
);

CREATE TABLE IF NOT EXISTS medicion (
    id              INT             NOT NULL,
    fecha           DATE            NOT NULL,
    hora            TIME            NOT NULL,
    valor           INT             NOT NULL,
    huerto_id       INT             NOT NULL,
    tipo_id         INT             NOT NULL
);

CREATE TABLE IF NOT EXISTS lote_producto (
    id                  INT         NOT NULL,
    fecha_plantar       DATE        NOT NULL,
    fecha_recoger       DATE,
    cantidad_plantada   INT         NOT NULL,
    cantidad_recogida   INT,
    precio_kilo         INT         NOT NULL,
    huerto_id           INT         NOT NULL,
    tipo_id             INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    username            VARCHAR(30)         NOT NULL,
    nombre              VARCHAR(25)         NOT NULL,
    apellido            VARCHAR(50)         NOT NULL,
    correo              VARCHAR(320)        NOT NULL,
    telefono            VARCHAR(9)          NOT NULL,
    piso                VARCHAR(5),
    calle               VARCHAR(50)         NOT NULL,
    pueblo              VARCHAR(25)         NOT NULL,
    codigo_postal       VARCHAR(5)          NOT NULL,
    tipo                VARCHAR(10)         NOT NULL,
    huerto_id           INT
);

CREATE TABLE IF NOT EXISTS pedido (
    id          INT             NOT NULL,
    username    VARCHAR(30)     NOT NULL
);

CREATE TABLE IF NOT EXISTS hacer_pedido (
    pedido_id       INT         NOT NULL,
    lote_id         INT         NOT NULL,
    tipo_entrega    VARCHAR(25) NOT NULL,
    cantidad        INT         NOT NULL
);

ALTER TABLE huerto
    ADD CONSTRAINT PK_huerto PRIMARY KEY (id);

ALTER TABLE tipo_medicion
    ADD CONSTRAINT PK_tipo_medicion PRIMARY KEY (id);

ALTER TABLE medicion
    ADD CONSTRAINT PK_medicion PRIMARY KEY (id),
    ADD CONSTRAINT medicion_FK_huerto FOREIGN KEY (huerto_id) REFERENCES huerto (id),
    ADD CONSTRAINT medicion_FK_tipo FOREIGN KEY (tipo_id) REFERENCES tipo_medicion (id);

ALTER TABLE tipo_producto
    ADD CONSTRAINT PK_tipo_medicion PRIMARY KEY (id);

ALTER TABLE lote_producto
    ADD CONSTRAINT PK_lote PRIMARY KEY (id),
    ADD CONSTRAINT lote_FK_huerto FOREIGN KEY (huerto_id) REFERENCES huerto (id),
    ADD CONSTRAINT lote_FK_tipo FOREIGN KEY (tipo_id) REFERENCES tipo_producto (id);

ALTER TABLE usuario
    ADD CONSTRAINT PF_usuario PRIMARY KEY (username),
    ADD CONSTRAINT usuario_FK_huerto FOREIGN KEY (huerto_id) REFERENCES huerto (id);

ALTER TABLE pedido
    ADD CONSTRAINT PF_pedido PRIMARY KEY (id),
    ADD CONSTRAINT pedido_FK_username FOREIGN KEY (username) REFERENCES usuario (username);

ALTER TABLE hacer_pedido
    ADD CONSTRAINT PK_hacer_pedido PRIMARY KEY (pedido_id, lote_id),
    ADD CONSTRAINT hacer_pedido_FK_pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id),
    ADD CONSTRAINT hacer_pedido_FK_lote FOREIGN KEY (lote_id) REFERENCES lote_producto (id);
