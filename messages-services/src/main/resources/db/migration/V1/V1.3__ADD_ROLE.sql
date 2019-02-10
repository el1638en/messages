-- Insertion de rôles
INSERT INTO T_ROLE(R_ID, R_CODE, R_LIBELLE) VALUES (nextval('ROLE_SEQ'),'ADMIN','Administrateur');
INSERT INTO T_ROLE(R_ID, R_CODE, R_LIBELLE) VALUES (nextval('ROLE_SEQ'),'USERS','Utilisateur');
