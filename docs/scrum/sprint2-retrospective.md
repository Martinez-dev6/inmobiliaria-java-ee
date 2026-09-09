# Sprint 2 — Retrospective
**Fecha de cierre:** 08/09/2026

## ¿Qué salió bien?
- Reutilizar patrones ya establecidos en Sprint 1 (transacción manual, excepción *checked* para UNIQUE, `${pageContext.request.contextPath}`) aceleró las Historias 6 y 7 sin tener que redescubrir nada.
- La protección contra IDOR (`WHERE id_propiedad = ? AND id_inmobiliaria = ?`) se aplicó de forma consistente en toda escritura desde el principio, no como parche después.
- Separar `SELECT_BASE` como constante reutilizable en `PropiedadDAO` evitó reescribir el mismo JOIN cuatro veces (listado del agente, catálogo público, destacadas, ficha de detalle).

## ¿Qué no salió bien / problemas encontrados?

| Problema | Causa | Solución aplicada |
|---|---|---|
| Se implementó la galería de imágenes con URLs externas (Pexels) en vez de archivos reales | Se priorizó simplicidad de implementación sobre el principio del proyecto de ser una app "realmente funcional" | Jose objetó; se revisó el DML de Fase 1 (confirmó que ya se esperaban archivos locales) y se reescribió con `@MultipartConfig` + API `Part` |
| Tildes/ñ se guardaban corrompidas al guardar el perfil | Tomcat decodifica el body de un POST en ISO-8859-1 por defecto | `CodificacionFilter` global (`urlPatterns="/*"`) |
| `sprint2-planning.md` no coincidía en formato con el de Sprint 1 | Se redactó de memoria sin tener a la vista el archivo real | Comparación línea por línea contra el `.zip` del proyecto |
| Botones de acceso y destacados de la landing no llevaban a ninguna parte | Enlaces heredados de Sprint 1 apuntando a archivos que nunca existieron (`login.jsp`, `registro.jsp`, `detalle-propiedad`) | Corregidos uno por uno al tocar cada sección relacionada |
| No existía forma de marcar una propiedad como destacada sin pgAdmin | La columna `destacada` nunca se incluyó en el `INSERT`/`UPDATE` al construir el CRUD | Se agregó el checkbox y se completaron ambas sentencias SQL |

## ¿Qué mejorar para el próximo sprint?
- Antes de diseñar la persistencia de un dato nuevo (como las imágenes), revisar primero si el DML/DDL ya tomó una decisión al respecto, en vez de proponer una alternativa desde cero.
- Verificar explícitamente, al construir cada CRUD, que **todas** las columnas de la tabla (incluidas las "opcionales" como `destacada`) queden cubiertas por el formulario — no solo las que parecían obligatorias a primera vista.
- Seguir revisando los enlaces heredados de sprints anteriores al tocar una sección relacionada — varios bugs de Sprint 1 solo aparecieron al construir la funcionalidad real que los conectaba.

## Compromisos para Sprint 3
- Mantener la misma disciplina de protección IDOR en toda escritura nueva (citas, solicitudes, documentos, favoritos).
- Documentar desde el Planning cuáles de las 5 consultas SQL obligatorias del PDF se resuelven con cuál historia — varias ya quedaron construidas en Sprint 2 (el JOIN N:M de características, por ejemplo).
- Antes de dar por cerrada una historia, probar explícitamente el caso "esto debería poder hacerse sin tocar la base de datos a mano" — el principio de producto que guió varias correcciones este sprint.
