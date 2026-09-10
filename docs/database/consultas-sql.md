# Consultas SQL obligatorias

El PDF exige al menos 5 consultas: 2 con `INNER JOIN` entre 3+ tablas, 1 que resuelva una relación N:M, 1 con `LEFT JOIN`, y 1 de agregación con `GROUP BY`/`HAVING`. Las 5 ya están implementadas y en uso real dentro de la aplicación (no son consultas sueltas escritas solo para este documento) — aquí se documentan con su propósito y dónde viven en el código.

---

## 1. INNER JOIN (3+ tablas) — Ficha completa de una propiedad

**Dónde vive:** `PropiedadDAO.SELECT_BASE`, usada por `buscarPorId`, `listarPorInmobiliaria`, `buscarConFiltros`, `listarDestacadas` y `listarFavoritasDeUsuario`.

```sql
SELECT p.id_propiedad, p.titulo, p.precio, p.estado,
       c.nombre_ciudad, t.nombre_tipo, i.nombre_comercial, i.telefono_contacto
FROM propiedad p
JOIN ciudad c ON c.id_ciudad = p.id_ciudad
JOIN tipo_propiedad t ON t.id_tipo_propiedad = p.id_tipo_propiedad
JOIN inmobiliaria i ON i.id_inmobiliaria = p.id_inmobiliaria
WHERE p.id_propiedad = ?;
```

**Propósito:** una propiedad, por sí sola, no dice mucho — el nombre de la ciudad, el tipo y quién la publica viven en otras tablas. Esta consulta arma la "ficha completa" combinando 4 tablas en una sola ida a la base de datos.

---

## 2. INNER JOIN (3+ tablas) — Citas de una inmobiliaria

**Dónde vive:** `CitaDAO.listarPorInmobiliaria`.

```sql
SELECT c.id_cita, c.fecha_hora, c.estado,
       p.titulo AS titulo_propiedad, u.correo AS correo_cliente
FROM cita c
JOIN propiedad p ON p.id_propiedad = c.id_propiedad
JOIN usuario u ON u.id_usuario = c.id_cliente
WHERE p.id_inmobiliaria = ?
ORDER BY c.fecha_hora ASC;
```

**Propósito:** el agente necesita ver, en una sola tabla, qué propiedad, con qué cliente y en qué horario — sin este JOIN tendría que consultar tres veces por separado.

---

## 3. Relación muchos a muchos — Características de una propiedad

**Dónde vive:** `PropiedadCaracteristicaDAO.listarNombresPorPropiedad`.

```sql
SELECT c.nombre_caracteristica
FROM propiedad_caracteristica pc
JOIN caracteristica c ON c.id_caracteristica = pc.id_caracteristica
WHERE pc.id_propiedad = ?
ORDER BY c.nombre_caracteristica;
```

**Propósito:** `propiedad` y `caracteristica` se relacionan N:M a través de `propiedad_caracteristica` (llave primaria compuesta). Esta consulta resuelve esa relación para mostrar, en texto, qué características tiene una propiedad — se usa en la ficha pública de detalle.

---

## 4. LEFT JOIN — Propiedades sin ninguna cita agendada

**Dónde vive:** `PropiedadDAO.listarSinCitas`.

```sql
SELECT p.id_propiedad, p.titulo
FROM propiedad p
LEFT JOIN cita ci ON ci.id_propiedad = p.id_propiedad
WHERE p.id_inmobiliaria = ? AND ci.id_cita IS NULL
ORDER BY p.fecha_publicacion DESC;
```

**Propósito:** un `INNER JOIN` aquí sería un error — dejaría fuera exactamente las propiedades que se quieren encontrar (las que NO tienen citas). El `LEFT JOIN` conserva todas las propiedades de la inmobiliaria, y donde no hay cita relacionada, `ci.id_cita` llega como `NULL` — esa es la condición que filtra el `WHERE`.

---

## 5. Agregación con GROUP BY / HAVING — Reporte de propiedades por ciudad y estado

**Dónde vive:** `ReporteDAO.propiedadesPorCiudadYEstado`, usada en el panel de administrador (`/admin/reportes`).

```sql
SELECT c.nombre_ciudad, p.estado, COUNT(*) AS total
FROM propiedad p
JOIN ciudad c ON c.id_ciudad = p.id_ciudad
GROUP BY c.nombre_ciudad, p.estado
HAVING COUNT(*) > 0
ORDER BY c.nombre_ciudad, p.estado;
```

**Propósito:** el administrador no necesita ver cada propiedad una por una — necesita un resumen: cuántas propiedades hay en cada ciudad, separadas por estado. `GROUP BY` arma esos grupos y `COUNT(*)` los cuenta; `HAVING COUNT(*) > 0` se deja explícito para demostrar el uso de `HAVING`, aunque en la práctica un `GROUP BY` nunca produce grupos vacíos.
