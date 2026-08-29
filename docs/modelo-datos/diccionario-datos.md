# Diccionario de Datos
## Sistema Web de Inmobiliaria — Programación Java (UTS)

Este documento describe cada tabla y columna del modelo de datos,
diseñado y verificado en 3FN. Ver también: `MER-inmobiliaria.png`.

**Convenciones generales:**
- Nombres en snake_case, sin tildes ni ñ (compatibilidad).
- Todas las fechas usan `timestamp without time zone`.
- PK = Primary Key · FK = Foreign Key · NN = Not Null · UQ = Unique

---

## BLOQUE A — Autenticación y roles

### Tabla `usuario`
Guarda únicamente credenciales y estado de la cuenta.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_usuario | serial | PK | ✔ | | Identificador único del usuario |
| correo | varchar(150) | | ✔ | ✔ | Credencial de ingreso, única por cuenta |
| contrasena_hash | varchar(255) | | ✔ | | Contraseña cifrada (BCrypt/PBKDF2/SHA-256+salt) |
| activo | boolean | | ✔ | | Estado de la cuenta (activa/inactiva) |
| fecha_registro | timestamp | | ✔ | | Fecha de creación de la cuenta |

### Tabla `perfil`
Datos personales del usuario, separados de sus credenciales (relación 1:1).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_perfil | serial | PK | ✔ | | Identificador único del perfil |
| id_usuario | integer | FK → usuario | ✔ | ✔ | Garantiza la relación 1:1 |
| nombres | varchar(100) | | ✔ | | Nombres de la persona |
| apellidos | varchar(100) | | ✔ | | Apellidos de la persona |
| documento | varchar(30) | | ✔ | | Número de documento de identidad |
| telefono | varchar(20) | | | | Teléfono de contacto |
| direccion | varchar(200) | | | | Dirección de residencia |
| foto_url | varchar(255) | | | | Ruta/URL de la foto de perfil |

### Tabla `inmobiliaria`
Datos de negocio del usuario con rol Inmobiliaria (relación 1:1).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_inmobiliaria | serial | PK | ✔ | | Identificador único de la inmobiliaria |
| id_usuario | integer | FK → usuario | ✔ | ✔ | Garantiza la relación 1:1 |
| nombre_comercial | varchar(150) | | ✔ | | Nombre comercial de la agencia |
| nit | varchar(30) | | | | Identificación tributaria |
| telefono_contacto | varchar(20) | | | | Teléfono de contacto comercial |

### Tabla `rol`
Catálogo de roles del sistema (Cliente, Inmobiliaria, Administrador).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_rol | serial | PK | ✔ | | Identificador único del rol |
| nombre_rol | varchar(30) | | ✔ | ✔ | Nombre del rol |

### Tabla `usuario_rol`
Tabla intermedia N:M — resuelve que un usuario tenga varios roles.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_usuario | integer | PK compuesta, FK → usuario | ✔ | | Usuario asignado |
| id_rol | integer | PK compuesta, FK → rol | ✔ | | Rol asignado |
| fecha_asignacion | timestamp | | ✔ | | Fecha en que se otorgó el rol |

---

## BLOQUE B — Catálogo e inmobiliaria

### Tabla `ciudad`
Catálogo de ciudades donde hay propiedades registradas.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_ciudad | serial | PK | ✔ | | Identificador único de la ciudad |
| nombre_ciudad | varchar(100) | | ✔ | ✔ | Nombre de la ciudad |

### Tabla `tipo_propiedad`
Catálogo de tipos de inmueble (Casa, Apartamento, Local, Oficina, Terreno).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_tipo_propiedad | serial | PK | ✔ | | Identificador único del tipo |
| nombre_tipo | varchar(50) | | ✔ | ✔ | Nombre del tipo de propiedad |

### Tabla `propiedad`
Tabla central del sistema — el inmueble publicado.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_propiedad | serial | PK | ✔ | | Identificador único de la propiedad |
| id_inmobiliaria | integer | FK → inmobiliaria | ✔ | | Agencia que publicó el inmueble |
| id_ciudad | integer | FK → ciudad | ✔ | | Ciudad donde está ubicado |
| id_tipo_propiedad | integer | FK → tipo_propiedad | ✔ | | Tipo de inmueble |
| matricula_inmobiliaria | varchar(50) | | ✔ | ✔ | Identifica de forma irrepetible el inmueble |
| titulo | varchar(150) | | ✔ | | Título de la publicación |
| descripcion | text | | | | Descripción detallada |
| direccion | varchar(200) | | ✔ | | Dirección física del inmueble |
| precio | numeric(14,2) | | ✔ | | Precio de venta o arriendo |
| area_m2 | numeric(8,2) | | | | Área en metros cuadrados |
| estado | varchar(20) | | ✔ | | disponible / vendida / arrendada / inactiva |
| destacada | boolean | | ✔ | | Si aparece como destacada en landing page |
| fecha_publicacion | timestamp | | ✔ | | Fecha de publicación |

