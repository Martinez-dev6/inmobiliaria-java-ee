# Sprint 2 — Planning
## Sistema Web de Inmobiliaria — Programación Java (UTS)

**Duración:** 7 días
**Sprint:** 2 de 3 — "Núcleo del negocio"
**Roles Scrum:** Product Owner = Docente | Scrum Master + Development Team = Nosotros

---

## Sprint Goal

Convertir el catálogo de propiedades en un módulo real conectado a la base
de datos: CRUD completo de propiedades con imágenes (1:N) y características
(N:M), buscador con filtros funcionando contra datos reales, perfil de
usuario (1:1) completo, y paneles (dashboards) reales diferenciados por rol
que reemplacen los placeholders de Sprint 1.

---

## Historias de usuario abordadas

| # | Historia | Prioridad (backlog) | Estimación |
|---|---|---|---|
| 5 | Como cliente, quiero completar mi perfil con documento, teléfono y dirección asociados a mi cuenta para agilizar mis trámites. | Media | 1 día |
| 6 | Como agente de la inmobiliaria, quiero registrar y editar propiedades con fotos, características y precio para mantener el catálogo actualizado. | Alta | 3 días |
| 7 | Como cliente, quiero buscar y filtrar propiedades por ciudad, tipo, precio y características para encontrar las opciones que se ajusten a mis necesidades. | Alta | 1.5 días |

**Nota:** además de las tres historias del backlog, este sprint incluye una
tarea técnica no numerada — reemplazar los `panel.jsp` temporales por
dashboards reales — porque el PDF exige "paneles diferenciados" como
módulo obligatorio y los actuales son solo placeholders de Sprint 1.

---

## Tareas técnicas (prerrequisito obligatorio antes de programar)

| Tarea | Estimación |
|---|---|
| Confirmar datos de prueba en `ciudad`/`tipo_propiedad` y definir estrategia de almacenamiento de imágenes | Incluida en Día 1, sin estimación propia |
| Reemplazar `panel.jsp` temporales por dashboards reales por rol | 1.5 días |

**Nota:** a diferencia de Sprint 1, no hace falta una fase separada de
diseño de base de datos — el modelo, el DDL y el DML ya se completaron en
la Fase 1. El modelo y DAO de `Propiedad`, `Ciudad` y `TipoPropiedad` se
construyen como parte del trabajo de la Historia 6, no como tarea aparte.

---

## Cronograma (7 días)

| Día | Actividad |
|---|---|
| 1 | Definir estrategia de imágenes + confirmar catálogos + Historia 5 — Perfil de usuario |
| 2 | Historia 6 — Modelo y DAO de `Propiedad`/`Ciudad`/`TipoPropiedad` + CRUD base |
| 3 | Historia 6 — Galería de imágenes (1:N) |
| 4 | Historia 6 — Características de propiedad (N:M) |
| 5–6 | Historia 7 — Buscador con filtros + landing destacadas conectada a BD |
| 7 | Dashboards reales por rol + Sprint Review |

---

## Criterios de aceptación (Definition of Done)

**H5 — Perfil de usuario (1:1)**
- [ ] El usuario autenticado puede ver y editar su perfil (nombres, apellidos, documento, teléfono, dirección, foto)
- [ ] Los datos se guardan en la tabla `perfil`, ligada 1:1 a `usuario` mediante `perfil.id_usuario` UNIQUE
- [ ] Validación de campos obligatorios y formatos (teléfono, documento)
- [ ] Si el perfil no existe aún se crea; si ya existe se actualiza (no se duplica)

**H6 — Gestión de propiedades (CRUD + imágenes + características)**
- [ ] Crear propiedad con matrícula inmobiliaria única; captura el error de duplicado con mensaje claro (no stacktrace de Java)
- [ ] Editar una propiedad existente
- [ ] Dar de baja lógica una propiedad (cambio de `estado`, no eliminación física)
- [ ] Asociar una o varias imágenes a la propiedad (relación 1:N vía `imagen_propiedad`)
- [ ] Marcar una o varias características para la propiedad (relación N:M vía `propiedad_caracteristica`)
- [ ] Validación en servidor de campos obligatorios y formatos (precio, fechas)

**H7 — Buscador con filtros**
- [ ] El catálogo filtra por ciudad, tipo de propiedad y rango de precio contra datos reales de la BD
- [ ] El buscador rápido de la landing redirige al catálogo aplicando los filtros seleccionados
- [ ] Las propiedades destacadas de la landing se obtienen mediante consulta real (ya no son datos estáticos)

**Tarea técnica — Dashboards reales por rol**
- [ ] Se reemplazan los `panel.jsp` temporales de cliente, agente y administrador
- [ ] Cada dashboard muestra información real y relevante para su rol
- [ ] Se respeta el control de acceso ya implementado (`AccesoFilter`)

---

## Fuera de alcance en este sprint (explícitamente pospuesto)

- Citas, solicitudes, documentos, favoritos, reportes con agregación → Sprint 3
- Chat de contacto, notificaciones por correo → Backlog extra (Sprint 3 o descartable si falta tiempo)