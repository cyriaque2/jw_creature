package perso.logic;

/**
 * Générateur d'identifiants pour les créatures selon leur rareté.
 * <p>
 * Chaque méthode retourne une chaîne préfixée par une lettre représentant la
 * rareté (I, C, R, E, L, U, S) suivie d'un compteur incrémental unique pour
 * cette rareté. Les compteurs commencent à 1 et s'incrémentent à chaque appel.
 * </p>
 * <p>
 * Utilisation typique : {@code getId(rarete)} renvoie un identifiant adapté à
 * la rareté fournie. La valeur {@code -1} est réservée aux entrées "manquantes".
 * </p>
 */
public class Rarete {
    /**
     * Compteur pour les identifiants de type "I" (entrée manquante).
     */
    private int icI = 1;
    private int idC = 1;
    private int idR = 1;
    private int idE = 1;
    private int idL = 1;
    private int idU = 1;
    private int idS = 1;
    
    /**
     * Retourne un identifiant unique pour la rareté donnée.
     *
     * @param r code de rareté :
     *          -1 => entrée manquante (préfixe "I"),
     *           1 => Commun ("C"),
     *           2 => Rare ("R"),
     *           3 => Epique ("E"),
     *           4 => Légendaire ("L"),
     *           5 => Unique ("U"),
     *           6 => Super Prédateur ("S")
     * @return identifiant sous forme de chaîne (préfixe + compteur)
     * @throws IllegalArgumentException si {@code r} n'est pas l'un des codes attendus
     */
    public String getId(int r){
        return switch (r) {
            case -1 -> getIdI();
            case  1 -> getIdC();
            case  2 -> getIdR();
            case  3 -> getIdE();
            case  4 -> getIdL();
            case  5 -> getIdU();
            case  6 -> getIdS();
            default -> throw new IllegalArgumentException("Rareté invalide : " + r);
        };
    }

    /**
     * Génère un identifiant pour une entrée manquante.
     *
     * @return chaîne sous la forme "I{n}" où n est un entier unique croissant
     */
    private String getIdI() {
        return "I"+icI++;
    }

    /**
     * Génère un identifiant pour une créature commune.
     *
     * @return chaîne sous la forme "C{n}"
     */
    private String getIdC() {
        return "C"+idC++;
    }

    /**
     * Génère un identifiant pour une créature rare.
     *
     * @return chaîne sous la forme "R{n}"
     */
    private String getIdR() {
        return "R"+idR++;
    }

    /**
     * Génère un identifiant pour une créature épique.
     *
     * @return chaîne sous la forme "E{n}"
     */
    private String getIdE() {
        return "E"+idE++;
    }   

    /**
     * Génère un identifiant pour une créature légendaire.
     *
     * @return chaîne sous la forme "L{n}"
     */
    private String getIdL() {
        return "L"+idL++;
    }

    /**
     * Génère un identifiant pour une créature unique.
     *
     * @return chaîne sous la forme "U{n}"
     */
    private String getIdU() {
        return "U"+idU++;
    }

    /**
     * Génère un identifiant pour une créature super-prédateur.
     *
     * @return chaîne sous la forme "S{n}"
     */
    private String getIdS() {
        return "S"+idS++;
    }

}
