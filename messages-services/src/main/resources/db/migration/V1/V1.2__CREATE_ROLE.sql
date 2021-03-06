-- Création de la table des rôles
CREATE TABLE T_ROLE(
  R_ID BIGINT NOT NULL,
  R_CODE VARCHAR(50) NOT NULL,
  R_LIBELLE VARCHAR(255) NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  PRIMARY KEY(R_ID),
  CONSTRAINT U_ROLE_CODE UNIQUE(R_CODE)
);

-- Commentaires sur la table des rôles et ses colonnes
COMMENT ON TABLE T_ROLE IS 'Table des rôles.';
COMMENT ON COLUMN T_ROLE.R_ID IS 'ID du rôle.';
COMMENT ON COLUMN T_ROLE.R_CODE IS 'Code du rôle';
COMMENT ON COLUMN T_ROLE.R_LIBELLE IS 'Libellé du rôle';
COMMENT ON COLUMN T_ROLE.CREATE_DATE IS 'Date de création';
COMMENT ON COLUMN T_ROLE.UPDATE_DATE IS 'Date de dernière mise à jour';

-- Création d'une sequence pour gérer les identifiants techniques des rôles
CREATE SEQUENCE ROLE_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
