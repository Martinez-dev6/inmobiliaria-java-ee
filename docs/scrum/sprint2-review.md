# Sprint 2 — Review
**Fecha de cierre:** 08/09/2026

## Historias completadas (3 de 3 planificadas)

| Historia | Descripción | Estado | Evidencia |
|---|---|---|---|
| H5 | Perfil de usuario (1:1) | ✅ Completa | `PerfilController`/`PerfilDAO`, upsert `ON CONFLICT` verificado en BD |
| H6 | Gestión de propiedades: CRUD + imágenes (1:N) + características (N:M) | ✅ Completa | `PropiedadController`, subida real de archivos en `web/img/propiedades/`, `propiedad_caracteristica` verificada en pgAdmin |
| H7 | Buscador con filtros + destacados reales | ✅ Completa | `CatalogoController`, `PropiedadDetalleController`, `LandingController` |

## Tareas técnicas completadas

| Tarea | Estado | Evidencia |
|---|---|---|
| Definir estrategia de imágenes de propiedad | ✅ Completa | Revertida de URLs externas a subida real de archivo (ver Retrospective) |
| Dashboards reales por rol | ✅ Completa | `ClientePanelController`, `AgentePanelController`, `AdminPanelController` |

## Demostración funcional realizada
- Perfil: completar/editar datos personales, validación de teléfono, corrección de codificación UTF-8 verificada con texto en español (tildes/ñ).
- Propiedades: creación con matrícula única (rechazo de duplicados verificado), edición, baja lógica y reactivación, todo restringido al dueño real (probado intentando editar con el `id` de otro agente vía URL).
- Imágenes: subida real de archivos, galería con eliminación individual, verificado en disco (`web/img/propiedades/`) y en pgAdmin.
- Características: checkboxes precargados correctamente al editar, verificados contra `propiedad_caracteristica` en pgAdmin.
- Catálogo público: filtros combinados por ciudad/tipo/precio, sin necesitar sesión.
- Ficha de detalle: galería, características, contacto de la inmobiliaria; redirección verificada al intentar acceder a una propiedad dada de baja o inexistente.
- Landing: destacados y buscador rápido conectados a datos reales, sin ninguna tarjeta estática restante.
- Dashboards: estadísticas verificadas para los tres roles, incluyendo actualización en vivo al dar de baja una propiedad.

## Funcionalidad adicional agregada (no exigida explícitamente, pero derivada de las historias)
- `CodificacionFilter` global (UTF-8), descubierto necesario al probar Historia 5.
- Transición fade en la navegación (mejora de UX, no exigida por el profesor).
- Checkbox "Destacada" en el formulario del agente, para no depender de `UPDATE` manual en pgAdmin.
- Corrección de varios enlaces rotos heredados de Sprint 1 (`login.jsp`/`registro.jsp`, `index.jsp`, `detalle-propiedad`).

## Desviaciones respecto al Planning
- La estrategia de imágenes cambió de URLs externas (como se planteó inicialmente) a subida real de archivos — ver Retrospective para el detalle de por qué.
- La ficha de detalle de propiedad no estaba en el Planning como historia separada, pero se construyó como parte natural de la Historia 7 (el PDF la exige dentro de "Gestión de propiedades").