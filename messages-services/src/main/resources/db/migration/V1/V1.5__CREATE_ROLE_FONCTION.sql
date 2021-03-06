-- Création de la table de jointure T_ROLE_FONCTION
CREATE TABLE T_ROLE_FONCTION(
  ROLE_ID BIGINT NOT NULL,
  FONCTION_ID BIGINT NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  CONSTRAINT FK_ROLE_ID FOREIGN KEY(ROLE_ID) REFERENCES T_ROLE(R_ID),
  CONSTRAINT FK_FONCTION_ID FOREIGN KEY(FONCTION_ID) REFERENCES T_FONCTION(F_ID),
  PRIMARY KEY(ROLE_ID,FONCTION_ID)
);

-- Commentaires sur la table de jointure et ses colonnes
COMMENT ON TABLE T_ROLE_FONCTION IS 'Table de jointure entre les rôles et les fonctions.';
COMMENT ON COLUMN T_ROLE_FONCTION.ROLE_ID IS 'ID du rôle.';
COMMENT ON COLUMN T_ROLE_FONCTION.FONCTION_ID IS 'ID de la fonction.';
COMMENT ON COLUMN T_ROLE_FONCTION.CREATE_DATE IS 'Date de création.';
COMMENT ON COLUMN T_ROLE_FONCTION.UPDATE_DATE IS 'Date de dernière mise à jour.';