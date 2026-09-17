# Sprint 1 — Review
**Fecha de cierre:** 07/09/2026

Cerramos las 4 historias planificadas, ninguna se quedó a medias.

| Historia | Qué era | Evidencia |
|---|---|---|
| H1 | Landing page responsiva | `web/index.jsp`, commit `9b213ed` |
| H2 | Registro con correo único y contraseña cifrada | `RegistroController`, hash BCrypt verificado directo en la BD |
| H3 | Login/logout con sesión por rol | `LoginController` / `LogoutController`, probado con 5 casos distintos |
| H4 | Admin asigna/revoca roles | `GestionRolesController`, queda registrado en `auditoria` |

## Cómo lo probamos

Registramos un usuario nuevo y confirmamos en pgAdmin que la contraseña quedó como hash BCrypt (`$2a$...`), con el rol Cliente asignado automáticamente. Intentamos registrar el mismo correo dos veces y salió el mensaje "El correo ya se encuentra registrado" — sin excepción de Java expuesta.

Entramos con un usuario de cada rol (Cliente, Inmobiliaria, Administrador) y cada uno cayó en su panel correspondiente. Con una cuenta marcada como `activo = false` el acceso se rechazó. Cerramos sesión y confirmamos con `session.invalidate()` que, al volver a intentar entrar por URL directa, ya no había sesión.

También probamos entrar a `/admin/panel.jsp` sin sesión y con sesión de otro rol — en ambos casos `AccesoFilter` nos mandó a `acceso-denegado.jsp`. Y en el panel del admin, asignamos y revocamos roles en vivo; el sistema bloquea que un usuario se quede sin ningún rol.

## Cosas que agregamos sin que nos las pidieran

- Normalizar el correo a minúsculas antes de guardar o comparar, para que "Usuario@Mail.com" y "usuario@mail.com" no cuenten como distintos.
- Cada cambio de rol que hace el admin queda registrado en `auditoria`.
- La vista de login/registro terminó con una animación de tarjeta única, responsiva a celular — más trabajo de UI del que estimamos, pero no cambió el alcance.

## ¿Nos desviamos del Planning?

En alcance no, las 4 historias salieron completas. Lo que sí tomó más tiempo del estimado fue el CSS de la vista de acceso (ver Retrospective).
