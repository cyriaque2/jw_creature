package perso.logic;

/**
 * Représente une créature référencée mais non encore définie dans le bestiaire.
 * <p>
 * Cette sous-classe de {@link Creature} sert de "place-holder" : elle porte un
 * nom connu mais une rareté temporaire (-1). Lorsque les informations complètes
 * deviennent disponibles, on peut obtenir une instance concrète {@link Creature}
 * via les méthodes {@link #trouveCreature(int)} ou
 * {@link #trouveCreature(int, Creature, Creature)}.
 * </p>
 */
public class CreatureManquante extends Creature {

    /**
     * Construit une entrée manquante avec le nom fourni.
     * <p>
     * La rareté est initialisée à -1 pour indiquer qu'il s'agit d'une créature
     * non définie/complète.
     * </p>
     *
     * @param nom nom de la créature manquante (non null)
     */
    public CreatureManquante(String nom) {
        super(nom, -1);
    }

    /**
     * Crée une instance concrète de {@link Creature} correspondant à cette
     * entrée manquante, en fournissant la rareté et les parents.
     *
     * @param rarete  rareté effective à utiliser pour la nouvelle créature (1..6)
     * @param parent1 parent 1 de la nouvelle créature (peut être {@code null})
     * @param parent2 parent 2 de la nouvelle créature (peut être {@code null})
     * @return une nouvelle {@code Creature} avec le même nom que l'entrée manquante
     */
    public Creature trouveCreature(int rarete, Creature parent1, Creature parent2) {
        return new Creature(this.nom, rarete, parent1, parent2);
    }

    /**
     * Crée une instance concrète de {@link Creature} sans parents correspondant à
     * cette entrée manquante.
     *
     * @param rarete rareté effective à utiliser pour la nouvelle créature (1..6)
     * @return une nouvelle {@code Creature} avec le même nom que l'entrée manquante
     */
    public Creature trouveCreature(int rarete) {
        return new Creature(this.nom, rarete);
    }

    /**
     * Représentation textuelle de l'entrée manquante.
     *
     * @return chaîne décrivant l'id et le nom de la {@code CreatureManquante}
     */
    @Override
    public String toString() {
        return "CreatureManquante{" +"id=" + getId() + " | nom= " + nom + "}";
    }
    
}
