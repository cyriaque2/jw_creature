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
     * @return Une Map associant le nom des ressources (DNA de créatures de base ou "Coins") à leur quantité totale.
     */
    public static Map<String, Integer> calculCout(int objectif, CostScenario cas, Creature creature) {
        return calculCoutGeneral(objectif, -1, cas, creature);
        
    }

    private static Map<String, Integer> calculCoutGeneral(int objectif_dna, int objectif_lvl, CostScenario cas, Creature creature){
        Map<String, Integer> res = calculCoutFusion(objectif_dna, cas, creature);
        Map<String, Integer> temp;

        if(objectif_lvl != -1){
            temp = calculCoutLevelUp(objectif_lvl, creature);
            merge(res, temp);
        } else {
            if(creature.getParent1()==null){
                return res;
            }
        }
        
        if(creature.getParent1()!=null){
            temp = calculCoutGeneral(res.get(creature.getParent1().getNom()), (creature.getRarete()-1)*5, cas, creature.getParent1());
            merge(res, temp);
            temp = calculCoutGeneral(res.get(creature.getParent2().getNom()), (creature.getRarete()-1)*5, cas, creature.getParent2());
            merge(res, temp);
            return res;
        } else {
            res.clear();
            res.put("Coins", 0);
            return res;
        }
    }

    public static Map<String, Integer> calculCoutFusion(int objectif, CostScenario cas, Creature creature) {
        Map<String, Integer> res = new HashMap<>();
        int divisor = cas.divisor;
        int nombre_fusion = MathUtils.divsup(objectif, divisor);

        if (creature.getParent1() == null) {
            res.put(creature.getNom(), objectif);
            res.put("Coins", 0);
        } else {
            int objectif_DNA_parent1 = nombre_fusion * Taux.instance.get_Taux_DNA_Fusion()[creature.getRarete() - creature.getParent1().getRarete()];
            int objectif_DNA_parent2 = nombre_fusion * Taux.instance.get_Taux_DNA_Fusion()[creature.getRarete() - creature.getParent2().getRarete()];
            res.put(creature.getParent1().getNom(), objectif_DNA_parent1);
            res.put(creature.getParent2().getNom(), objectif_DNA_parent2);
            int coins = nombre_fusion * Taux.instance.get_Taux_Coins_Fusion()[creature.getRarete() - 2];
            res.merge("Coins", coins, Integer::sum);
        }
        return res;
    }

    public static Map<String, Integer> calculCoutLevelUp(int objectif, Creature creature) { //objectif -> Niveau visé
        if(objectif<1 || objectif>30){
            throw new IllegalArgumentException("Objecctif niveau Incorrect, doit être compris entre 1 et 30 inclu. Valeur fournie:"+objectif);
        }
        Map<String, Integer> res = new HashMap<>();
        int niveau_creature = creature.getNiveau();
        int niveau_origine = creature.getRarete()*5-4;
        int coins = 0;
        int DNA = 0;
        if(niveau_creature==-1){
            niveau_creature = niveau_origine;
            DNA += Taux.instance.get_Taux_DNA_Unlock()[creature.getRarete()-1];
        }
        if(niveau_creature<objectif){
            for(int i = niveau_creature; i<objectif; i++){
                coins += Taux.instance.get_Taux_Coins_LevelUp()[i-1];
                DNA += Taux.instance.get_Taux_DNA_LevelUp()[i-niveau_origine];
            }
        }
        res.put("Coins", coins);
        res.put(creature.getNom(), DNA);
        return res;
    }

    public static Map<Creature, Boolean> getAncetres(Creature creature){
        Map<Creature, Boolean> res = new HashMap<>();
        res.put(creature, false);
        if(creature.getParent1() != null){
            Map<Creature, Boolean> temp = getAncetres(creature.getParent1());
            for (Map.Entry<Creature, Boolean> entry : temp.entrySet()){
               res.merge(entry.getKey(), entry.getValue(), Boolean::logicalAnd);
            }
            temp = getAncetres(creature.getParent2());
            for (Map.Entry<Creature, Boolean> entry : temp.entrySet()){
               res.merge(entry.getKey(), entry.getValue(), Boolean::logicalAnd);
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

}