# Sprint 3 — Retrospective
**Fecha de cierre:** 10/09/2026

## ¿Qué salió bien?
- Los patrones ya establecidos en Sprints 1-2 (transacción manual, checked exceptions para UNIQUE, protección IDOR, subida real de archivos) se reutilizaron sin fricción en 6 historias nuevas de una sola vez — la arquitectura ya construida escaló bien a un lote grande de trabajo.
- La protección IDOR se resolvió incluso en tablas sin columna de "dueño" directa (`cita`, `solicitud`), usando una subconsulta contra `propiedad` en vez de desnormalizar el modelo agregando una columna redundante.
- Verificar el DDL/DML reales antes de escribir código (mismo hábito de sprints anteriores) confirmó que Sprint 3 no necesitaba ningún cambio de esquema — las 5 tablas ya existían completas desde la Fase 1.

## ¿Qué no salió bien / problemas encontrados?

| Problema | Causa | Solución aplicada |
|---|---|---|
| Los 3 tests fallaban con `ClassNotFoundException` de un nombre de clase con un `java.` inventado al principio | La Test Package Folder de NetBeans estaba registrada como `test`, pero los archivos venían en `test/java/com/...` (espejo de la estructura de `src/java/...`) — NetBeans arma el paquete contando carpetas desde la raíz registrada, no desde el `package` del archivo | Mover los archivos a `test/com/...`, sin la carpeta `java` intermedia |
| `NoClassDefFoundError: org/hamcrest/SelfDescribing` al correr los tests | JUnit 4 depende de Hamcrest en tiempo de ejecución; el asistente de NetBeans no siempre la agrega junto con JUnit | Agregar la librería "Hamcrest" por separado desde el mismo asistente |
| Carpeta de pruebas quedó duplicada en la configuración del proyecto | Doble clic accidental en "Add Folder" | Eliminar la fila repetida en Project Properties → Sources |

## ¿Qué mejorar para futuros proyectos?
- Al usar un asistente de configuración de IDE (agregar librerías, carpetas de test), verificar el resultado final antes de asumir que quedó bien — dos de los tres problemas de este sprint fueron de configuración del entorno, no de código.
- Cuando se entregue código en lote (como el `.zip` de las Historias 9-13), ser explícito sobre la ruta EXACTA que el IDE tiene configurada, en vez de asumir una estructura de carpetas "espejo" del código fuente — ahí nació el problema de `test/java/...` vs `test/...`.
- "Compila sin errores" y "corre sin errores" son dos confirmaciones distintas — vale la pena pedir siempre la segunda antes de dar algo por completado, como pasó aquí con las pruebas unitarias.

## Preparación para la sustentación (en vez de "compromisos para el siguiente sprint" — este era el último)
- Repasar, con el registro maestro como guía, el porqué de cada relación 1:1, 1:N y N:M del modelo — el profesor puede preguntar por cualquiera de las tres.
- Practicar explicar la protección IDOR con un ejemplo concreto (por qué un agente no puede editar la propiedad de otro, ni aprobar la solicitud de otro).
- Tener claro qué se decidió reforzar más allá del mínimo del backlog (imágenes reales en vez de URLs, checkbox de destacada) y por qué — son buenas respuestas para "¿qué le agregarías si tuvieras más tiempo?" porque ya están hechas.
