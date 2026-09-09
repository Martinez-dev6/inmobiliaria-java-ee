# Sprint 3 — Planning
## Sistema Web de Inmobiliaria — Programación Java (UTS)

**Duración:** 7 días
**Sprint:** 3 de 3 — "Operación y cierre"
**Roles Scrum:** Product Owner = Docente | Scrum Master + Development Team = Nosotros

---

## Sprint Goal

Completar el ciclo de negocio de la inmobiliaria: que un cliente pueda marcar favoritos, agendar una cita sin cruces de horario, y radicar una solicitud de compra/arriendo con documentos; que el agente pueda aprobar o rechazar esas solicitudes; y que el administrador cuente con un reporte de agregación y consulta de auditoría. Se documentan las 5 consultas SQL obligatorias, se agregan pruebas unitarias, y se deja el proyecto listo para la sustentación final.

---

## Historias de usuario abordadas

| # | Historia | Prioridad (backlog) | Estimación |
|---|---|---|---|
| 8 | Como cliente, quiero marcar propiedades como favoritas para consultarlas más adelante sin tener que buscarlas de nuevo. | Media | 0.5 día |
| 9 | Como cliente, quiero solicitar una cita en un horario disponible para visitar el inmueble sin que se crucen las agendas. | Media | 1.5 días |
| 10 | Como cliente, quiero radicar los documentos de compra o arriendo y consultar el estado de mi solicitud. | Media | 1 día |
| 11 | Como agente de la inmobiliaria, quiero aprobar o rechazar las solicitudes y sus documentos para dar trámite a la negociación. | Media | 1 día |
| 12 | Como administrador, quiero un reporte de propiedades por ciudad y estado, generado con consultas de agregación, para tomar decisiones. | Media | 0.5 día |
| 13 | Como administrador, quiero consultar la auditoría de accesos y cambios para hacer seguimiento a la operación del sistema. | Baja | 0.5 día |

**Nota:** la Historia 14 (otras historias propuestas por el estudiante: mapas, notificaciones, comparador, chat) queda fuera de alcance — ver "Fuera de alcance".

---

## Tareas técnicas (prerrequisito obligatorio antes de programar)

| Tarea | Estimación |
|---|---|
| Documentar las 5 consultas SQL obligatorias del PDF (2 INNER JOIN con 3+ tablas, 1 que resuelva N:M, 1 LEFT JOIN, 1 GROUP BY/HAVING) | 0.5 día |
| Pruebas unitarias (JUnit) sobre los DAO más críticos | 1 día |
| Documentación final: exportar MER/relacional actualizados, revisar diccionario de datos, limpiar pendientes menores (fotos rotas del DML, ruta con "/" inicial) | 0.5 día |

**Nota:** varias de las 5 consultas obligatorias ya existen o son variaciones directas de código de Sprint 2 — `PropiedadCaracteristicaDAO.listarNombresPorPropiedad` ya resuelve la relación N:M exigida, y `PropiedadDAO.SELECT_BASE` ya es un INNER JOIN de 3+ tablas. Documentarlas no debería requerir escribir SQL nuevo desde cero en la mayoría de los casos.

---

## Cronograma (7 días)

| Día | Actividad |
|---|---|
| 1 | Historia 8 — Favoritos |
| 2 | Historia 9 — Citas (agendamiento sin cruce de horario) |
| 3 | Historia 10 — Solicitudes + documentos (cliente) |
| 4 | Historia 11 — Aprobar/rechazar solicitudes (agente) |
| 5 | Historia 12 — Reporte de agregación + Historia 13 — Auditoría (admin) |
| 6 | Documentar las 5 consultas SQL obligatorias + pruebas unitarias |
| 7 | Documentación final + Sprint Review + Sprint Retrospective |

---

## Criterios de aceptación (Definition of Done)

**H8 — Favoritos**
- [ ] El cliente puede marcar/desmarcar una propiedad como favorita desde el catálogo o la ficha de detalle
- [ ] Existe una vista "Mis favoritos" con las propiedades marcadas
- [ ] La relación se guarda en `favorito` (llave primaria compuesta `id_usuario`, `id_propiedad`)

**H9 — Citas**
- [ ] El cliente puede agendar una cita eligiendo fecha y hora para una propiedad específica
- [ ] Se respeta la restricción `UNIQUE (id_propiedad, fecha_hora)` — se captura el error y se muestra un mensaje claro, no una excepción de Java
- [ ] El cliente puede consultar el estado de sus citas

**H10 — Solicitudes + documentos**
- [ ] El cliente puede radicar una solicitud de compra o arriendo para una propiedad, indicando el tipo
- [ ] El cliente puede adjuntar uno o varios documentos a su solicitud (relación 1:N con `documento_solicitud`)
- [ ] El cliente puede consultar el estado de sus solicitudes

**H11 — Aprobar/rechazar solicitudes**
- [ ] El agente ve las solicitudes recibidas para sus propiedades
- [ ] El agente puede aprobar o rechazar una solicitud, restringido a solicitudes de sus propias propiedades (misma protección IDOR de Historia 6)

**H12 — Reporte de agregación**
- [ ] El administrador puede consultar un reporte de propiedades agrupadas por ciudad y estado, con conteos (`GROUP BY`/`HAVING`)

**H13 — Auditoría**
- [ ] El administrador puede consultar el historial de la tabla `auditoria` (ya alimentada desde Sprint 1 con los cambios de rol)

---

## Fuera de alcance en este sprint (explícitamente pospuesto)

- Historia 14 (mapas, notificaciones por correo, comparador de propiedades, chat de contacto) — funcionalidad extra no exigida como mínimo
- Despliegue en línea de la aplicación y la base de datos — bono opcional, fuera de alcance por decisión explícita de Jose
- Recuperación de contraseña por correo y bloqueo temporal tras intentos fallidos — "valor agregado opcional" del PDF, no mínimo
