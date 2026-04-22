package perso.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilitaire final fournissant le calcul des coûts (ou quantités) nécessaires
 * pour atteindre un objectif de production d'une {@link Creature} donnée selon
 * un scénario de coût {@link CostScenario}.
 * <p>
 * La méthode principale effectue un calcul récursif : si la créature est une 
 * créature de base (pas de parent), le coût est directement affecté à son nom ; 
 * sinon la méthode décompose l'objectif en objectifs intermédiaires pour 
 * chacun des parents, applique des multiplicateurs selon la différence de 
 * rareté et agrège les résultats.
 * </p>
 */
public final class CalculsCout {

    /**
     * Table des multiplicateurs de DNA indexée par la différence de rareté.
     * <p>
     * L'index i correspond au multiplicateur à appliquer lorsqu'une créature
     * d'une rareté supérieure d'i par rapport à son parent est fabriquée.
     * Valeurs : {0, 50, 200, 500, 2000, 5000}.
     * </p>
     */
    public static final int[] Taux_DNA = {0, 50, 200, 500, 2000, 5000};

    /**
     * Table des multiplicateurs de pièces (Coins) indexée par la rareté relative.
     * <p>
     * Utilisée pour calculer le coût local de fusion lors de la création d'une créature.
     * </p>
     */
    public static final int[] Taux_Coins = {20, 100, 200, 1000, 2000};

    /**
     * Constructeur privé pour empêcher l'instanciation.
     * <p>
     * Cette classe est une collection de méthodes utilitaires statiques.
     * </p>
     */
    private CalculsCout() {}

    /**
     * Calcule le coût total pour une créature donnée en utilisant le type de monnaie par défaut (Valeur Temporaire).
     *
     * @param objectif Quantité cible à produire.
     * @param cas      Scénario de coût définissant notamment le diviseur de production.
     * @param creature La créature cible du calcul.
     * @return Une Map associant le nom des ressources (DNA de créatures de base ou "Coins ") à leur quantité totale.
     */
    public static Map<String, Integer> calculCout(int objectif, CostScenario cas, Creature creature) {
        return calculCout(objectif, cas, creature, CurrencyType.COINS);
    }

    /**
     * Méthode récursive calculant le coût détaillé selon le type de monnaie spécifié.
     *
     * @param objectif Quantité cible pour l'étape actuelle.
     * @param cas      Scénario de coût.
     * @param creature Créature en cours d'évaluation.
     * @param type     Le type de monnaie (DNA ou COINS) à calculer.
     * @return Une Map des coûts agrégés.
     * @throws IllegalArgumentException si le type de monnaie fourni est inconnu.
     */
    private static Map<String, Integer> calculCout(int objectif, CostScenario cas, Creature creature, CurrencyType type) {
        Map<String, Integer> res = new HashMap<>();
        Map<String, Integer> temp;
        int objectif_DNA_parent1;
        int objectif_DNA_parent2;
        
        int divisor = cas.divisor;
        int nombre_fusion = MathUtils.divsup(objectif, divisor);

        if (creature.getParent1() == null) {
            switch (type) {
                case DNA -> res.put(creature.getNom(), objectif);
                case COINS -> res.put("Coins ", 0);
                default -> throw new IllegalArgumentException("Type d'ID inconnu : " + type);
            }
        } else {
            objectif_DNA_parent1 = nombre_fusion * Taux_DNA[creature.getRarete() - creature.getParent1().getRarete()];
            objectif_DNA_parent2 = nombre_fusion * Taux_DNA[creature.getRarete() - creature.getParent2().getRarete()];

            if (type == CurrencyType.DNA) {
                res = calculCout(objectif_DNA_parent1, cas, creature.getParent1(), type);
                temp = calculCout(objectif_DNA_parent2, cas, creature.getParent2(), type);
                merge(res, temp);

            } else if (type == CurrencyType.COINS) {
                addLocalCost(res, type, creature, nombre_fusion);

                temp = calculCout(objectif_DNA_parent1, cas, creature.getParent1(), type);
                merge(res, temp);

                temp = calculCout(objectif_DNA_parent2, cas, creature.getParent2(), type);
                merge(res, temp);

            } else {
                throw new IllegalArgumentException("Type d'ID inconnu : " + type);
            }
        }
        return res;
    }

    /**
     * Fusionne les résultats d'une Map temporaire dans la Map de résultats principale 
     * en additionnant les valeurs pour les clés communes.
     *
     * @param res  La Map de destination (sera modifiée).
     * @param temp La Map source contenant les valeurs à ajouter.
     */
    private static void merge(Map<String, Integer> res, Map<String, Integer> temp) {
        for (Map.Entry<String, Integer> entry : temp.entrySet()) {
            res.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    /**
     * Calcule et ajoute le coût local (spécifique à l'étape de fusion actuelle) 
     * à la Map de résultats.
     *
     * @param res           La Map de résultats.
     * @param type          Le type de monnaie actuel.
     * @param creature      La créature dont on calcule la fusion.
     * @param nombre_fusion Le nombre de fusions nécessaires.
     */
    private static void addLocalCost(Map<String, Integer> res, CurrencyType type, Creature creature, int nombre_fusion) {
        if (type == CurrencyType.COINS) {
            int coins = nombre_fusion * Taux_Coins[creature.getRarete() - 2];
            res.merge("Coins ", coins, Integer::sum);
        }
    }
}