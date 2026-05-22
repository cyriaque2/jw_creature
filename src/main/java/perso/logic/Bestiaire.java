package perso.logic;
import java.util.ArrayList;

public class Bestiaire {
    public static final Bestiaire instance = new Bestiaire();
    private Bestiaire() {}
    
    private final ArrayList<Creature> bestiaire = new ArrayList<>();

    private final ArrayList<Creature> bestiaireInconnu = new ArrayList<>();

    public void addCreature(Creature creature) {
        this.bestiaire.add(creature);
    }

    public Creature getCreatureByName(String name) {
        for (Creature creature : bestiaire) {
            if (creature.getNom().equals(name)) {
                return creature;
            }
        }
        for (Creature creatureInconnue : bestiaireInconnu) {
            if (creatureInconnue.getNom().equals(name)) {
                return creatureInconnue;
            }
        }
        bestiaireInconnu.add(new Creature(name));
        return bestiaireInconnu.get(bestiaireInconnu.size() - 1);
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

    public boolean peutSupprimer(Creature creature) {

        for (Creature c : bestiaire) {
            if (creature.equals(c.getParent1()) || creature.equals(c.getParent2())) {
                return false;
            }
        }
        return true;
    }

    public void supprimeCreature(Creature creature) {
        if (!peutSupprimer(creature)) {
            throw new IllegalStateException("La créature " + creature.getNom() + " est utilisée comme parent.");
        }
        bestiaire.remove(creature);
        bestiaireInconnu.remove(creature);
    }

    public ArrayList<Creature> getBestiaireInconnu() {
        return bestiaireInconnu;
    }  

    @Override
    public String toString() {
        String sb = "";
        for (Creature creature : bestiaire) {
            sb += creature.toString() + "\n";
        }
        if (bestiaireInconnu.isEmpty()) {
            return sb;
        }
        sb += "\n\nCréatures Inconnues : \n";
        for (Creature creatureInconnue : bestiaireInconnu) {
            sb += creatureInconnue.toString() + "\n";
        }
        return sb;
    }
}
