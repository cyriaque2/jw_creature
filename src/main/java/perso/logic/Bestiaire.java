package perso.logic;
import java.util.ArrayList;
/**
 * Représente le conteneur principal des {@link Creature} connus et des entrées
 * "manquantes" créées à la volée lorsqu'une créature est référencée mais non encore
 * présente dans le bestiaire.
 * <p>
 * Fournit des opérations d'ajout, de recherche par nom ou par identifiant et de
 * récupération de la liste interne. Les listes internes sont retournées directement
 * (référence interne) : toute modification faite sur la liste retournée affecte
 * l'état du Bestiaire.
 * </p>
 */
public class Bestiaire {
    /**
     * Liste des créatures chargées dans le bestiaire.
     */
    private final ArrayList<Creature> bestiaire = new ArrayList<>();

    /**
     * Liste des créatures "manquantes" : objets créés lorsque l'on demande une
     * créature par nom qui n'existe pas encore dans {@code bestiaire}. Permet de
     * conserver les références manquantes afin d'établir des liens ultérieurement.
     */
    private final ArrayList<Creature> bestiaireInconnu = new ArrayList<>();

    /**
     * Ajoute une créature au bestiaire.
     *
     * @param creature instance de {@link Creature} à ajouter
     */
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
