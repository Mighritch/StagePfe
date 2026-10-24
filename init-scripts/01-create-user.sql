-- ============================================================
-- Création du schéma GRH_DW pour l'application BEA-BI
-- ============================================================

ALTER SESSION SET CONTAINER = XEPDB1;

-- Supprimer l'utilisateur s'il existe déjà (pour les recréations)
BEGIN
   EXECUTE IMMEDIATE 'DROP USER grh_dw CASCADE';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -1918 THEN -- ORA-01918: user does not exist
         RAISE;
      END IF;
END;
/

-- Créer l'utilisateur
CREATE USER grh_dw IDENTIFIED BY grh_dw
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP
QUOTA UNLIMITED ON USERS;

-- Privilèges de base pour Hibernate/JPA
GRANT CREATE SESSION TO grh_dw;
GRANT CREATE TABLE TO grh_dw;
GRANT CREATE SEQUENCE TO grh_dw;
GRANT CREATE TRIGGER TO grh_dw;
GRANT CREATE VIEW TO grh_dw;
GRANT CREATE PROCEDURE TO grh_dw;
GRANT CREATE TYPE TO grh_dw;
GRANT CREATE SYNONYM TO grh_dw;

-- Pour que Hibernate puisse lire les métadonnées
GRANT SELECT ANY DICTIONARY TO grh_dw;

-- Pour les opérations DML
GRANT INSERT ANY TABLE TO grh_dw;
GRANT UPDATE ANY TABLE TO grh_dw;
GRANT DELETE ANY TABLE TO grh_dw;
GRANT SELECT ANY TABLE TO grh_dw;

COMMIT;