-- ============================================================
-- Script DML — Datos de prueba
-- Sistema Web de Inmobiliaria — Programación Java UTS
-- Contraseña de prueba para TODOS los usuarios: Prueba123!
-- (hash BCrypt, 12 rounds — nunca texto plano)
-- ============================================================

-- ===== BLOQUE A =====

-- Roles (catálogo fijo, según el sistema: 3 roles)
INSERT INTO rol (nombre_rol) VALUES
('Cliente'),
('Inmobiliaria'),
('Administrador');

-- Usuarios: 8 clientes, 3 agentes, 1 admin = 12 usuarios
INSERT INTO usuario (correo, contrasena_hash, activo, fecha_registro) VALUES
('cliente1@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-05 09:00:00'),
('cliente2@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-06 10:15:00'),
('cliente3@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-07 11:30:00'),
('cliente4@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-08 08:45:00'),
('cliente5@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-09 14:20:00'),
('cliente6@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-10 16:00:00'),
('cliente7@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', FALSE, '2026-01-11 09:10:00'),
('cliente8@correo.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-12 13:25:00'),
('agente1@inmobiliaria.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-03 08:00:00'),
('agente2@inmobiliaria.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-04 09:30:00'),
('agente3@inmobiliaria.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-04 10:00:00'),
('admin@inmobiliaria.com', '$2a$12$lbTR2uvoA7dX8AUPQTf6re11MzcLMGUpj..7yL8HpNd32bbMjGk5G', TRUE, '2026-01-01 07:00:00');

-- Perfiles (1:1 con usuario — mismo id_usuario que en la tabla anterior)
INSERT INTO perfil (id_usuario, nombres, apellidos, documento, telefono, direccion, foto_url) VALUES
(1,  'Laura',    'Gómez',    '1098765432', '3101234567', 'Cra 27 #45-12, Bucaramanga', NULL),
(2,  'Andrés',   'Ramírez',  '1098765433', '3102234567', 'Calle 56 #12-30, Floridablanca', NULL),
(3,  'Camila',   'Torres',   '1098765434', '3103234567', 'Cra 15 #20-45, Girón', NULL),
(4,  'Julián',   'Suárez',   '1098765435', '3104234567', 'Calle 33 #8-19, Bucaramanga', NULL),
(5,  'Valentina','Rojas',    '1098765436', '3105234567', 'Cra 9 #14-22, Piedecuesta', NULL),
(6,  'Sebastián','Moreno',   '1098765437', '3106234567', 'Calle 45 #17-08, Bucaramanga', NULL),
(7,  'Daniela',  'Castro',   '1098765438', '3107234567', 'Cra 22 #30-11, Floridablanca', NULL),
(8,  'Felipe',   'Herrera',  '1098765439', '3108234567', 'Calle 10 #5-60, Girón', NULL),
(9,  'Mónica',   'Vargas',   '1098765440', '3109234567', 'Cra 33 #40-05, Bucaramanga', NULL),
(10, 'Ricardo',  'Peña',     '1098765441', '3110234567', 'Calle 60 #25-14, Bucaramanga', NULL),
(11, 'Paola',    'Jiménez',  '1098765442', '3111234567', 'Cra 18 #33-09, Floridablanca', NULL),
(12, 'Admin',    'Sistema',  '1098765443', '3000000000', 'Oficina Central, Bucaramanga', NULL);

-- Inmobiliarias (1:1 con usuario — solo los 3 usuarios con rol Inmobiliaria: id 9, 10, 11)
INSERT INTO inmobiliaria (id_usuario, nombre_comercial, nit, telefono_contacto) VALUES
(9,  'Vargas Bienes Raíces',    '901234567-1', '6076001122'),
(10, 'Peña Propiedades S.A.S.', '901234568-2', '6076002233'),
(11, 'Jiménez Inmobiliaria',    '901234569-3', '6076003344');

