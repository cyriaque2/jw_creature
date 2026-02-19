package perso.logic;

public class Creature {
    private static final Rarete idCounter = new Rarete();
    private final String id;
    private final int rarete;
    public final String nom;
    private final Creature parent1, parent2;
    
    public Creature(String nom, int rarete, Creature parent1 , Creature parent2) {
        this.nom = nom;

        if ((rarete>=0 && rarete<=5) || (rarete ==-1 && this instanceof CreatureManquante)){//test de rareté de la Créature
            this.rarete = rarete;
        } else {
            throw new IllegalArgumentException("Mauvaise valeur pour la rareté:\n     1 : Commun\n     2 : Rare\n     3 : Epique\n     4 : Legendaire\n    5 : Unique\n    6 : Super Prédateur");
        }

        this.parent1 = parent1;//a changer pour que les parents soient récupérés via le nom
        this.parent2 = parent2;//pareil
        this.id = idCounter.getId(rarete);
    }

    public Creature(String nom, int rarete) {
        this(nom, rarete, null, null);
    }

    public String getNom() {return nom;}

    public String getId() {return id;}

    public int getRarete() {return rarete;}

    public Creature getParent1() {return parent1;}

    public Creature getParent2() {return parent2;}

    @Override
    public String toString() {
        String s ="Creature{" +"id=" + id + " | nom= " + nom;
        if (parent1 != null) {
            s += " | parent1= " + parent1.nom+" | parent2= " + parent2.nom;
        }
        return s + "}";
    }
}
