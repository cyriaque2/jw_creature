package perso;

import java.io.IOException;

import perso.logic.Bestiaire;
import perso.logic.CalculsCout;
import perso.logic.CostScenario;
import perso.logic.inout.Import;

public class Main {
    public static void main(String[] args) throws IOException    {
        Bestiaire bestiaire = Import.traduireRessource("/perso/ressources/donnees.txt");
        //System.out.println(bestiaire);
        System.out.println(CalculsCout.calculCout(10, CostScenario.WORST_CASE, bestiaire.getCreatureByName("Indoraptor")));
    }
}