-- Asignación de roles (usuario_rol)
INSERT INTO usuario_rol (id_usuario, id_rol, fecha_asignacion) VALUES
(1, 1, '2026-01-05 09:00:00'),
(2, 1, '2026-01-06 10:15:00'),
(3, 1, '2026-01-07 11:30:00'),
(4, 1, '2026-01-08 08:45:00'),
(5, 1, '2026-01-09 14:20:00'),
(6, 1, '2026-01-10 16:00:00'),
(7, 1, '2026-01-11 09:10:00'),
(8, 1, '2026-01-12 13:25:00'),
(9, 2, '2026-01-03 08:00:00'),
(10, 2, '2026-01-04 09:30:00'),
(11, 2, '2026-01-04 10:00:00'),
(12, 3, '2026-01-01 07:00:00');

-- ===== BLOQUE B =====

-- Ciudades (variedad razonable para reportes por ciudad)
INSERT INTO ciudad (nombre_ciudad) VALUES
('Bucaramanga'),
('Floridablanca'),
('Girón'),
('Piedecuesta'),
('Bogotá'),
('Medellín');

-- Tipos de propiedad (exactamente los 5 que menciona el PDF)
INSERT INTO tipo_propiedad (nombre_tipo) VALUES
('Casa'),
('Apartamento'),
('Local'),
('Oficina'),
('Terreno');

-- Características (catálogo variado)
INSERT INTO caracteristica (nombre_caracteristica) VALUES
('Piscina'),
('Parqueadero'),
('Ascensor'),
('Gimnasio'),
('Balcón'),
('Jardín'),
('Seguridad 24h');

-- Propiedades: 12 registros (cumple el mínimo de 10 para tabla principal)
-- id_inmobiliaria: 1=Vargas, 2=Peña, 3=Jiménez
-- id_ciudad: 1=Bucaramanga, 2=Floridablanca, 3=Girón, 4=Piedecuesta, 5=Bogotá, 6=Medellín
-- id_tipo_propiedad: 1=Casa, 2=Apartamento, 3=Local, 4=Oficina, 5=Terreno
INSERT INTO propiedad (id_inmobiliaria, id_ciudad, id_tipo_propiedad, matricula_inmobiliaria, titulo, descripcion, direccion, precio, area_m2, estado, destacada, fecha_publicacion) VALUES
(1, 1, 1, 'MAT-0001', 'Casa campestre en Cabecera', 'Amplia casa con jardín y zona social.', 'Cra 30 #45-10, Bucaramanga', 450000000, 220, 'disponible', TRUE,  '2026-01-15 10:00:00'),
(1, 1, 2, 'MAT-0002', 'Apartamento moderno Cabecera', 'Apartamento remodelado, excelente iluminación.', 'Calle 48 #29-15, Bucaramanga', 320000000, 85,  'disponible', TRUE,  '2026-01-16 11:00:00'),
(1, 2, 2, 'MAT-0003', 'Apartaestudio Floridablanca', 'Ideal para estudiantes o parejas jóvenes.', 'Cra 10 #6-20, Floridablanca', 180000000, 45,  'disponible', FALSE, '2026-01-17 09:30:00'),
(2, 1, 3, 'MAT-0004', 'Local comercial Centro', 'Local esquinero, alto flujo peatonal.', 'Calle 35 #18-40, Bucaramanga', 250000000, 60,  'disponible', TRUE,  '2026-01-18 14:00:00'),
(2, 3, 1, 'MAT-0005', 'Casa familiar en Girón', 'Casa de dos plantas, patio amplio.', 'Cra 25 #10-05, Girón', 380000000, 180, 'disponible', FALSE, '2026-01-19 08:00:00'),
(2, 4, 4, 'MAT-0006', 'Oficina ejecutiva Piedecuesta', 'Oficina lista para operar, incluye mobiliario.', 'Cra 14 #12-30, Piedecuesta', 210000000, 50,  'disponible', FALSE, '2026-01-20 10:15:00'),
(3, 1, 2, 'MAT-0007', 'Apartamento vista panorámica', 'Piso alto, vista a la ciudad.', 'Calle 52 #33-20, Bucaramanga', 410000000, 95,  'disponible', TRUE,  '2026-01-21 09:00:00'),
(3, 2, 5, 'MAT-0008', 'Lote urbanizable Floridablanca', 'Terreno plano, apto para construcción.', 'Vía Floridablanca km 3', 150000000, 500, 'disponible', FALSE, '2026-01-22 13:20:00'),
(3, 5, 2, 'MAT-0009', 'Apartamento en Chapinero', 'Cerca a zona universitaria y comercial.', 'Cra 13 #55-30, Bogotá', 520000000, 78,  'disponible', TRUE,  '2026-01-23 11:45:00'),
(1, 6, 1, 'MAT-0010', 'Casa en El Poblado', 'Sector exclusivo, acabados de lujo.', 'Cra 43A #20-15, Medellín', 890000000, 260, 'disponible', TRUE,  '2026-01-24 16:00:00'),
(2, 1, 3, 'MAT-0011', 'Local en centro comercial', 'Local dentro de centro comercial reconocido.', 'CC Cañaveral, Floridablanca', 300000000, 70,  'arrendada', FALSE, '2026-01-25 10:30:00'),
(1, 3, 1, 'MAT-0012', 'Casa esquinera Girón', 'Excelente para negocio en primer piso.', 'Cra 28 #15-40, Girón', 340000000, 150, 'inactiva',  FALSE, '2026-01-26 12:00:00');

