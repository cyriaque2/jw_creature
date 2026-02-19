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
    private final ArrayList<CreatureManquante> bestiaireManquant = new ArrayList<>();

    /**
     * Ajoute une créature au bestiaire.
     *
     * @param creature instance de {@link Creature} à ajouter
     */
    public void addCreature(Creature creature) {
        this.bestiaire.add(creature);
    }

    /**
     * Recherche une créature par son nom.
     * <p>
     * Recherche d'abord dans la liste des créatures chargées. Si aucune créature
     * avec ce nom n'est trouvée, recherche ensuite dans les entrées manquantes.
     * Si toujours introuvable, crée une {@link CreatureManquante} correspondante,
     * l'ajoute à la liste des manquantes et la retourne.
     * </p>
     *
     * @param name nom de la créature recherchée (sensible à la casse)
     * @return la créature trouvée ou une {@code CreatureManquante} nouvellement créée
     */
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

    /**
     * Recherche une créature par son identifiant unique.
     *
     * @param id identifiant de la créature
     * @return la {@link Creature} correspondant à l'ID
     * @throws IllegalArgumentException si aucune créature du bestiaire ne possède l'ID fourni
     */
    public Creature getCreatureById(String id) {
        for (Creature creature : bestiaire) {
            if (creature.getId().equals(id)) {
                return creature;
            }
        }
        throw new IllegalArgumentException("Aucune créature trouvée avec l'ID: " + id);
    }

    /**
     * Retourne la liste interne des créatures du bestiaire.
     * <p>
     * ATTENTION : la liste retournée est la référence interne ; la modifier aura un
     * impact direct sur l'état du Bestiaire. Si un accès immuable est requis,
     * il faut retourner une copie.
     * </p>
     *
     * @return la {@code ArrayList<Creature>} interne contenant les créatures chargées
     */
    public ArrayList<Creature> getBestiaire() {
        return bestiaire;
    }

    /**
     * Représentation textuelle du bestiaire.
     * <p>
     * Concatène les toString() de toutes les créatures chargées, puis, si des
     * entrées manquantes existent, ajoute une section "Bestiaire manquant".
     * </p>
     *
     * @return une chaîne décrivant le contenu du bestiaire et des entrées manquantes
     */
    @Override
    public String toString() {
        String sb = "";
        for (Creature creature : bestiaire) {
            sb += creature.toString() + "\n";
        }
        if (bestiaireManquant.isEmpty()) {
            return sb;
        }
        sb += "\n\nBestiaire manquant : \n";
        for (CreatureManquante creatureManquante : bestiaireManquant) {
            sb += creatureManquante.toString() + "\n";
        }
        return sb;
    }
}
