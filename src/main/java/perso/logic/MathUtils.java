package perso.logic;

/**
 * Classe utilitaire finale fournissant des fonctions mathématiques auxiliaires.
 * <p>
 * Actuellement contient une méthode pour effectuer une division entière
 * arrondie vers le haut (division euclidienne plafond).
 * </p>
 */
public final class MathUtils {

    /**
     * Constructeur privé pour empêcher l'instanciation.
     */
    private MathUtils() {}

    /**
     * Divise a par b en arrondissant le résultat vers la valeur supérieure.
     * <p>
     * Comportement :
     * <ul>
     *   <li>Si a est divisible par b, retourne a / b.</li>
     *   <li>Sinon, retourne (a / b) + 1 (division entière, arrondi supérieur).</li>
     * </ul>
     * </p>
     *
     * @param a numérateur (entier)
     * @param b dénominateur (entier, non nul)
     * @return le résultat de la division de a par b arrondi vers le haut
     * @throws ArithmeticException si {@code b == 0}
     */
    public static int divsup(int a, int b) { // pour diviser a par b mais en arrondissant vers la valeur supérieure
        if (a % b != 0) {
            return (a / b) + 1;
        }
        return a / b;
    }
}
