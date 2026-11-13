-- V1 : creation des tables d'ODC Connect (CDCT, section 3)

CREATE TABLE formation (
    id       BIGSERIAL PRIMARY KEY,
    intitule VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE utilisateur (
    id            BIGSERIAL PRIMARY KEY,
    nom           VARCHAR(100) NOT NULL,
    prenom        VARCHAR(100) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe  VARCHAR(100),
    role          VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'PARTICIPANT')),
    statut        VARCHAR(30)  NOT NULL CHECK (statut IN ('INVITATION_ENVOYEE', 'ACTIF', 'SUSPENDU_INACTIF')),
    formation_id  BIGINT REFERENCES formation (id),
    date_creation TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_utilisateur_formation CHECK (role = 'ADMIN' OR formation_id IS NOT NULL)
);

CREATE TABLE offre (
    id                   BIGSERIAL PRIMARY KEY,
    titre                VARCHAR(200) NOT NULL,
    organisation         VARCHAR(200) NOT NULL,
    type_contrat         VARCHAR(30)  NOT NULL,
    description          TEXT         NOT NULL,
    competences_requises TEXT         NOT NULL,
    contact_candidature  VARCHAR(255) NOT NULL,
    date_publication     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_limite          TIMESTAMP,
    statut               VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE' CHECK (statut IN ('ACTIVE', 'CLOTUREE', 'EXPIREE')),
    auteur_id            BIGINT       NOT NULL REFERENCES utilisateur (id),
    nombre_consultations INTEGER      NOT NULL DEFAULT 0
);

CREATE TABLE offre_formation (
    offre_id     BIGINT NOT NULL REFERENCES offre (id) ON DELETE CASCADE,
    formation_id BIGINT NOT NULL REFERENCES formation (id),
    PRIMARY KEY (offre_id, formation_id)
);

CREATE TABLE notification (
    id             BIGSERIAL PRIMARY KEY,
    participant_id BIGINT      NOT NULL REFERENCES utilisateur (id) ON DELETE CASCADE,
    offre_id       BIGINT      NOT NULL REFERENCES offre (id) ON DELETE CASCADE,
    type           VARCHAR(20) NOT NULL CHECK (type IN ('NOUVELLE_OFFRE', 'RAPPEL')),
    date_envoi     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_notification UNIQUE (participant_id, offre_id, type)
);

CREATE TABLE parametres_notification (
    participant_id        BIGINT  PRIMARY KEY REFERENCES utilisateur (id) ON DELETE CASCADE,
    notifications_actives BOOLEAN NOT NULL DEFAULT TRUE,
    rappels_actifs        BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE jeton (
    id              BIGSERIAL PRIMARY KEY,
    utilisateur_id  BIGINT      NOT NULL REFERENCES utilisateur (id) ON DELETE CASCADE,
    type            VARCHAR(20) NOT NULL CHECK (type IN ('ACTIVATION', 'REINITIALISATION', 'RAFRAICHISSEMENT')),
    valeur_hachee   VARCHAR(64) NOT NULL UNIQUE,
    date_expiration TIMESTAMP   NOT NULL,
    utilise         BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE favori (
    participant_id BIGINT    NOT NULL REFERENCES utilisateur (id) ON DELETE CASCADE,
    offre_id       BIGINT    NOT NULL REFERENCES offre (id) ON DELETE CASCADE,
    date_ajout     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (participant_id, offre_id)
);

-- Index (CDCT, section 3.3) ; utilisateur(email) est deja indexe par UNIQUE
CREATE INDEX idx_offre_statut_publication ON offre (statut, date_publication DESC);
CREATE INDEX idx_offre_date_limite ON offre (date_limite);
CREATE INDEX idx_offre_formation_formation ON offre_formation (formation_id);
CREATE INDEX idx_jeton_utilisateur ON jeton (utilisateur_id);

-- Recherche plein texte sans tenir compte des accents (US 06)
CREATE EXTENSION IF NOT EXISTS unaccent;
CREATE TEXT SEARCH CONFIGURATION fr_sans_accent (COPY = french);
ALTER TEXT SEARCH CONFIGURATION fr_sans_accent
    ALTER MAPPING FOR hword, hword_part, word WITH unaccent, french_stem;
CREATE INDEX idx_offre_recherche ON offre
    USING GIN (to_tsvector('fr_sans_accent', titre || ' ' || organisation || ' ' || description));
