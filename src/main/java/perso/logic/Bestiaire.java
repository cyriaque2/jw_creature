package perso.logic;
import java.util.ArrayList;

public class Bestiaire {
    private final ArrayList<Creature> bestiaire = new ArrayList<>(); 
    private final ArrayList<CreatureManquante> bestiaireManquant = new ArrayList<>();
    public void addCreature(Creature creature) {
        this.bestiaire.add(creature);
    }

    public Creature getCreatureByName(String name) {
        for (Creature creature : bestiaire) {
            if (creature.getNom().equals(name)) {
                return creature;
            }
        }
        for (CreatureManquante creatureManquante : bestiaireManquant) {
            if (creatureManquante.getNom().equals(name)) {
                return creatureManquante;
            }
        }
        bestiaireManquant.add(new CreatureManquante(name));
        return bestiaireManquant.get(bestiaireManquant.size() - 1);
    }

    public Creature getCreatureById(String id) {
        for (Creature creature : bestiaire) {
            if (creature.getId().equals(id)) {
                return creature;
            }
        }
        throw new IllegalArgumentException("Aucune créature trouvée avec l'ID: " + id);
    }

    public ArrayList<Creature> getBestiaire() {
        return bestiaire;
    }

    @Override
    public String toString() {
        String sb = "";
        for (Creature creature : bestiaire) {
            sb+=creature.toString()+"\n";
        }
        if (bestiaireManquant.isEmpty()) {
            return sb;
        }
        sb+="\n\nBestiaire manquant : \n";
        for (CreatureManquante creatureManquante : bestiaireManquant) {
            sb+=creatureManquante.toString()+"\n";
        }
        return sb;
    }
}
