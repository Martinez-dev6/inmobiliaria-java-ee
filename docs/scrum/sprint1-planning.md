# Sprint 1 — Planning
## Sistema Web de Inmobiliaria — Programación Java (UTS)

**Duración:** 7 días
**Sprint:** 1 de 3 — "Cimientos y acceso"
**Roles Scrum:** Product Owner = Docente | Scrum Master + Development Team = Nosotros

---

## Meta del sprint

Dejar la base de datos diseñada y creada, la conexión JDBC centralizada funcionando, y todo el flujo de landing page → registro → login → redirección por rol, con el control de acceso validado del lado del servidor (no solo ocultando botones en la vista).

---

## Historias que abordamos

| # | Historia | Prioridad | Estimación |
|---|---|---|---|
| 1 | Como visitante, quiero una página de aterrizaje atractiva para conocer la inmobiliaria y buscar propiedades rápidamente. | Alta | 1 día |
| 2 | Como usuario, quiero registrarme con un correo único y validado para crear mi cuenta sin duplicados. | Alta | 0.5 día |
| 3 | Como usuario registrado, quiero iniciar y cerrar sesión de forma segura para que el sistema me lleve al panel de mi rol. | Alta | 0.5 día |
| 4 | Como administrador, quiero asignar y revocar roles a los usuarios para controlar los permisos de la aplicación. | Alta | 1 día |

La Historia 5 (perfil de usuario, la relación 1:1) la dejamos a propósito para Sprint 2 — así lo sugiere la distribución del docente y no tenía sentido adelantarla sin tener aún login funcionando.

---

## Antes de programar: el modelo de datos

El PDF es claro en esto — primero se diseña y sustenta el modelo, después se escribe código. Así que antes de tocar un solo controlador:

| Tarea | Estimación |
|---|---|
| Diseño del MER | 1 día |
| Modelo relacional en 3FN + diccionario de datos | 1 día |
| Script DDL + script DML (mínimo 10 registros por tabla principal) | 1 día |
| Conexión JDBC centralizada y configurable | 1 día |

---

## Cómo repartimos los 7 días

| Día | Actividad |
|---|---|
| 1–2 | MER + modelo relacional 3FN + diccionario de datos |
| 3 | Script DDL + DML |
| 4 | Conexión JDBC centralizada |
| 5 | Historia 1 — Landing page |
| 6 | Historias 2 y 3 — Registro + Login/logout |
| 7 | Historia 4 — Roles + Filter de acceso + Sprint Review |

---

## Cuándo damos por hecha cada historia

**H1 — Landing page**
Responsiva, con propiedades destacadas y buscador rápido visibles, y accesos a registro/login para quien no ha iniciado sesión.

**H2 — Registro**
Valida el formato del correo, rechaza duplicados con un mensaje claro (nada de stacktrace de Java en pantalla), y la contraseña se guarda con hash — BCrypt, PBKDF2 o SHA-256 con salt, nunca texto plano.

**H3 — Login/logout**
Valida contra la base de datos, crea la `HttpSession` con el id del usuario y su(s) rol(es), redirige al panel que corresponde, y el logout invalida la sesión de verdad (no solo redirige).

**H4 — Roles y control de acceso**
El admin puede asignar y revocar roles desde su panel. Un filtro de servlet bloquea las rutas privadas si alguien entra sin sesión o con el rol equivocado, mandándolo a una página de acceso denegado — probando escribir la URL a mano, no solo navegando por los menús.

---

## Lo que dejamos para después, a propósito

- Perfil de usuario (1:1) → Sprint 2
- CRUD de propiedades, imágenes, características → Sprint 2
- Citas, solicitudes, documentos, favoritos, reportes → Sprint 3
- Chat de contacto, notificaciones por correo → si sobra tiempo, si no, se descarta
