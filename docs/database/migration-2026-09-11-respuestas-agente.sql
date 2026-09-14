-- ============================================================
-- Migración — Hogar 360 (2026-09-11)
-- Agrega la respuesta del agente (motivo de rechazo / mensaje de
-- confirmación) a solicitudes y citas.
--
-- Ejecutar UNA SOLA VEZ contra la base de datos ya existente
-- (inmobiliaria_db). No borra ni modifica datos existentes.
-- Estas mismas columnas ya quedaron agregadas directamente en
-- docs/database/ddl-inmobiliaria.sql para instalaciones nuevas.
-- ============================================================

ALTER TABLE solicitud
    ADD COLUMN IF NOT EXISTS respuesta_agente TEXT,
    ADD COLUMN IF NOT EXISTS fecha_respuesta TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE cita
    ADD COLUMN IF NOT EXISTS respuesta_agente TEXT,
    ADD COLUMN IF NOT EXISTS fecha_respuesta TIMESTAMP WITHOUT TIME ZONE;
