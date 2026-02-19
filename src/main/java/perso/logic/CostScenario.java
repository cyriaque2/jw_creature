package perso.logic;

public enum CostScenario {
    WORST_CASE(10),
    AVERAGE_CASE(20),
    BEST_CASE(100);

    public final int divisor;

    CostScenario(int divisor) {
        this.divisor = divisor;
    }
}

