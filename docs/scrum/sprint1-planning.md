# Sprint 1 — Planning
## Sistema Web de Inmobiliaria — Programación Java (UTS)

**Duración:** 7 días
**Sprint:** 1 de 3 — "Cimientos y acceso"
**Roles Scrum:** Product Owner = Docente | Scrum Master + Development Team = Nosotros

---

## Sprint Goal

Tener la base de datos diseñada y creada, la conexión JDBC centralizada
funcionando, y un flujo completo de landing page → registro → login →
redirección por rol, con control de acceso validado en servidor.

---

## Historias de usuario abordadas

| # | Historia | Prioridad (backlog) | Estimación |
|---|---|---|---|
| 1 | Como visitante, quiero una página de aterrizaje atractiva para conocer la inmobiliaria y buscar propiedades rápidamente. | Alta | 1 día |
| 2 | Como usuario, quiero registrarme con un correo único y validado para crear mi cuenta sin duplicados en el sistema. | Alta | 0.5 día |
| 3 | Como usuario registrado, quiero iniciar y cerrar sesión de forma segura para que el sistema me lleve al panel que corresponde a mi rol. | Alta | 0.5 día |
| 4 | Como administrador, quiero asignar y revocar roles a los usuarios para controlar los permisos de la aplicación. | Alta | 1 día |

**Nota:** la Historia 5 (perfil de usuario 1:1) se deja explícitamente para
Sprint 2, según la distribución sugerida por el docente.

---

## Tareas técnicas (prerrequisito obligatorio antes de programar)

| Tarea | Estimación |
|---|---|
| Diseño del MER | 1 día |
| Modelo relacional en 3FN + diccionario de datos | 1 día |
| Script DDL + script DML (≥10 registros/tabla principal) | 1 día |
| Conexión JDBC centralizada y configurable | 1 día |

---

## Cronograma (7 días)

| Día | Actividad |
|---|---|
| 1–2 | MER + modelo relacional 3FN + diccionario de datos |
| 3 | Script DDL + DML |
| 4 | Conexión JDBC centralizada |
| 5 | Historia 1 — Landing page |
| 6 | Historias 2 y 3 — Registro + Login/logout |
| 7 | Historia 4 — Roles + Filter de control de acceso + Sprint Review |

---

## Criterios de aceptación (Definition of Done)

**H1 — Landing page**
- [ ] Responsiva (Bootstrap / media queries)
- [ ] Muestra propiedades destacadas
- [ ] Buscador rápido visible
- [ ] Accesos a registro/login visibles para visitante no autenticado

**H2 — Registro**
- [ ] Valida formato de correo
- [ ] Rechaza correos duplicados con mensaje claro (no stacktrace de Java)
- [ ] Contraseña almacenada con hash (BCrypt/PBKDF2/SHA-256+salt), nunca texto plano

**H3 — Login/logout**
- [ ] Valida credenciales contra la base de datos
- [ ] Crea HttpSession con id_usuario y rol(es)
- [ ] Redirige al dashboard correspondiente según rol
- [ ] Logout invalida correctamente la sesión

**H4 — Gestión de roles + control de acceso**
- [ ] Admin puede asignar/revocar roles desde su panel
- [ ] Filter de servlet bloquea rutas privadas a usuarios no autenticados o sin el rol requerido
- [ ] Redirección a página de acceso denegado al intentar entrar por URL directa

---

## Fuera de alcance en este sprint (explícitamente pospuesto)

- Perfil de usuario (1:1) → Sprint 2
- CRUD de propiedades, imágenes, características → Sprint 2
- Citas, solicitudes, documentos, favoritos, reportes → Sprint 3
- Chat de contacto, notificaciones por correo → Backlog extra (Sprint 3 o descartable si falta tiempo)