-- Imágenes por propiedad (1:N — al menos 2 por propiedad, ejemplo con las primeras)
INSERT INTO imagen_propiedad (id_propiedad, url_imagen, orden) VALUES
(1, '/img/propiedades/mat0001_1.jpg', 1),
(1, '/img/propiedades/mat0001_2.jpg', 2),
(2, '/img/propiedades/mat0002_1.jpg', 1),
(3, '/img/propiedades/mat0003_1.jpg', 1),
(4, '/img/propiedades/mat0004_1.jpg', 1),
(5, '/img/propiedades/mat0005_1.jpg', 1),
(6, '/img/propiedades/mat0006_1.jpg', 1),
(7, '/img/propiedades/mat0007_1.jpg', 1),
(8, '/img/propiedades/mat0008_1.jpg', 1),
(9, '/img/propiedades/mat0009_1.jpg', 1),
(10, '/img/propiedades/mat0010_1.jpg', 1),
(11, '/img/propiedades/mat0011_1.jpg', 1),
(12, '/img/propiedades/mat0012_1.jpg', 1);

-- Características por propiedad (N:M — combinaciones variadas)
INSERT INTO propiedad_caracteristica (id_propiedad, id_caracteristica) VALUES
(1, 1), (1, 2), (1, 6),
(2, 2), (2, 3), (2, 4),
(3, 2), (3, 7),
(4, 2), (4, 7),
(5, 1), (5, 2), (5, 6),
(6, 2), (6, 3),
(7, 2), (7, 3), (7, 4),
(8, 6),
(9, 3), (9, 4), (9, 7),
(10, 1), (10, 2), (10, 4), (10, 7),
(11, 2), (11, 7),
(12, 2), (12, 6);


-- ===== BLOQUE C =====

-- Citas: 12 registros (tabla principal, cumple mínimo 10)
-- id_cliente: 1 a 8 (los usuarios con rol Cliente)
INSERT INTO cita (id_propiedad, id_cliente, fecha_hora, estado) VALUES
(1, 1, '2026-02-01 09:00:00', 'confirmada'),
(2, 2, '2026-02-01 10:30:00', 'pendiente'),
(3, 3, '2026-02-02 14:00:00', 'confirmada'),
(4, 4, '2026-02-02 15:30:00', 'cancelada'),
(5, 5, '2026-02-03 09:00:00', 'realizada'),
(6, 6, '2026-02-03 11:00:00', 'pendiente'),
(7, 1, '2026-02-04 16:00:00', 'confirmada'),
(8, 2, '2026-02-05 10:00:00', 'pendiente'),
(9, 3, '2026-02-05 13:00:00', 'realizada'),
(10, 7, '2026-02-06 09:30:00', 'confirmada'),
(1, 8, '2026-02-06 14:00:00', 'pendiente'),
(2, 4, '2026-02-07 11:00:00', 'confirmada');