### Tabla `imagen_propiedad`
Imágenes asociadas a una propiedad (relación 1:N).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_imagen | serial | PK | ✔ | | Identificador único de la imagen |
| id_propiedad | integer | FK → propiedad | ✔ | | Propiedad a la que pertenece |
| url_imagen | varchar(255) | | ✔ | | Ruta/URL de la imagen |
| orden | integer | | | | Orden de despliegue en la galería |

### Tabla `caracteristica`
Catálogo de características de inmuebles (piscina, parqueadero, etc.).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_caracteristica | serial | PK | ✔ | | Identificador único de la característica |
| nombre_caracteristica | varchar(50) | | ✔ | ✔ | Nombre de la característica |

### Tabla `propiedad_caracteristica`
Tabla intermedia N:M — una propiedad puede tener varias características.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_propiedad | integer | PK compuesta, FK → propiedad | ✔ | | Propiedad relacionada |
| id_caracteristica | integer | PK compuesta, FK → caracteristica | ✔ | | Característica asignada |

---

## BLOQUE C — Operación

### Tabla `cita`
Agendamiento de visitas a una propiedad.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_cita | serial | PK | ✔ | | Identificador único de la cita |
| id_propiedad | integer | FK → propiedad | ✔ | | Propiedad a visitar |
| id_cliente | integer | FK → usuario | ✔ | | Cliente que agenda la visita |
| fecha_hora | timestamp | | ✔ | | Fecha y hora de la visita |
| estado | varchar(20) | | ✔ | | pendiente / confirmada / cancelada / realizada |
| *(id_propiedad, fecha_hora)* | — | | | ✔ | UNIQUE compuesta: evita cruces de agenda en la misma propiedad |

### Tabla `solicitud`
Trámite de compra o arriendo de una propiedad.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_solicitud | serial | PK | ✔ | | Identificador único de la solicitud |
| id_propiedad | integer | FK → propiedad | ✔ | | Propiedad solicitada |
| id_cliente | integer | FK → usuario | ✔ | | Cliente que radica la solicitud |
| tipo_solicitud | varchar(20) | | ✔ | | compra / arriendo |
| estado | varchar(20) | | ✔ | | pendiente / aprobada / rechazada |
| fecha_solicitud | timestamp | | ✔ | | Fecha de radicación |
| observaciones | text | | | | Comentario de la inmobiliaria al aprobar/rechazar |

### Tabla `documento_solicitud`
Documentos adjuntos a una solicitud (relación 1:N).

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_documento | serial | PK | ✔ | | Identificador único del documento |
| id_solicitud | integer | FK → solicitud | ✔ | | Solicitud a la que pertenece |
| tipo_documento | varchar(50) | | ✔ | | Ej. cédula, comprobante de ingresos |
| url_archivo | varchar(255) | | ✔ | | Ruta/URL del archivo |
| fecha_carga | timestamp | | ✔ | | Fecha de carga del documento |

### Tabla `favorito`
Tabla intermedia N:M — propiedades marcadas como favoritas por un usuario.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_usuario | integer | PK compuesta, FK → usuario | ✔ | | Usuario que marca el favorito |
| id_propiedad | integer | PK compuesta, FK → propiedad | ✔ | | Propiedad marcada |
| fecha_marcado | timestamp | | ✔ | | Fecha en que se marcó como favorita |

### Tabla `auditoria`
Registro transversal de actividad del sistema.

| Columna | Tipo | PK/FK | NN | UQ | Descripción |
|---|---|---|---|---|---|
| id_auditoria | serial | PK | ✔ | | Identificador único del evento |
| id_usuario | integer | FK → usuario (ON DELETE SET NULL) | | | Usuario que ejecutó la acción (puede quedar null) |
| accion | varchar(100) | | ✔ | | Ej. login, creacion_propiedad, cambio_rol |
| descripcion | text | | | | Detalle adicional del evento |
| fecha_evento | timestamp | | ✔ | | Fecha y hora del evento |

---

## Resumen de restricciones UNIQUE (mínimo 3 exigidas por el PDF)

1. `usuario.correo` — obligatoria
2. `propiedad.matricula_inmobiliaria`
3. `perfil.id_usuario` (y `usuario_rol(id_usuario, id_rol)` como PK compuesta)
4. `cita(id_propiedad, fecha_hora)` — sugerida, implementada

## Resumen de las 3 relaciones obligatorias

- **1:1** → `usuario`↔`perfil`, `usuario`↔`inmobiliaria`
- **1:N** → `inmobiliaria`→propiedad, `ciudad`→propiedad, `tipo_propiedad`→propiedad,
  `propiedad`→imagen_propiedad, `propiedad`→cita, `usuario`→cita, `propiedad`→solicitud,
  `usuario`→solicitud, `solicitud`→documento_solicitud, `usuario`→auditoria
- **N:M** → `usuario`↔`rol` (vía usuario_rol), `propiedad`↔`caracteristica`
  (vía propiedad_caracteristica), `usuario`↔`propiedad` (vía favorito)
