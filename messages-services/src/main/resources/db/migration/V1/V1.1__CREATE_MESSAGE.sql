-- Création de la tables des messages
CREATE TABLE T_MESSAGE(
  M_ID BIGINT NOT NULL,
  M_TITLE VARCHAR(200) NOT NULL,
  M_CONTENT VARCHAR(255) NOT NULL,
  M_BEGIN_DATE DATE NOT NULL,
  M_END_DATE DATE NOT NULL,
  CREATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP default CURRENT_TIMESTAMP,
  PRIMARY KEY(M_ID)
);

-- Commentaires sur la table des messages et des colonnes
COMMENT ON TABLE T_MESSAGE IS 'Table des patients';
COMMENT ON COLUMN T_MESSAGE.M_ID IS 'ID du message';
COMMENT ON COLUMN T_MESSAGE.M_TITLE IS 'Titre du message';
COMMENT ON COLUMN T_MESSAGE.M_CONTENT IS 'Prénom du patient';
COMMENT ON COLUMN T_MESSAGE.M_BEGIN_DATE IS 'Date de debut de publication du message';
COMMENT ON COLUMN T_MESSAGE.M_END_DATE IS 'Date de fin de publication du message';
COMMENT ON COLUMN T_MESSAGE.CREATE_DATE IS 'Date de création';
COMMENT ON COLUMN T_MESSAGE.UPDATE_DATE IS 'Date de dernière mise à jour';


-- Création d'une sequence pour gérer les identifiants techniques des messages
CREATE SEQUENCE MESSAGE_SEQ
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;