# Sprint 1 — Retrospective
**Fecha de cierre:** 07/09/2026

## Qué salió bien

Mantener la arquitectura en capas (modelo / DAO / controlador / vista) desde el principio nos ahorró tiempo en la Historia 4, porque reutilizamos el mismo `UsuarioDAO` que ya teníamos de registro y login. Las transacciones manuales (`setAutoCommit(false)`) en el registro también valieron la pena: probamos a propósito provocar un error a mitad del proceso y no quedó ningún registro huérfano. Y comitear seguido, con mensajes que dijeran qué se hizo, nos salvó un par de veces cuando tocó revertir algo.

## Qué no salió tan bien

| Problema | Por qué pasó | Cómo lo arreglamos |
|---|---|---|
| `git add "Web Pages/..."` no funcionaba | Confundimos el nombre que muestra NetBeans ("Web Pages") con la carpeta real en disco (`web/`) | Desde entonces, siempre revisamos `git status` antes de un `add` |
| Aparecieron archivos sueltos llamados `git` y `master` en la raíz | Pegamos un comando que traía el prompt de la terminal incluido, y eso redirigió (`>`) hacia un archivo | Escribir el comando directo en la terminal, no pegar bloques completos |
| El registro fallaba con "No se pudo completar el registro" | El DAO buscaba el rol como `'cliente'` en minúscula, pero el DML lo sembró como `'Cliente'` — y PostgreSQL sí distingue mayúsculas en VARCHAR | Corregimos la constante al valor exacto del catálogo |
| Los paneles de login/registro se veían montados uno sobre el otro | Al adaptar un diseño de referencia nos faltó ocultar la cara inactiva del panel | Agregamos `opacity` / `pointer-events` según el estado |
| El botón "Cerrar sesión" daba 404 dentro de `/admin/` | La ruta relativa (`href="logout"`) se resolvía distinto según en qué carpeta estuviera el JSP | Usar siempre `${pageContext.request.contextPath}/...` para rutas absolutas |

## Para el próximo sprint

Antes de escribir código que dependa de un archivo existente (un CSS, un layout), revisarlo primero en vez de asumir qué tiene. Dejar siempre `e.printStackTrace()` o algo parecido en los `catch` que conviertan errores técnicos en mensajes de usuario, para no perder el detalle real si algo falla. Y usar `${pageContext.request.contextPath}/...` en toda ruta interna desde ya, antes de que Sprint 2 meta más carpetas por rol y el problema se multiplique.
