# Sprint 1 — Retrospective
**Fecha de cierre:** 07/09/2026

## ¿Qué salió bien?
- La arquitectura en capas (modelo / DAO / controlador / vista) se mantuvo consistente en las 4 historias, facilitando agregar la Historia 4 reutilizando el mismo `UsuarioDAO`.
- El uso de transacciones manuales (`setAutoCommit(false)`) en el registro protegió la integridad de datos: se comprobó en la práctica que, ante un error a mitad de proceso, no quedaron registros huérfanos.
- Los commits frecuentes con mensajes descriptivos permitieron aislar y revertir errores con facilidad.

## ¿Qué no salió bien / problemas encontrados?

| Problema | Causa | Solución aplicada |
|---|---|---|
| `git add "Web Pages/..."` fallaba | Confusión entre el nombre visual del nodo en NetBeans ("Web Pages") y la carpeta real en disco (`web/`) | Verificar siempre rutas reales con `git status` antes de un `add` |
| Archivos `git` y `master` aparecieron sueltos en la raíz | Redirección accidental de la terminal (`>`) al pegar un comando con el prompt incluido | Escribir comandos directamente en la terminal, no pegar bloques completos |
| Registro fallaba con "No se pudo completar el registro" | El DAO buscaba el rol `'cliente'` en minúscula, pero el DML lo sembró como `'Cliente'` (comparación sensible a mayúsculas en PostgreSQL) | Ajustar la constante al valor exacto del catálogo; se evaluó alternativa con `LOWER()` para mayor robustez futura |
| Paneles del login/registro se veían superpuestos | Al adaptar un diseño de referencia, se dejó el desplazamiento visual sin ocultar la cara inactiva (`opacity`) | Agregar `opacity`/`pointer-events` a cada estado del panel |
| Botón "Cerrar sesión" daba 404 dentro de `/admin/` | Ruta relativa (`href="logout"`) se resolvía distinto según la carpeta del JSP que la contenía | Usar siempre `${pageContext.request.contextPath}/...` para rutas absolutas dentro de la app |

## ¿Qué mejorar para el próximo sprint?
- Revisar archivos de referencia (CSS, layouts existentes) **antes** de escribir código nuevo que dependa de ellos, en vez de asumir su contenido.
- Usar siempre `e.printStackTrace()` (o un logger) en los `catch` que conviertan errores técnicos en mensajes de usuario, para no perder el detalle real del fallo.
- Adoptar desde ya la convención `${pageContext.request.contextPath}/...` en toda ruta interna, antes de que Sprint 2 agregue más carpetas por rol.

## Compromisos para Sprint 2
- Mantener la misma disciplina de commits frecuentes y verificación con `git status` antes de cada `add`.
- Documentar decisiones de diseño no exigidas por el profesor (marcadas como [NUESTRA DECISIÓN]) directamente en el código o en este mismo repositorio de documentación.
