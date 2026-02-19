package perso.logic;

public class Rarete {
    private int icI = 1;
    private int idC = 1;
    private int idR = 1;
    private int idE = 1;
    private int idL = 1;
    private int idU = 1;
    private int idS = 1;
    
    public String getId(int r){
        return switch (r) {
            case -1 -> getIdI();
            case  1 -> getIdC();
            case  2 -> getIdR();
            case  3 -> getIdE();
            case  4 -> getIdL();
            case  5 -> getIdU();
            case  6 -> getIdS();
            default -> throw new IllegalArgumentException("Rareté invalide : " + r);
        };
    }

    private String getIdI() {
        return "I"+icI++;
    }

    private String getIdC() {
        return "C"+idC++;
    }

    private String getIdR() {
        return "R"+idR++;
    }

    private String getIdE() {
        return "E"+idE++;
    }   

    private String getIdL() {
        return "L"+idL++;
    }

    private String getIdU() {
        return "U"+idU++;
    }

    private String getIdS() {
        return "S"+idS++;
    }

}
