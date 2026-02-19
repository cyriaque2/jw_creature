package perso.logic;

/**
 * Enumération décrivant les scénarios de rendement utilisés par les calculs de
 * production/quantité dans l'application.
 * <p>
 * La quantité obtenue par fusion est aléatoire et varie typiquement entre 10 et 100
 * unités, avec une valeur moyenne observée autour de 20 unités par fusion.
 * Chaque constante représente une hypothèse (pire cas, cas moyen, meilleur cas).
 * </p>
 * <p>
 * Exemple d'utilisation : {@link CalculsCout#calculCout(int, CostScenario, Creature)}
 * utilise la valeur {@code divisor} pour déterminer comment répartir un objectif
 * entre les parents d'une créature.
 * </p>
 */
public enum CostScenario {

    /**
     * Pire cas : on suppose un rendement faible par fusion (10 unités).
     * Utilisé pour obtenir une estimation conservatrice (besoins plus élevés).
     */
    WORST_CASE(10),

    /**
     * Cas moyen : rendement moyen observé (20 unités).
     * Valeur recommandée pour des estimations réalistes.
     */
    AVERAGE_CASE(20),

    /**
     * Meilleur cas : rendement maximal possible (100 unités).
     * Utilisé pour estimer le besoin minimal en supposant chance maximale.
     */
    BEST_CASE(100);

    /**
     * Rendement attendu par fusion (quantité moyenne supposée fournie par une opération).
     * <p>
     * En pratique, cette valeur sert de diviseur lors du calcul des objectifs
     * intermédiaires : objectif_intermediaire = divsup(objectif_final, divisor).
     * Une valeur plus petite implique davantage d'opérations nécessaires.
     * </p>
     */
    public final int divisor;

    /**
     * Constructeur de l'énumération.
     *
     * @param divisor rendement attendu(doit être > 0)
     */
    CostScenario(int divisor) {
        this.divisor = divisor;
    }
}

