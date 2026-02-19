package perso.logic;

import java.util.HashMap;
import java.util.Map;

public final class CalculsCout {
    public static final int[] Taux={0,50,200,500,2000,5000};

    private CalculsCout() {}// classe utilitaire, pas d'instance
    
    public static Map<String, Integer> calculCout(int objectif, CostScenario cas, Creature creature) {
        Map<String, Integer> res = new HashMap<>();
        Map<String, Integer> temp;
        int objectif2;
        if(creature.getParent1() == null){
            res.put(creature.getNom(), objectif);
        } else {
            int divisor = cas.divisor;
            objectif2 = MathUtils.divsup(objectif, divisor)*Taux[creature.getRarete()-creature.getParent1().getRarete()];
            res = calculCout(objectif2, cas, creature.getParent1());
            objectif2 = MathUtils.divsup(objectif, divisor)*Taux[creature.getRarete()-creature.getParent2().getRarete()];
            temp = calculCout(objectif2, cas, creature.getParent2());
            for (Map.Entry<String, Integer> entry : temp.entrySet()) {
                res.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return res;
    }
}
