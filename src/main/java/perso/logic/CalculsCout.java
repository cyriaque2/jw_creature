package perso.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilitaire final fournissant le calcul des coûts (ou quantités) nécessaires
 * pour atteindre un objectif de production d'une {@link Creature} donnée selon
 * un scénario de coût {@link CostScenario}.
 * <p>
 * La méthode principale est {@link #calculCout(int, CostScenario, Creature)} qui
 * effectue un calcul récursif : si la créature est une créature de base (pas de
 * parent1), le coût est directement affecté à son nom ; sinon la méthode
 * décompose l'objectif en objectifs intermédiaires pour chacun des parents,
 * applique des multiplicateurs selon la différence de rareté et agrège les
 * résultats.
 * </p>
 */
public final class CalculsCout {
    /**
     * Table des multiplicateurs (taux) indexée par la différence de rareté.
     * <p>
     * L'index i correspond au multiplicateur à appliquer lorsqu'une créature
     * d'une rareté supérieure d'i par rapport à son parent est fabriquée.
     * Valeurs observées : {0, 50, 200, 500, 2000, 5000}.
     * </p>
     */
    public static final int[] Taux = {0, 50, 200, 500, 2000, 5000};

    /**
     * Constructeur privé pour empêcher l'instanciation.
     * <p>
     * Cette classe est une collection de méthodes utilitaires statiques.
     * </p>
     */
    private CalculsCout() {} // classe utilitaire, pas d'instance

    /**
     * Calcule récursivement le coût (ou la quantité) nécessaire pour atteindre
     * un objectif donné d'une créature.
     * <p>
     * Algorithme :
     * <ol>
     *   <li>Si la créature n'a pas de parent1 (créature de base), on associe
     *       directement l'objectif au nom de la créature : map.put(nom, objectif).</li>
     *   <li>Sinon :
     *     <ul>
     *       <li>On calcule pour chaque parent un objectif intermédiaire :
     *           objectifParent = MathUtils.divsup(objectif, cas.divisor) *
     *           Taux[creature.rarete - parent.rarete]</li>
     *       <li>On appelle récursivement calculCout pour chaque parent avec
     *           son objectif calculé.</li>
     *       <li>On fusionne les cartes retournées en sommant les valeurs pour
     *           les mêmes clefs (noms de créatures).</li>
     *     </ul>
     *   </li>
     * </ol>
     * </p>
     *
     * @param objectif quantité cible de la créature finale (entier >= 0)
     * @param cas      scénario de coût utilisé
     * @param creature la créature cible pour laquelle calculer le coût
     * @return une {@code Map<String,Integer>} associant les noms de créatures
     *         de base aux quantités/couts nécessaires pour atteindre l'objectif
     */
    public static Map<String, Integer> calculCout(int objectif, CostScenario cas, Creature creature) {
        Map<String, Integer> res = new HashMap<>();
        Map<String, Integer> temp;
        int objectif2;
        if (creature.getParent1() == null) {
            res.put(creature.getNom(), objectif);
        } else {
            int divisor = cas.divisor;
            objectif2 = MathUtils.divsup(objectif, divisor) * Taux[creature.getRarete() - creature.getParent1().getRarete()];
            res = calculCout(objectif2, cas, creature.getParent1());
            objectif2 = MathUtils.divsup(objectif, divisor) * Taux[creature.getRarete() - creature.getParent2().getRarete()];
            temp = calculCout(objectif2, cas, creature.getParent2());
            for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                res.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return res;
    }
}
