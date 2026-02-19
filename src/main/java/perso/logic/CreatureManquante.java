package perso.logic;

public class CreatureManquante extends Creature {

    public CreatureManquante(String nom) {
        super(nom, -1);
    }

    public Creature trouveCreature(int rarete, Creature parent1, Creature parent2) {
        return new Creature(this.nom, rarete, parent1, parent2);
    }

    public Creature trouveCreature(int rarete) {
        return new Creature(this.nom, rarete);
    }

    @Override
    public String toString() {
        return "CreatureManquante{" +"id=" + getId() + " | nom= " + nom + "}";
    }
    
}
