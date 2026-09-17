# Sprint 2 — Planning
## Sistema Web de Inmobiliaria — Programación Java (UTS)

**Duración:** 7 días
**Sprint:** 2 de 3 — "Núcleo del negocio"
**Roles Scrum:** Product Owner = Docente | Scrum Master + Development Team = Nosotros

---

## Meta del sprint

Que el catálogo deje de ser una maqueta y se vuelva un módulo real: CRUD completo de propiedades con imágenes (1:N) y características (N:M), buscador con filtros contra datos reales, perfil de usuario (1:1) completo, y dashboards de verdad que reemplacen los placeholders que dejamos en Sprint 1.

---

## Historias que abordamos

| # | Historia | Prioridad | Estimación |
|---|---|---|---|
| 5 | Como cliente, quiero completar mi perfil con documento, teléfono y dirección para agilizar mis trámites. | Media | 1 día |
| 6 | Como agente de la inmobiliaria, quiero registrar y editar propiedades con fotos, características y precio. | Alta | 3 días |
| 7 | Como cliente, quiero buscar y filtrar propiedades por ciudad, tipo, precio y características. | Alta | 1.5 días |

Aparte de esas tres, metimos una tarea técnica que no viene numerada en el backlog: cambiar los `panel.jsp` temporales por dashboards reales. No es opcional — el PDF pide "paneles diferenciados" como módulo obligatorio, y lo que teníamos hasta ahora eran solo placeholders de Sprint 1.

---

## Antes de programar

A diferencia de Sprint 1, aquí no hace falta una fase aparte de diseño de base de datos — el modelo, el DDL y el DML ya quedaron listos en la Fase 1. Lo único que hace falta confirmar es la estrategia de imágenes y que los catálogos de `ciudad` y `tipo_propiedad` tengan datos suficientes. El modelo y los DAO de `Propiedad`, `Ciudad` y `TipoPropiedad` los construimos como parte de la Historia 6, no como algo aparte.

| Tarea | Estimación |
|---|---|
| Confirmar datos de prueba y definir cómo se van a guardar las imágenes | va dentro del Día 1 |
| Reemplazar los `panel.jsp` por dashboards reales | 1.5 días |

---

## Cómo repartimos los 7 días

| Día | Actividad |
|---|---|
| 1 | Estrategia de imágenes + catálogos + Historia 5 |
| 2 | Historia 6 — modelo y DAO de Propiedad/Ciudad/TipoPropiedad + CRUD base |
| 3 | Historia 6 — galería de imágenes (1:N) |
| 4 | Historia 6 — características (N:M) |
| 5–6 | Historia 7 — buscador con filtros + destacadas conectadas a BD |
| 7 | Dashboards reales + Sprint Review |

---

## Cuándo damos por hecha cada historia

**H5 — Perfil (1:1)**
El usuario ve y edita nombres, apellidos, documento, teléfono, dirección y foto. Se guarda en `perfil`, ligada 1:1 a `usuario` con `perfil.id_usuario` como UNIQUE. Si no existe perfil se crea, si ya existe se actualiza — nunca se duplica.

**H6 — Propiedades (CRUD + imágenes + características)**
Crear con matrícula única (el duplicado se captura con mensaje claro, no un stacktrace), editar, dar de baja lógicamente (nunca borrar de verdad), asociar imágenes vía `imagen_propiedad` y marcar características vía `propiedad_caracteristica`. Todo con validación de servidor en precio, fechas y campos obligatorios.

**H7 — Buscador con filtros**
El catálogo filtra por ciudad, tipo y rango de precio contra la BD real. El buscador rápido de la landing manda al catálogo ya con esos filtros aplicados, y las propiedades destacadas ya no son datos fijos sino una consulta real.

**Dashboards reales**
Cliente, agente y admin dejan de ver un placeholder y pasan a ver información real de su rol, sin romper el control de acceso que ya teníamos.

---

## Lo que dejamos para Sprint 3

Citas, solicitudes, documentos, favoritos y reportes con agregación. El chat de contacto y las notificaciones por correo siguen como extra opcional, se ven si sobra tiempo.
