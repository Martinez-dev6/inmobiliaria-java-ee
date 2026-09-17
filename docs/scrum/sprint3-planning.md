# Sprint 3 — Planning
## Sistema Web de Inmobiliaria — Programación Java (UTS)

**Duración:** 7 días
**Sprint:** 3 de 3 — "Operación y cierre"
**Roles Scrum:** Product Owner = Docente | Scrum Master + Development Team = Nosotros

---

## Meta del sprint

Cerrar el ciclo de negocio completo: que un cliente pueda marcar favoritos, agendar una cita sin que se cruce con otra, y radicar una solicitud de compra o arriendo con sus documentos; que el agente pueda aprobar o rechazar esas solicitudes; y que el administrador tenga un reporte de agregación y pueda consultar la auditoría. También documentamos las 5 consultas SQL que pide el PDF, agregamos pruebas unitarias, y dejamos todo listo para la sustentación.

---

## Historias que abordamos

| # | Historia | Prioridad | Estimación |
|---|---|---|---|
| 8 | Como cliente, quiero marcar propiedades como favoritas para no tener que buscarlas de nuevo. | Media | 0.5 día |
| 9 | Como cliente, quiero solicitar una cita en un horario disponible sin que se crucen las agendas. | Media | 1.5 días |
| 10 | Como cliente, quiero radicar documentos de compra o arriendo y consultar el estado de mi solicitud. | Media | 1 día |
| 11 | Como agente, quiero aprobar o rechazar solicitudes y sus documentos. | Media | 1 día |
| 12 | Como administrador, quiero un reporte de propiedades por ciudad y estado con agregación. | Media | 0.5 día |
| 13 | Como administrador, quiero consultar la auditoría de accesos y cambios. | Baja | 0.5 día |

La Historia 14 (las que uno mismo puede proponer — mapas, notificaciones, comparador, chat) la dejamos fuera de alcance, está en la sección de "fuera de alcance" más abajo.

---

## Antes de programar

| Tarea | Estimación |
|---|---|
| Documentar las 5 consultas SQL obligatorias (2 INNER JOIN con 3+ tablas, 1 que resuelva N:M, 1 LEFT JOIN, 1 GROUP BY/HAVING) | 0.5 día |
| Pruebas unitarias (JUnit) sobre los DAO más críticos | 1 día |
| Documentación final: exportar MER/relacional actualizados, revisar el diccionario de datos, limpiar pendientes menores (fotos del DML, la ruta con "/" inicial) | 0.5 día |

Varias de las 5 consultas obligatorias ya existían de Sprint 2 en otra forma — `PropiedadCaracteristicaDAO.listarNombresPorPropiedad` ya resuelve la relación N:M, y `PropiedadDAO.SELECT_BASE` ya es un INNER JOIN de 3+ tablas. Documentarlas no debería implicar escribir SQL nuevo en la mayoría de los casos.

---

## Cómo repartimos los 7 días

| Día | Actividad |
|---|---|
| 1 | Historia 8 — Favoritos |
| 2 | Historia 9 — Citas |
| 3 | Historia 10 — Solicitudes + documentos |
| 4 | Historia 11 — Aprobar/rechazar |
| 5 | Historia 12 — Reporte + Historia 13 — Auditoría |
| 6 | Documentar las 5 consultas + pruebas unitarias |
| 7 | Documentación final + Sprint Review + Retrospective |

---

## Cuándo damos por hecha cada historia

**H8 — Favoritos:** marcar/desmarcar desde el catálogo o la ficha de detalle, con una vista "Mis favoritos", guardado en `favorito` con llave compuesta (`id_usuario`, `id_propiedad`).

**H9 — Citas:** el cliente elige fecha y hora para una propiedad; la restricción `UNIQUE (id_propiedad, fecha_hora)` se captura con un mensaje claro si ya está ocupado ese horario, no con una excepción cruda; el cliente puede ver el estado de sus citas.

**H10 — Solicitudes + documentos:** radicar una solicitud de compra o arriendo, adjuntar uno o varios documentos (1:N con `documento_solicitud`), y consultar el estado.

**H11 — Aprobar/rechazar:** el agente ve solo las solicitudes de sus propias propiedades y decide sobre ellas — misma protección IDOR que en la Historia 6.

**H12 — Reporte:** propiedades agrupadas por ciudad y estado, con conteos (`GROUP BY`/`HAVING`).

**H13 — Auditoría:** consultar el historial de la tabla `auditoria`, que ya se viene alimentando desde Sprint 1.

---

## Lo que dejamos fuera de este sprint

Historia 14 (mapas, notificaciones, comparador, chat) — no es mínimo exigido. Despliegue en línea de la app y la base de datos — es un bono opcional, y por ahora quedó fuera de alcance por decisión nuestra. Recuperación de contraseña por correo y bloqueo tras intentos fallidos — el PDF los marca como "valor agregado opcional", no como mínimo.
