# Sprint 2 — Retrospective
**Fecha de cierre:** 08/09/2026

## Qué salió bien

Reutilizar lo que ya habíamos probado en Sprint 1 —la transacción manual, la excepción *checked* para los UNIQUE, el `${pageContext.request.contextPath}`— hizo que las Historias 6 y 7 avanzaran sin tener que redescubrir nada. La protección contra IDOR (`WHERE id_propiedad = ? AND id_inmobiliaria = ?`) la metimos desde el principio en toda escritura, no como parche después de encontrar el problema. Y sacar `SELECT_BASE` como constante reutilizable en `PropiedadDAO` nos evitó escribir el mismo JOIN cuatro veces (listado del agente, catálogo, destacadas, ficha de detalle).

## Qué no salió tan bien

| Problema | Por qué pasó | Cómo lo arreglamos |
|---|---|---|
| Empezamos la galería de imágenes con URLs externas de Pexels en vez de archivos reales | Priorizamos lo más simple de implementar por encima del principio del proyecto de ser una app "realmente funcional" | Jose lo objetó; revisamos el DML de la Fase 1 y confirmamos que ya se esperaban archivos locales, así que lo reescribimos con `@MultipartConfig` + la API `Part` |
| Las tildes y la ñ se guardaban corrompidas al guardar el perfil | Tomcat decodifica el body de un POST en ISO-8859-1 por defecto | Un `CodificacionFilter` global con `urlPatterns="/*"` |
| El `sprint2-planning.md` no coincidía en formato con el de Sprint 1 | Lo redactamos de memoria sin tener el archivo real a la vista | Lo comparamos línea por línea contra el `.zip` del proyecto |
| Los botones de acceso y destacados de la landing no llevaban a ningún lado | Enlaces heredados de Sprint 1 que apuntaban a archivos que nunca existieron | Los corregimos uno por uno al tocar cada sección |
| No había forma de marcar una propiedad como destacada sin entrar a pgAdmin | La columna `destacada` nunca quedó incluida en el INSERT/UPDATE del CRUD | Agregamos el checkbox y completamos ambas sentencias |

## Para el próximo sprint

Antes de diseñar cómo se va a guardar un dato nuevo (como pasó con las imágenes), primero revisar si el DML o el DDL ya tomaron una decisión al respecto, en vez de proponer algo desde cero. Al construir cada CRUD, verificar que *todas* las columnas de la tabla queden cubiertas por el formulario, no solo las que parecen obligatorias a primera vista. Y seguir revisando los enlaces viejos de sprints anteriores cada vez que se toca una sección relacionada — varios bugs de Sprint 1 solo aparecieron ahora, al construir lo que realmente los conectaba.

Para Sprint 3: mantener la misma protección IDOR en todo lo nuevo (citas, solicitudes, documentos, favoritos), dejar anotado desde el Planning cuál de las 5 consultas SQL obligatorias resuelve cada historia (varias ya están construidas desde Sprint 2, como el JOIN N:M de características), y antes de cerrar una historia, probar el caso "esto debería poder hacerse sin tocar la base de datos a mano" — el principio que guió varias correcciones este sprint.
