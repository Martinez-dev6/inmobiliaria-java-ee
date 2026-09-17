# Mejoras y correcciones después del cierre de Sprint 3
**Fecha:** 14/09/2026

Sprint 3 quedó cerrado el 10/09 con las 6 historias del backlog completas. Después de esa fecha, y antes de la entrega final, seguimos trabajando en dos frentes: funcionalidad extra que no estaba en el backlog original, y una limpieza de pendientes que habíamos dejado anotados como menores. Este documento existe para que quede claro qué se hizo después del cierre y por qué — no para reabrir el sprint ni para maquillar el registro.

## Funcionalidad extra (no exigida por el backlog, agregada por decisión nuestra)

**Respuesta del agente en citas y solicitudes.** Cuando el agente aprueba, rechaza o confirma una cita o solicitud, ahora puede dejar un motivo o un mensaje de confirmación (`respuesta_agente`, `fecha_respuesta` en `solicitud` y `cita`). Se agregó vía `migration-2026-09-11-respuestas-agente.sql` — ejecutable una sola vez contra una base ya instalada, sin borrar ni tocar datos existentes — y esas mismas columnas ya quedaron en el `ddl-inmobiliaria.sql` para quien instale el proyecto desde cero.

**Panel de administración de propiedades.** El administrador ahora puede dar de baja o reactivar cualquier propiedad del sistema desde `/admin/propiedades` (`AdminPropiedadController`), con el cambio quedando registrado en `auditoria`. Antes, esa acción solo la podía hacer el agente dueño de la propiedad.

Las dos quedaron registradas como issues en el tablero de GitHub Projects (`#16` y `#17`), marcadas como Done, y se comitearon el 14/09 junto con `web.xml` (páginas de error 404/500, nunca antes comiteado), el `navbar.jsp` compartido y varios ajustes de estilo.

## Pendientes menores que se cerraron

- **Fotos de siembra de las 12 propiedades del DML:** nunca habían existido físicamente. Se consiguieron de Pexels, se colocaron en `web/img/propiedades/` y se comitearon.
- **Ruta con "/" inicial en `imagen_propiedad`:** el DML ya la traía así desde la Fase 1; no rompía nada visualmente (el navegador tolera la doble barra resultante), pero era una inconsistencia menor. Se corrigió en el script.
- **Tabla `favorito` con 9 registros:** el PDF pide mínimo 10 por tabla principal. Se agregó una fila más.

## Por qué esto no está en `sprint3-review.md`

Ese documento describe honestamente lo que se sabía y se había hecho al 10/09 — incluye, de hecho, las fotos y la ruta con "/" como pendientes sin resolver en ese momento. No lo reescribimos para que pareciera que todo estaba listo desde el cierre; preferimos dejar la historia real y agregar este documento aparte con lo que pasó después.
