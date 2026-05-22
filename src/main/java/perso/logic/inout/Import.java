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

public class Import {

    private Import() {
    }

    public static Bestiaire traduireRessource(String resourcePath) throws IOException {
        try (InputStream is = Import.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Ressource introuvable : " + resourcePath);
            }
            List<String> txt;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                txt = reader.lines().collect(Collectors.toList());
            }
            Bestiaire bestiaire = Bestiaire.instance;
            chargerCreature(txt, bestiaire);
            return bestiaire;
        }
    }

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

    private static String retireParentheses(String s){ return s.substring(1, s.length()-1);}

    private static String isoleNom(String s){
        if (!s.startsWith("(") || !s.endsWith(")")) {
                    throw new IllegalArgumentException("Format du nom invalide : \"" + s + "\"");
                }
                return retireParentheses(s);
    }

    private static int trouveEspace(String s) {
        int pos = s.indexOf(' ');
        if (pos == -1) {
            throw new IllegalArgumentException("Format de ligne invalide : \"" + s + "\"");
        }
        return pos;
    }
}