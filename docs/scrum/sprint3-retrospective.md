# Sprint 3 — Retrospective
**Fecha de cierre:** 10/09/2026

## Qué salió bien

Los patrones que ya veníamos usando desde Sprints 1 y 2 —transacción manual, excepciones *checked* para los UNIQUE, protección IDOR, subida real de archivos— se reutilizaron sin fricción en las 6 historias de este sprint, aunque las metimos casi todas de una sola vez. La arquitectura que ya teníamos construida aguantó bien un lote grande de trabajo. La protección IDOR la resolvimos incluso en tablas que no tienen una columna de "dueño" directa (`cita`, `solicitud`) usando una subconsulta contra `propiedad`, en vez de desnormalizar el modelo agregando una columna redundante. Y revisar el DDL/DML reales antes de escribir código —la misma costumbre de los sprints anteriores— nos confirmó que este sprint no necesitaba ningún cambio de esquema: las 5 tablas ya existían completas desde la Fase 1.

## Qué no salió tan bien

| Problema | Por qué pasó | Cómo lo arreglamos |
|---|---|---|
| Los 3 tests fallaban con un `ClassNotFoundException` de un nombre de clase raro, con un `java.` metido al principio | La Test Package Folder de NetBeans estaba registrada como `test`, pero los archivos venían en `test/java/com/...` — NetBeans arma el nombre del paquete contando carpetas desde la raíz registrada, no desde el `package` que dice el archivo | Movimos los archivos a `test/com/...`, sin la carpeta `java` de más |
| `NoClassDefFoundError: org/hamcrest/SelfDescribing` al correr los tests | JUnit 4 depende de Hamcrest en tiempo de ejecución, y el asistente de NetBeans no siempre la agrega junto con JUnit | Agregamos la librería Hamcrest aparte, desde el mismo asistente |
| La carpeta de pruebas quedó duplicada en la configuración del proyecto | Doble clic sin querer en "Add Folder" | Eliminamos la fila repetida en Project Properties → Sources |

## Qué nos llevamos para más adelante

Cuando se usa un asistente de configuración del IDE (agregar librerías, carpetas de test), conviene revisar el resultado final antes de asumir que quedó bien — dos de los tres problemas de este sprint fueron de configuración, no de código. Cuando se entrega código en lote, como pasó con el `.zip` de las Historias 9 a 13, hay que ser explícito sobre la ruta exacta que tiene configurada el IDE, en vez de asumir una estructura "espejo" del código fuente — ahí nació justo el problema de `test/java/...` contra `test/...`. Y "compila sin errores" no es lo mismo que "corre sin errores" — vale la pena pedir siempre la segunda confirmación antes de dar algo por terminado, como nos pasó con las pruebas unitarias.

## Antes de la sustentación (en vez de compromisos para el siguiente sprint — este era el último)

Repasar, con el registro maestro como guía, el porqué de cada relación 1:1, 1:N y N:M del modelo — el profesor puede preguntar por cualquiera de las tres. Practicar cómo explicar la protección IDOR con un ejemplo concreto: por qué un agente no puede editar la propiedad de otro, ni aprobar la solicitud de otro. Y tener claro qué se decidió reforzar más allá del mínimo del backlog —imágenes reales en vez de URLs, el checkbox de destacada— porque son buenas respuestas para "¿qué le agregarías si tuvieras más tiempo?", ya que eso ya está hecho.
