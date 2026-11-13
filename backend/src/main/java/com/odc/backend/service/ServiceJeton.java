package com.odc.backend.service;

import com.odc.backend.entite.TypeJeton;
import com.odc.backend.entite.Utilisateur;

/**
 * Contrat partagé : jetons à usage unique (CDCT, section 5.3).
 * Version réelle : Isabel (jeton aléatoire de 32 octets, empreinte SHA-256, durée selon le type).
 * Utilisé par : Isabel (activation, mot de passe oublié, session), Jean-Jacques (invitation,
 * réinitialisation par l'administrateur, suspension d'un compte).
 */
public interface ServiceJeton {

    /**
     * Crée un jeton pour cet utilisateur et renvoie sa valeur en clair, à placer dans le lien envoyé par e-mail.
     * Seule l'empreinte du jeton est enregistrée en base.
     * Durée : 72 heures (ACTIVATION), 1 heure (REINITIALISATION), 7 jours (RAFRAICHISSEMENT).
     */
    String creerJeton(Utilisateur utilisateur, TypeJeton type);

    /**
     * Vérifie un jeton reçu (il existe, il a le bon type, il n'a pas servi, il n'a pas expiré),
     * le marque comme utilisé et renvoie l'utilisateur concerné.
     * Si le jeton est expiré ou déjà utilisé, une exception est levée (code 410).
     */
    Utilisateur consommerJeton(String valeur, TypeJeton type);

    /** Rend inutilisables tous les jetons de ce type pour cet utilisateur (par exemple à la suspension du compte). */
    void invaliderJetons(Utilisateur utilisateur, TypeJeton type);
}
