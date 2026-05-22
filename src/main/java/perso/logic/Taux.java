package perso.logic;

public class Taux {
    private Taux(){}
    public static final Taux instance = new Taux();

    /**
     * Table des multiplicateurs de DNA indexée par la différence de rareté.
     * <p>
     * L'index i correspond au multiplicateur à appliquer lorsqu'une créature
     * d'une rareté supérieure d'i par rapport à son parent est fabriquée.
     * Valeurs : {0, 50, 200, 500, 2000, 5000}.
     * </p>
     */
    private final int[] Taux_DNA_Fusion = {0, 50, 200, 500, 2000, 5000};

    /**
     * Table des multiplicateurs de pièces (Coins) indexée par la rareté relative.
     * <p>
     * Utilisée pour calculer le coût local de fusion lors de la création d'une créature.
     * </p>
     */
    private final int[] Taux_Coins_Fusion = {20, 100, 200, 1000, 2000};

    private final int[] Taux_DNA_LevelUp = {100, 150, 200, 250, 300, 350, 400, 500, 750, 1000, 1250, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 7500, 10000, 12500, 15000, 20000, 25000, 30000, 35000, 40000, 50000, 75000};

    private final int[] Taux_Coins_LevelUp = {5, 10, 25, 50, 100, 200, 400, 600, 800, 1000, 2000, 4000, 6000, 8000, 10000, 15000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 120000, 150000, 200000, 250000};

    private final int[] Taux_DNA_Unlock = {50, 100, 150, 200, 250, 300};

    public int[] get_Taux_DNA_Fusion(){
        return Taux_DNA_Fusion;
    }

    public int[] get_Taux_Coins_Fusion(){
        return Taux_Coins_Fusion;
    }

    public int[] get_Taux_DNA_LevelUp(){
        return Taux_DNA_LevelUp;
    }

    public int[] get_Taux_Coins_LevelUp(){
        return Taux_Coins_LevelUp;
    }

    public int[] get_Taux_DNA_Unlock(){
        return Taux_DNA_Unlock;
    }
}
