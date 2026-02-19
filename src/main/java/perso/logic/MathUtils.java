package perso.logic;

public final class MathUtils {

    private MathUtils() {
        // classe utilitaire, pas d'instance
    }

    public static int divsup(int a,int b){ //pour diviser a par b mais en arrondissant vers la valeur supérieure
        if(a%b!=0){
            return (a/b)+1;
        }
        return a/b;
    }
}
