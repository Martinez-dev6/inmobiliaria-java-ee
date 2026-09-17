# Sprint 2 — Review
**Fecha de cierre:** 08/09/2026

Las 3 historias planificadas quedaron completas, más la tarea técnica de los dashboards.

| Historia | Qué era | Evidencia |
|---|---|---|
| H5 | Perfil de usuario (1:1) | `PerfilController` / `PerfilDAO`, el upsert con `ON CONFLICT` verificado en la BD |
| H6 | CRUD de propiedades + imágenes (1:N) + características (N:M) | `PropiedadController`, subida real de archivos a `web/img/propiedades/`, `propiedad_caracteristica` verificada en pgAdmin |
| H7 | Buscador con filtros + destacadas reales | `CatalogoController`, `PropiedadDetalleController`, `LandingController` |

| Tarea técnica | Evidencia |
|---|---|
| Estrategia de imágenes | La cambiamos de URLs externas a subida real de archivo (el porqué está en la Retrospective) |
| Dashboards reales | `ClientePanelController`, `AgentePanelController`, `AdminPanelController` |

## Cómo lo probamos

En el perfil probamos completar y editar datos, validar el teléfono, y confirmar que las tildes y la ñ se guardan bien (habíamos tenido un problema de codificación). En propiedades: creamos con matrícula única y comprobamos que un duplicado se rechaza; editamos, dimos de baja y reactivamos, y probamos a propósito entrar con el `id` de una propiedad de otro agente por la URL para confirmar que no se puede tocar.

Las imágenes se suben de verdad — lo confirmamos mirando el archivo en `web/img/propiedades/` y la fila en pgAdmin — y se pueden eliminar de a una desde la galería. Las características quedan marcadas correctamente al volver a abrir el formulario de edición. El catálogo público filtra por ciudad, tipo y precio combinados, sin necesitar sesión, y la ficha de detalle redirige bien si la propiedad ya no existe o está dada de baja. La landing ya no tiene ninguna tarjeta fija — todo sale de la base de datos. Y los tres dashboards muestran estadísticas reales, incluyendo que se actualizan al dar de baja una propiedad.

## Cosas que agregamos sin que nos las pidieran

Un filtro global de codificación UTF-8 (lo necesitamos al probar el perfil con tildes). Una transición con fade en la navegación, solo por UX. Un checkbox de "Destacada" en el formulario del agente, para no tener que hacer `UPDATE` a mano en pgAdmin. Y de paso corregimos varios enlaces rotos que venían de Sprint 1 (`login.jsp`, `registro.jsp`, `detalle-propiedad`).

## ¿Nos desviamos del Planning?

Sí, en dos cosas. La estrategia de imágenes cambió de URLs externas a subida real de archivo — está explicado en la Retrospective. Y la ficha de detalle de propiedad no estaba como historia separada en el Planning, pero terminó saliendo como parte natural de la Historia 7, porque el PDF la pide dentro de "Gestión de propiedades".
