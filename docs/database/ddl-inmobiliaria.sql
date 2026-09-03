-- ============================================================
-- Script DDL — Sistema Web de Inmobiliaria
-- Programación Java — UTS
-- Base de datos: inmobiliaria_db (PostgreSQL)
-- ============================================================

-- ===== BLOQUE A: Autenticación y roles =====

CREATE TABLE usuario (
    id_usuario      SERIAL PRIMARY KEY,
    correo          VARCHAR(150) NOT NULL UNIQUE,
    contrasena_hash VARCHAR(255) NOT NULL,
    activo          BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE perfil (
    id_perfil    SERIAL PRIMARY KEY,
    id_usuario   INTEGER NOT NULL UNIQUE,
    nombres      VARCHAR(100) NOT NULL,
    apellidos    VARCHAR(100) NOT NULL,
    documento    VARCHAR(30) NOT NULL,
    telefono     VARCHAR(20),
    direccion    VARCHAR(200),
    foto_url     VARCHAR(255),
    CONSTRAINT fk_perfil_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE inmobiliaria (
    id_inmobiliaria     SERIAL PRIMARY KEY,
    id_usuario          INTEGER NOT NULL UNIQUE,
    nombre_comercial    VARCHAR(150) NOT NULL,
    nit                 VARCHAR(30),
    telefono_contacto   VARCHAR(20),
    CONSTRAINT fk_inmobiliaria_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE rol (
    id_rol      SERIAL PRIMARY KEY,
    nombre_rol  VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE usuario_rol (
    id_usuario        INTEGER NOT NULL,
    id_rol            INTEGER NOT NULL,
    fecha_asignacion  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_usuario, id_rol),
    CONSTRAINT fk_usuario_rol_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_usuario_rol_rol
        FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ===== BLOQUE B: Catálogo e inmobiliaria =====

CREATE TABLE ciudad (
    id_ciudad      SERIAL PRIMARY KEY,
    nombre_ciudad  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tipo_propiedad (
    id_tipo_propiedad  SERIAL PRIMARY KEY,
    nombre_tipo        VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE propiedad (
    id_propiedad             SERIAL PRIMARY KEY,
    id_inmobiliaria          INTEGER NOT NULL,
    id_ciudad                INTEGER NOT NULL,
    id_tipo_propiedad        INTEGER NOT NULL,
    matricula_inmobiliaria   VARCHAR(50) NOT NULL UNIQUE,
    titulo                   VARCHAR(150) NOT NULL,
    descripcion              TEXT,
    direccion                VARCHAR(200) NOT NULL,
    precio                   NUMERIC(14,2) NOT NULL,
    area_m2                  NUMERIC(8,2),
    estado                   VARCHAR(20) NOT NULL DEFAULT 'disponible',
    destacada                BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_publicacion        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_propiedad_inmobiliaria
        FOREIGN KEY (id_inmobiliaria) REFERENCES inmobiliaria (id_inmobiliaria)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_propiedad_ciudad
        FOREIGN KEY (id_ciudad) REFERENCES ciudad (id_ciudad)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_propiedad_tipo
        FOREIGN KEY (id_tipo_propiedad) REFERENCES tipo_propiedad (id_tipo_propiedad)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE imagen_propiedad (
    id_imagen     SERIAL PRIMARY KEY,
    id_propiedad  INTEGER NOT NULL,
    url_imagen    VARCHAR(255) NOT NULL,
    orden         INTEGER DEFAULT 0,
    CONSTRAINT fk_imagen_propiedad
        FOREIGN KEY (id_propiedad) REFERENCES propiedad (id_propiedad)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE caracteristica (
    id_caracteristica       SERIAL PRIMARY KEY,
    nombre_caracteristica   VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE propiedad_caracteristica (
    id_propiedad       INTEGER NOT NULL,
    id_caracteristica  INTEGER NOT NULL,
    PRIMARY KEY (id_propiedad, id_caracteristica),
    CONSTRAINT fk_propcarac_propiedad
        FOREIGN KEY (id_propiedad) REFERENCES propiedad (id_propiedad)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_propcarac_caracteristica
        FOREIGN KEY (id_caracteristica) REFERENCES caracteristica (id_caracteristica)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ===== BLOQUE C: Operación =====

CREATE TABLE cita (
    id_cita       SERIAL PRIMARY KEY,
    id_propiedad  INTEGER NOT NULL,
    id_cliente    INTEGER NOT NULL,
    fecha_hora    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    estado        VARCHAR(20) NOT NULL DEFAULT 'pendiente',
    CONSTRAINT uq_cita_propiedad_fecha UNIQUE (id_propiedad, fecha_hora),
    CONSTRAINT fk_cita_propiedad
        FOREIGN KEY (id_propiedad) REFERENCES propiedad (id_propiedad)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cita_cliente
        FOREIGN KEY (id_cliente) REFERENCES usuario (id_usuario)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE solicitud (
    id_solicitud      SERIAL PRIMARY KEY,
    id_propiedad      INTEGER NOT NULL,
    id_cliente        INTEGER NOT NULL,
    tipo_solicitud    VARCHAR(20) NOT NULL,
    estado            VARCHAR(20) NOT NULL DEFAULT 'pendiente',
    fecha_solicitud   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    observaciones     TEXT,
    CONSTRAINT fk_solicitud_propiedad
        FOREIGN KEY (id_propiedad) REFERENCES propiedad (id_propiedad)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_solicitud_cliente
        FOREIGN KEY (id_cliente) REFERENCES usuario (id_usuario)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE documento_solicitud (
    id_documento     SERIAL PRIMARY KEY,
    id_solicitud     INTEGER NOT NULL,
    tipo_documento   VARCHAR(50) NOT NULL,
    url_archivo      VARCHAR(255) NOT NULL,
    fecha_carga      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_documento_solicitud
        FOREIGN KEY (id_solicitud) REFERENCES solicitud (id_solicitud)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE favorito (
    id_usuario      INTEGER NOT NULL,
    id_propiedad    INTEGER NOT NULL,
    fecha_marcado   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id_usuario, id_propiedad),
    CONSTRAINT fk_favorito_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_favorito_propiedad
        FOREIGN KEY (id_propiedad) REFERENCES propiedad (id_propiedad)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE auditoria (
    id_auditoria    SERIAL PRIMARY KEY,
    id_usuario      INTEGER,
    accion          VARCHAR(100) NOT NULL,
    descripcion     TEXT,
    fecha_evento    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_auditoria_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
        ON DELETE SET NULL ON UPDATE CASCADE
);