-- Solicitudes: 11 registros (tabla principal, cumple mínimo 10)
INSERT INTO solicitud (id_propiedad, id_cliente, tipo_solicitud, estado, fecha_solicitud, observaciones) VALUES
(1, 1, 'compra',   'pendiente',  '2026-02-08 09:00:00', NULL),
(2, 2, 'arriendo', 'aprobada',   '2026-02-08 10:00:00', 'Documentación completa, aprobado.'),
(3, 3, 'arriendo', 'pendiente',  '2026-02-09 11:00:00', NULL),
(4, 4, 'compra',   'rechazada',  '2026-02-09 12:00:00', 'No cumple con requisitos financieros.'),
(5, 5, 'compra',   'aprobada',   '2026-02-10 08:30:00', 'Solicitud aprobada, en trámite notarial.'),
(6, 6, 'arriendo', 'pendiente',  '2026-02-10 09:15:00', NULL),
(7, 1, 'arriendo', 'aprobada',   '2026-02-11 10:00:00', 'Contrato firmado.'),
(9, 3, 'compra',   'pendiente',  '2026-02-11 14:00:00', NULL),
(10, 7, 'arriendo', 'rechazada', '2026-02-12 09:00:00', 'Propiedad ya comprometida.'),
(2, 8, 'compra',   'pendiente',  '2026-02-12 15:00:00', NULL),
(7, 4, 'arriendo', 'pendiente',  '2026-02-13 08:00:00', NULL);

-- Documentos de solicitud (1:N — al menos 1-2 por solicitud con estado avanzado)
INSERT INTO documento_solicitud (id_solicitud, tipo_documento, url_archivo, fecha_carga) VALUES
(2, 'cedula', '/docs/solicitudes/sol0002_cedula.pdf', '2026-02-08 10:05:00'),
(2, 'comprobante_ingresos', '/docs/solicitudes/sol0002_ingresos.pdf', '2026-02-08 10:06:00'),
(5, 'cedula', '/docs/solicitudes/sol0005_cedula.pdf', '2026-02-10 08:35:00'),
(5, 'comprobante_ingresos', '/docs/solicitudes/sol0005_ingresos.pdf', '2026-02-10 08:36:00'),
(7, 'cedula', '/docs/solicitudes/sol0007_cedula.pdf', '2026-02-11 10:05:00'),
(4, 'cedula', '/docs/solicitudes/sol0004_cedula.pdf', '2026-02-09 12:05:00');

-- Favoritos (N:M — combinaciones variadas de clientes y propiedades)
INSERT INTO favorito (id_usuario, id_propiedad, fecha_marcado) VALUES
(1, 2, '2026-01-20 10:00:00'),
(1, 5, '2026-01-21 11:00:00'),
(2, 1, '2026-01-20 09:30:00'),
(3, 7, '2026-01-22 14:00:00'),
(4, 9, '2026-01-23 15:00:00'),
(5, 1, '2026-01-24 08:00:00'),
(6, 3, '2026-01-25 09:00:00'),
(7, 10, '2026-01-26 10:00:00'),
(8, 2, '2026-01-27 11:00:00');

-- Auditoría: 12 registros (eventos simulados)
INSERT INTO auditoria (id_usuario, accion, descripcion, fecha_evento) VALUES
(1, 'login', 'Inicio de sesión exitoso', '2026-02-01 08:55:00'),
(2, 'login', 'Inicio de sesión exitoso', '2026-02-01 10:25:00'),
(9, 'creacion_propiedad', 'Publicó la propiedad MAT-0001', '2026-01-15 10:00:00'),
(9, 'creacion_propiedad', 'Publicó la propiedad MAT-0002', '2026-01-16 11:00:00'),
(10, 'creacion_propiedad', 'Publicó la propiedad MAT-0004', '2026-01-18 14:00:00'),
(12, 'cambio_rol', 'Asignó rol Inmobiliaria al usuario 9', '2026-01-03 08:05:00'),
(12, 'cambio_rol', 'Asignó rol Inmobiliaria al usuario 10', '2026-01-04 09:35:00'),
(3, 'login', 'Inicio de sesión exitoso', '2026-02-02 13:55:00'),
(9, 'aprobacion_solicitud', 'Aprobó la solicitud #2', '2026-02-08 10:00:00'),
(10, 'aprobacion_solicitud', 'Aprobó la solicitud #5', '2026-02-10 08:30:00'),
(4, 'login', 'Inicio de sesión exitoso', '2026-02-02 15:25:00'),
(12, 'consulta_auditoria', 'Consultó el reporte de auditoría del sistema', '2026-02-13 09:00:00');
