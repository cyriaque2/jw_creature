package perso.logic;

public class Creature {

    private static final Rarete idCounter = new Rarete();
      
    private final String nom;
    
    private Creature parent1, parent2;

    private String id;

    private int rarete;

    private int niveau;

    public Creature(String nom, int rarete, int niveau, Creature parent1 , Creature parent2) { //Créature débloquée avec parents
        this.nom = nom;
        if ((rarete>=1 && rarete<=6) || (rarete ==-1)){
            this.rarete = rarete;
        } else {
            throw new IllegalArgumentException("Mauvaise valeur pour la rareté:\n     1 : Commun\n     2 : Rare\n     3 : Epique\n     4 : Legendaire\n    5 : Unique\n    6 : Super Prédateur");
        }
        this.niveau = niveau;
        this.parent1 = parent1;//a changer pour que les parents soient récupérés via le nom
        this.parent2 = parent2;//pareil
        this.id = idCounter.getId(rarete);
    }

    public Creature(String nom, int rarete, Creature parent1, Creature parent2){ //Crétaures Non-débloquées avec parents
        this(nom, rarete, -1, parent1, parent2);
    }

    public Creature(String nom){ //Constructeur de créatures inconnues (jamais de parents)
        this(nom, -1, -1, null, null);
    }

    public Creature(String nom, int rarete, int niveau) { //Créature débloquée sans parents
        this(nom, rarete, niveau, null, null);
    }

    public Creature(String nom, int rarete) {//Créature non-débloquée sans parents
        this(nom, rarete, -1, null, null);
    }
    
    public void levelUp(){
        if (niveau>=1 && niveau <35){
            this.niveau++;
        } else {
            System.out.println("Mauvaise créature, vous ne l'avez pas encore débloquée");
        }
    }

    public String getNom() {return nom;}

    public String getId() {return id;}

    public int getRarete() {return rarete;}

    public int getNiveau() {return niveau;}

    public Creature getParent1() {return parent1;}

    public Creature getParent2() {return parent2;}

    public String getType(){
        if(niveau>=1){
            return "Créature";
        }
        if(rarete>=1){
            return "Créature Manquante";
        }
        return "Créature Inconnue";
    }
    
    public void trouveCreature(int rarete, Creature parent1, Creature parent2, boolean unlocked) { //permet à une créature de quiter l'état inconnu
        if(this.rarete==-1){
            if(unlocked){
                niveau = (rarete-1)*5 + 1;
            }
            this.rarete = rarete;
            this.parent1 = parent1;
            this.parent2 = parent2;
            this.id = idCounter.getId(rarete);
        } else {
            throw new IllegalAccessError("La créature \""+ nom +"\" exite déjà, il n'y a pas besoin de la trouver à nouveau");
        }
    }

    public void trouveCreature(int rarete, boolean unlocked) {
        trouveCreature(rarete, null, null, unlocked);
    }
   
    public void unlock(){ //permet de débloquer une créature Manquante
        if(rarete != -1 && niveau ==-1){
            niveau = (rarete-1)*5 + 1;
        } else {
            if(rarete == -1){
                throw new IllegalAccessError("Impossible de débloquer la créature \""+ nom +"\", car cette dernière n'exite pas (elle ne possède pas de rareté conforme");
            } else {
                throw new IllegalAccessError("Impossible de débloquer la créature \""+ nom +"\", car cette dernière est déjà débloquée");
            }
        }
    }
   
    
    @Override
    public String toString() {
        String s = getType()+"{" +"id=" + id + " | nom= " + nom;
        if (niveau!=-1) {
            s+=" | niveau= " + niveau;
        }
        
        if (parent1 != null) {
            s += " | parent1= " + parent1.nom+" | parent2= " + parent2.nom;
        }
        
        return s + "}";
    }
}
