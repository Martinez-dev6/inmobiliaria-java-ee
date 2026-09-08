# Sprint 1 — Review
**Fecha de cierre:** 07/09/2026

## Historias completadas (4 de 4 planificadas)

| Historia | Descripción | Estado | Evidencia |
|---|---|---|---|
| H1 | Landing page responsiva | ✅ Completa | `web/index.jsp`, commit `9b213ed` |
| H2 | Registro con correo único y contraseña cifrada | ✅ Completa | `RegistroController`, hash BCrypt verificado en BD |
| H3 | Login/logout con sesión por rol | ✅ Completa | `LoginController`/`LogoutController`, 5 casos probados |
| H4 | Admin asigna/revoca roles | ✅ Completa | `GestionRolesController`, con registro en tabla `auditoria` |

## Demostración funcional realizada
- Registro de un usuario nuevo → hash BCrypt verificado en pgAdmin (`$2a$...`), rol `Cliente` asignado automáticamente.
- Intento de registrar el mismo correo dos veces → mensaje "El correo ya se encuentra registrado" (sin excepción de Java expuesta al usuario).
- Login con usuario de cada rol (Cliente, Inmobiliaria, Administrador) → redirección automática al panel correspondiente.
- Login con cuenta inactiva (`activo = false`) → acceso rechazado.
- Cierre de sesión → verificado con `session.invalidate()`, confirmado que la sesión queda vacía al reintentar acceder por URL directa.
- Intento de acceso a `/admin/panel.jsp` sin sesión, y con sesión de rol incorrecto → ambos rechazados por `AccesoFilter`, redirigidos a `acceso-denegado.jsp`.
- Panel de administrador: asignación y revocación de roles en vivo, con bloqueo al intentar dejar a un usuario sin ningún rol.

## Funcionalidad adicional agregada (no exigida explícitamente, pero derivada de las historias)
- Tabla `usuario_rol` con normalización de correo a minúsculas antes de guardar/consultar (evita duplicados por diferencia de mayúsculas).
- Registro automático en tabla `auditoria` de cada cambio de rol realizado por el administrador.
- Vista de acceso (login/registro) con diseño de tarjeta única animada, responsiva a celular.

## Desviaciones respecto al Planning
Ninguna en alcance — las 4 historias planificadas se completaron. El tiempo de UI/CSS de la vista de acceso fue mayor al estimado inicialmente (ver Retrospective).
