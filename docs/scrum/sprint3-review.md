# Sprint 3 — Review
**Fecha de cierre:** 10/09/2026

Las 6 historias planificadas quedaron completas.

| Historia | Qué era | Evidencia |
|---|---|---|
| H8 | Favoritos | Marcar/desmarcar verificado en pgAdmin y en la vista "Mis favoritos" |
| H9 | Citas sin cruce de horario | Agendamos la misma propiedad a la misma hora dos veces: la segunda se rechazó con mensaje claro |
| H10 | Solicitudes + documentos | Radicamos una solicitud con documento adjunto, verificamos el archivo físico y la fila en `documento_solicitud` |
| H11 | Aprobar/rechazar solicitudes | Probado desde el panel de agente, con la protección IDOR funcionando |
| H12 | Reporte de agregación | `/admin/reportes` mostrando propiedades por ciudad y estado |
| H13 | Auditoría | `/admin/auditoria` mostrando los cambios de rol de Sprint 1 |

| Tarea técnica | Evidencia |
|---|---|
| Las 5 consultas SQL obligatorias documentadas | `docs/database/consultas-sql.md` — 2 INNER JOIN, 1 N:M, 1 LEFT JOIN, 1 GROUP BY/HAVING, todas tomadas del código real, no escritas aparte |
| Pruebas unitarias (JUnit) | 3 pruebas (`CiudadDAOTest`, `PropiedadDAOTest`, `FavoritoDAOTest`), en verde después de resolver dos problemas de configuración del entorno |
| Documentación final | Este documento, la retrospectiva y el registro maestro. Dos pendientes menores de esta tarea —las fotos de siembra del DML y la ruta con "/" inicial en `imagen_propiedad`— quedaron sin resolver en este momento; se cerraron después, el 14/09 (ver `post-sprint3-mejoras.md`) |

## Cómo lo probamos

Marcamos y desmarcamos favoritos desde la ficha de detalle y los vimos reflejados en "Mis favoritos". Agendamos una cita y confirmamos en vivo que el `UNIQUE(id_propiedad, fecha_hora)` rechaza un horario repetido sin lanzar una excepción cruda de Java. Radicamos una solicitud con documento adjunto —subida real de archivo— y la vimos visible y descargable desde el panel del agente. Aprobamos y rechazamos solicitudes, siempre restringido a las propiedades del agente que hace la acción. Revisamos el reporte por ciudad y estado, y la auditoría mostrando los eventos históricos de Sprint 1. Y corrimos la suite de pruebas unitarias completa contra la base de datos real de desarrollo — 100% en verde.

## Cosas que agregamos sin que nos las pidieran

Contadores reales de citas y solicitudes en los paneles de cliente y agente (antes eran tarjetas de "Próximamente"). Y el documento de consultas SQL quedó explicando el porqué de cada una, no solo listándolas.

## ¿Nos desviamos del Planning?

Sí, en dos cosas. Las Historias 9 a 13 las entregamos en un `.zip` con manifiesto de instalación, no con el flujo incremental historia por historia que veníamos usando — fue una decisión de Jose por lo cerca que estaba la fecha de entrega. Y nos aparecieron dos problemas de configuración del entorno de pruebas (una ruta de carpetas duplicada y una dependencia de Hamcrest que faltaba) que no estaban previstos, pero no afectaron el alcance ni el código de la aplicación.
