package perso.logic.inout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import perso.logic.Bestiaire;
import perso.logic.Creature;

/**
 * Classe utilitaire responsable de l'import de créatures à partir de ressources texte.
 * <p>
 * Le format attendu pour chaque ligne de la ressource :
 * <ul>
 *   <li>(Nom) niveau | rareté (Parent1) (Parent2)  — pour une créature ayant des parents</li>
 *   <li>(Nom) niveau | rareté                      — pour une créature sans parents (rarete sur 1 caractère)</li>
 * </ul>
 * Les noms doivent être entourés de parenthèses, la rareté est un entier entre 1 et 6, et le niveau est un entier entre 1 et 35.
 * </p>
 */
public class Import {
    /**
     * Constructeur privé pour empêcher l'instanciation.
     */
    private Import() {
    }

    /**
     * Lit une ressource intégrée et la convertit en un {@link Bestiaire}.
     *
     * @param resourcePath chemin relatif de la ressource (ex: "/monfichier.txt")
     * @return un Bestiaire contenant les créatures décrites dans la ressource
     * @throws IOException si la ressource est introuvable ou qu'une erreur I/O survient lors de la lecture
     * @throws IllegalArgumentException si le format d'une ligne est invalide (mauvais nom, rareté hors bornes, etc.)
     */
    public static Bestiaire traduireRessource(String resourcePath) throws IOException {
        try (InputStream is = Import.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Ressource introuvable : " + resourcePath);
            }
            List<String> txt;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                txt = reader.lines().collect(Collectors.toList());
            }
            Bestiaire bestiaire = new Bestiaire();
            chargerCreature(txt, bestiaire);
            return bestiaire;
        }
    }

    /**
     * Analyse les lignes fournies et remplit le {@link Bestiaire} avec les créatures correspondantes.
     * <p>
     * Pour chaque ligne non vide :
     * <ol>
     *   <li>Isole le nom de la créature</li>
     *   <li>Lit la rareté (entier 1..6)</li>
     *   <li>Si la ligne contient des parents, récupère les créatures parents depuis le bestiaire</li>
     *   <li>Construit la {@code Creature} et l'ajoute au bestiaire</li>
     * </ol>
     * </p>
     *
     * @param txt la liste des lignes lues depuis la ressource
     * @param bestiaire le Bestiaire à remplir
     * @throws IllegalArgumentException si une ligne ne respecte pas le format attendu,
     *         si la rareté n'est pas dans [0,5], ou si le nom n'est pas entouré de parenthèses
     */
    private static void chargerCreature(List<String> txt, Bestiaire bestiaire) {
        for (String s : txt) {
            if (s.trim().isEmpty()) {
                continue; // ignore les lignes vides
            }
            //Vérifie si c'est une créature Manquante
            boolean manquante = s.substring(0,1).equals("-");
            if (manquante){
                s = s.substring(1);
            }
            //Partie Nom de la créature
            int pos = trouveEspace(s);
            String nomPart = isoleNom(s.substring(0, pos));
            s = s.substring(pos + 1);

            //Partie Rareté de la créature
            pos = trouveEspace(s);
            int raretePart = Integer.parseInt(s.substring(0, pos));
            if(raretePart < 1 || raretePart > 6){
                throw new IllegalArgumentException("Format de la rareté invalide : " + raretePart);
            }
            s = s.substring(pos+1);
            //Test si le format entre la rareté et le niveau est correct
            if (!s.startsWith("|")){
                throw new IllegalArgumentException("Format Incorrect, \"|\" attendu entre la rareté et le niveau : \"" + s + "\"");
            }  
            s = s.substring(2);
            //Partie Niveau de la créature
            pos = trouveEspace(s);
            int niveauPart = Integer.parseInt(s.substring(0, pos));
            if ((niveauPart<1 || niveauPart>35) && !manquante){
                throw new IllegalArgumentException("Niveau incorrect : " + s);
            }
            s = s.substring(pos + 1);

            Creature creature;
            if(s.length() == 0){
                if(manquante){
                    creature = new Creature(nomPart, raretePart);
                } else {
                    creature = new Creature(nomPart, raretePart, niveauPart);
                }
            } else {                
                //Partie Parent1 de la créature
                pos = trouveEspace(s);
                String parent1Part = isoleNom(s.substring(0, pos));
                Creature parent1 = bestiaire.getCreatureByName(parent1Part);
                //Partie Parent2 de la créature
                String parent2Part = isoleNom(s.substring(pos + 1));
                Creature parent2 = bestiaire.getCreatureByName(parent2Part);
                if(manquante){
                    creature = new Creature(nomPart, raretePart, parent1, parent2);
                } else {
                    creature = new Creature(nomPart, raretePart, niveauPart, parent1, parent2);
                }
            }
            bestiaire.addCreature(creature);
        }
    }

    /**
     * Retire la première et la dernière parenthèse d'une chaîne supposée être "(Nom)".
     *
     * @param s la chaîne source commençant par '(' et se terminant par ')'
     * @return la chaîne sans les parenthèses externes
     */
    private static String retireParentheses(String s){ return s.substring(1, s.length()-1);}

    /**
     * Vérifie que la chaîne fournie représente un nom entouré de parenthèses et retourne le nom interne.
     *
     * @param s la chaîne attendue sous la forme "(Nom)"
     * @return le nom sans les parenthèses
     * @throws IllegalArgumentException si la chaîne ne commence pas par '(' ou ne se termine pas par ')'
     */
    private static String isoleNom(String s){
        if (!s.startsWith("(") || !s.endsWith(")")) {
                    throw new IllegalArgumentException("Format du nom invalide : \"" + s + "\"");
                }
                return retireParentheses(s);
    }

    /**
     * Trouve la position du premier espace dans la chaîne.
     *
     * @param s la chaîne à analyser
     * @return l'index du premier caractère espace
     * @throws IllegalArgumentException si aucun espace n'est trouvé (format de ligne invalide)
     */
    private static int trouveEspace(String s) {
        int pos = s.indexOf(' ');
        if (pos == -1) {
            throw new IllegalArgumentException("Format de ligne invalide : \"" + s + "\"");
        }
        return pos;
    }
}