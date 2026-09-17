# Mejoras y correcciones después del cierre de Sprint 3
**Última actualización:** 17/09/2026

Sprint 3 quedó cerrado el 10/09 con las 6 historias del backlog completas. Después de esa fecha, y antes de la entrega final, se siguió trabajando en dos bloques adicionales — funcionalidad extra que no estaba en el backlog original, y limpieza de pendientes que habían quedado anotados como menores. Este documento existe para que quede claro qué se hizo después del cierre y por qué — no para reabrir el sprint ni para maquillar el registro.

---

## Bloque 1 (14/09/2026)

**Respuesta del agente en citas y solicitudes.** Cuando el agente aprueba, rechaza o confirma una cita o solicitud, ahora puede dejar un motivo o un mensaje de confirmación (`respuesta_agente`, `fecha_respuesta` en `solicitud` y `cita`). Se agregó vía `migration-2026-09-11-respuestas-agente.sql`, y esas mismas columnas ya quedaron en el `ddl-inmobiliaria.sql` para quien instale el proyecto desde cero.

**Panel de administración de propiedades.** El administrador puede dar de baja o reactivar cualquier propiedad del sistema desde `/admin/propiedades` (`AdminPropiedadController`), con el cambio quedando registrado en `auditoria`.

Pendientes menores cerrados en este bloque: fotos de siembra de las 12 propiedades del DML (nunca habían existido físicamente), ruta con "/" inicial en `imagen_propiedad`, y la tabla `favorito` con 9 registros en vez de 10.

---

## Bloque 2 (17/09/2026)

Trabajo hecho con ayuda de Claude Code sobre funciones y visuales, revisado y comiteado en 5 commits temáticos (`bf7d9f2` a `94b4ef3`).

**Layout compartido y experiencia de usuario.** Barra lateral y superior comunes a toda la aplicación autenticada (`app-inicio.jsp`/`app-fin.jsp`), mensajes flash con patrón Post-Redirect-Get (`Flash.java`) para que recargar la página (F5) no reenvíe un formulario ni repita una acción, y paginación real (`Paginacion.java`) en el catálogo, en las propiedades del admin y en la auditoría.

**Catálogo avanzado.** El buscador con filtros (Historia 7) se extendió con filtro por área y por características (relación N:M), y con orden configurable — la columna de orden sale de un catálogo cerrado de opciones, nunca de texto escrito por el usuario, para evitar inyección SQL en el `ORDER BY`. La matrícula inmobiliaria ahora se autogenera (`HG-2026-NNNN`) en vez de que el agente la escriba a mano; las 12 propiedades sembradas por el DML mantienen su formato original (`MAT-XXXX`) porque ya existían antes de este cambio — es una diferencia de origen del dato, no un error.

**Ficha comercial de la inmobiliaria (corrige un vacío real).** Antes, si el administrador le asignaba el rol "Inmobiliaria" a un usuario, esa cuenta quedaba bloqueada sin ninguna forma de crear su ficha de agencia (`InmobiliariaPerfilController`, nuevo). También se agregó un contador de solicitudes y citas pendientes en el menú del agente (`ContadoresAgenteFilter`).

**Gestión de cuentas por el administrador.** Se agregó la función de activar/desactivar cuentas que pedía el PDF y no existía (`UsuarioDAO.cambiarEstadoActivo`), con dos candados: un administrador no puede desactivarse a sí mismo, y el sistema no permite quitarle el rol Administrador al último que quede activo. Se agregó además una regla propia: un administrador no puede modificar el rol ni el estado de la cuenta de **otro** administrador — separación de privilegios deliberada, ver nota abajo.

**Reporte de administrador reestructurado.** El reporte de propiedades por ciudad y estado (la consulta obligatoria de `GROUP BY`/`HAVING`) se reorganizó para agrupar primero por ciudad y desglosar los estados debajo, en vez de repetir el nombre de la ciudad en cada fila. Esto cambió el `SELECT`/`GROUP BY` real de la consulta — `docs/database/consultas-sql.md` se actualizó para reflejar la consulta tal como quedó, no como estaba antes del cambio.

**MER actualizado.** `MER-inmobiliaria.png` no se había vuelto a exportar desde que se agregaron `respuesta_agente`/`fecha_respuesta` en el Bloque 1 — quedó "congelado" en una versión anterior del esquema. Se regeneró completo desde el DDL real (DBML) y se reemplazó.

### Nota sobre "acceso total" del administrador

El PDF describe al Administrador como de "acceso total". La regla de que un admin no pueda tocar la cuenta de otro admin es una decisión de seguridad deliberada (evita que uno solo se quede con el control del sistema, o que una cuenta comprometida desactive a los demás administradores), no una limitación accidental. Vale la pena poder explicarlo así en la sustentación si se pregunta por qué existe esa restricción.

### Por qué esto no está en `sprint3-review.md`

Ese documento describe honestamente lo que se sabía y se había hecho al 10/09. No se reescribió para que pareciera que todo estaba listo desde el cierre; se prefirió dejar la historia real y agregar este documento aparte con lo que pasó después, en dos bloques fechados por separado.
