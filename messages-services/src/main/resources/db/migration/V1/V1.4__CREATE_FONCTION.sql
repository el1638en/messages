-- Création de la table des fonctionnalités
CREATE TABLE T_FONCTION(
  F_ID BIGINT NOT NULL,
  F_CODE VARCHAR(50) NOT NULL,
  F_LIBELLE VARCHAR(255) NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  CONSTRAINT U_FONCTIONNALITE_CODE UNIQUE(F_CODE), -- Contrainte d'unicité sur le code de la fonction
  PRIMARY KEY(F_ID) -- Définition de la clé primaire de la table
);

-- Commentaires sur la table des fonctionnalités et ses colonnes
COMMENT ON TABLE T_FONCTION IS 'Table des fonctions.';
COMMENT ON COLUMN T_FONCTION.F_ID IS 'ID de la fonction.';
COMMENT ON COLUMN T_FONCTION.F_CODE IS 'Code de la fonction.';
COMMENT ON COLUMN T_FONCTION.F_LIBELLE IS 'Libellé de la fonction.';
COMMENT ON COLUMN T_FONCTION.CREATE_DATE IS 'Date de création de la fonction.';
COMMENT ON COLUMN T_FONCTION.UPDATE_DATE IS 'Date de dernière mise à jour.';

-- Création d'une sequence pour gérer les identifiants techniques des fonctionnalités
CREATE SEQUENCE FONCTION_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;