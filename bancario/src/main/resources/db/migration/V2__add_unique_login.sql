-- =========================================================
-- V2__add_unique_login.sql
-- Adiciona restrição de unicidade ao login do cliente
-- =========================================================

ALTER TABLE cliente
ADD CONSTRAINT uk_cliente_login UNIQUE (login);