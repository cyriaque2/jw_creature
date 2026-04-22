package perso.logic;

/**
 * Définit les types de monnaies ou ressources supportées par le système de calcul de coûts.
 * <p>
 * Utilisé pour distinguer les différentes ressources lors de l'estimation des besoins 
 * de production des créatures.
 * </p>
 */
public enum CurrencyType {
    /** Ressource ADN (DNA) utilisée pour les besoins en hybridation de base. */
    DNA,
    
    /** Ressource monétaire (Coins) utilisée pour payer les frais de fusion. */
    COINS;
}