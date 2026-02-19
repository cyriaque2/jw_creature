package perso.logic;

/**
 * Représente une créature du bestiaire.
 * <p>
 * Chaque instance possède :
 * <ul>
 *   <li>un identifiant unique {@code id} généré via {@code Rarete} ;</li>
 *   <li>un {@code nom} visible publiquement ;</li>
 *   <li>une {@code rarete} (valeurs valides : 1..6) ;</li>
 *   <li>éventuellement deux parents {@code parent1} et {@code parent2} (null si créature de base).</li>
 * </ul>
 * La valeur spéciale {@code rarete == -1} est autorisée uniquement pour les sous-classes
 * représentant des créatures "manquantes" (ex. {@code CreatureManquante}).
 * </p>
 */
public class Creature {
    /**
     * Générateur d'identifiants (utilisé pour construire {@link #id}).
     */
    private static final Rarete idCounter = new Rarete();

    /**
     * Identifiant unique de la créature (dérivé à partir de la rareté via {@code idCounter}).
     */
    private final String id;

    /**
     * Rareté de la créature.
     * <p>
     * Valeurs attendues : 1..6 pour les créatures normales, ou -1 pour une
     * créature manquante (sous-classe {@code CreatureManquante}).
     * </p>
     */
    private final int rarete;

    /**
     * Nom public de la créature.
     */
    public final String nom;

    /**
     * Parents (peut être {@code null} si créature de base).
     */
    private final Creature parent1, parent2;
    
    /**
     * Construis une créature avec ses parents.
     *
     * @param nom     nom de la créature (non null)
     * @param rarete  rareté attendue (1..6 pour une créature normale, -1 autorisé
     *                uniquement si l'instance est une {@code CreatureManquante})
     * @param parent1 parent 1 (peut être {@code null})
     * @param parent2 parent 2 (peut être {@code null})
     * @throws IllegalArgumentException si {@code rarete} n'est pas dans les plages autorisées
     */
    public Creature(String nom, int rarete, Creature parent1 , Creature parent2) {
        this.nom = nom;

        if ((rarete>=0 && rarete<=5) || (rarete ==-1 && this instanceof CreatureManquante)){//test de rareté de la Créature
            this.rarete = rarete;
        } else {
            throw new IllegalArgumentException("Mauvaise valeur pour la rareté:\n     1 : Commun\n     2 : Rare\n     3 : Epique\n     4 : Legendaire\n    5 : Unique\n    6 : Super Prédateur");
        }

        this.parent1 = parent1;//a changer pour que les parents soient récupérés via le nom
        this.parent2 = parent2;//pareil
        this.id = idCounter.getId(rarete);
    }

    /**
     * Construis une créature sans parents.
     *
     * @param nom    nom de la créature
     * @param rarete rareté (1..6)
     * @throws IllegalArgumentException si {@code rarete} invalide
     */
    public Creature(String nom, int rarete) {
        this(nom, rarete, null, null);
    }

    /**
     * Retourne le nom de la créature.
     *
     * @return le nom
     */
    public String getNom() {return nom;}

    /**
     * Retourne l'identifiant unique de la créature.
     *
     * @return l'identifiant
     */
    public String getId() {return id;}

    /**
     * Retourne la rareté de la créature.
     *
     * @return la rareté (int)
     */
    public int getRarete() {return rarete;}

    /**
     * Retourne le parent1 (ou {@code null} si absent).
     *
     * @return parent1 ou {@code null}
     */
    public Creature getParent1() {return parent1;}

    /**
     * Retourne le parent2 (ou {@code null} si absent).
     *
     * @return parent2 ou {@code null}
     */
    public Creature getParent2() {return parent2;}

    /**
     * Représentation textuelle synthétique de la créature.
     *
     * @return chaîne décrivant l'id, le nom et éventuellement les parents
     */
    @Override
    public String toString() {
        String s ="Creature{" +"id=" + id + " | nom= " + nom;
        if (parent1 != null) {
            s += " | parent1= " + parent1.nom+" | parent2= " + parent2.nom;
        }
        return s + "}";
    }
}
