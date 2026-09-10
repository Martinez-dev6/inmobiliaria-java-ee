# Sprint 3 — Review
**Fecha de cierre:** 10/09/2026

## Historias completadas (6 de 6 planificadas)

| Historia | Descripción | Estado | Evidencia |
|---|---|---|---|
| H8 | Favoritos | ✅ Completa | Marcar/desmarcar verificado en pgAdmin y en la vista "Mis favoritos" |
| H9 | Citas sin cruce de horario | ✅ Completa | Intento de agendar la misma propiedad a la misma hora dos veces: la segunda fue rechazada con mensaje claro |
| H10 | Solicitudes + documentos | ✅ Completa | Solicitud radicada con documento adjunto, verificado el archivo físico y el registro en `documento_solicitud` |
| H11 | Aprobar/rechazar solicitudes | ✅ Completa | Aprobación/rechazo probado desde el panel de agente, con protección IDOR |
| H12 | Reporte de agregación | ✅ Completa | `/admin/reportes` mostrando propiedades por ciudad y estado |
| H13 | Auditoría | ✅ Completa | `/admin/auditoria` mostrando los cambios de rol de Sprint 1 |

## Tareas técnicas completadas

| Tarea | Estado | Evidencia |
|---|---|---|
| 5 consultas SQL obligatorias documentadas | ✅ Completa | `docs/consultas-sql.md` — 2 INNER JOIN, 1 N:M, 1 LEFT JOIN, 1 GROUP BY/HAVING, todas en código real |
| Pruebas unitarias (JUnit) | ✅ Completa | 3 pruebas (`CiudadDAOTest`, `PropiedadDAOTest`, `FavoritoDAOTest`), confirmadas en verde tras resolver dos problemas de configuración del entorno |
| Documentación final | ✅ Completa | Este documento, la retrospectiva, y el registro maestro actualizado |

## Demostración funcional realizada
- Favoritos: marcar y desmarcar desde la ficha de detalle, reflejado en "Mis favoritos".
- Citas: agendamiento con verificación en vivo de que el `UNIQUE(id_propiedad, fecha_hora)` rechaza horarios duplicados sin lanzar una excepción cruda de Java.
- Solicitudes: radicación con documento adjunto (subida real de archivo), visible y descargable desde el panel del agente.
- Aprobación/rechazo de solicitudes, restringido a las propiedades del agente que hace la acción.
- Reporte de agregación por ciudad y estado, y consulta de auditoría con los eventos históricos de Sprint 1 visibles.
- Suite de pruebas unitarias corriendo en verde (100%) contra la base de datos real de desarrollo.

## Funcionalidad adicional agregada (no exigida explícitamente)
- Contadores reales de citas y solicitudes en los paneles de cliente y agente (antes eran tarjetas de "Próximamente").
- Documento `docs/consultas-sql.md` con las 5 consultas explicadas, no solo listadas.

## Desviaciones respecto al Planning
- Las Historias 9-13 se entregaron en un `.zip` con manifiesto de instalación en vez del flujo incremental de las historias anteriores, por decisión explícita de Jose ante la cercanía de la fecha de entrega.
- Aparecieron dos problemas de configuración del entorno de pruebas (ruta de carpetas duplicada y dependencia de Hamcrest faltante) no previstos en el Planning — se resolvieron sin afectar el alcance ni el código de la aplicación en sí.