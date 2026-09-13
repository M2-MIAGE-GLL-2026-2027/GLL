import java.util.*;

public class Morpion {
   
    static String[] echiquier;
    static String symbol;
   
    static String evalGagnant() {
        for (int i = 0; i < 8; i++) {
            String ligne = null;
            switch (i) {
            case 0:
                ligne = echiquier[0] + echiquier[1] + echiquier[2];break;
            case 1:
                ligne = echiquier[3] + echiquier[4] + echiquier[5];break;
            case 2:
                ligne = echiquier[6] + echiquier[7] + echiquier[8];break;
            case 3:
                ligne = echiquier[0] + echiquier[3] + echiquier[6];break;
            case 4:
                ligne = echiquier[1] + echiquier[4] + echiquier[7];break;
            case 5:
                ligne = echiquier[2] + echiquier[5] + echiquier[8];break;
            case 6:
                ligne = echiquier[0] + echiquier[4] + echiquier[8];break;
            case 7:
                ligne = echiquier[2] + echiquier[4] + echiquier[6];break;
            }
            if (ligne.equals("XXX")) return "X";
            if (ligne.equals("OOO")) return "O";
        }

        for (int i = 0; i < 9; i++) {
            if (Arrays.asList(echiquier).contains(
                    String.valueOf(i + 1)))
                break;
            else if (i == 8)
                return "egalite";
        }
 
        System.out.println("Tour de " + symbol + "." +
            " Placez " + symbol + " a la case:");
        return null;
    }

     
    // Affichage de l'echiquier
    /* |---|---|---|
       | 1 | 2 | 3 |
       |-----------|
       | 4 | 5 | 6 |
       |-----------|
       | 7 | 8 | 9 |
       |---|---|---|*/

    static void affichEchiquier() {
        System.out.println("|---|---|---|");
        System.out.println("| " + echiquier[0] + " | "
            + echiquier[1] + " | " + echiquier[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + echiquier[3] + " | "
		    + echiquier[4] + " | " + echiquier[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + echiquier[6] + " | "
		    + echiquier[7] + " | " + echiquier[8] + " |");
        System.out.println("|---|---|---|");
    }
 
    public static void main(String[] args) {
        String winner = null;
        Scanner in = new Scanner(System.in);
        echiquier = new String[9];
        symbol = "X";
 
        for (int i = 0; i < 9; i++)
            echiquier[i] = String.valueOf(i + 1);
 
        System.out.println("Bienvenu au morpion.");
        affichEchiquier();
        System.out.println("Joueur " + symbol + " commence."
            + " Placez " + symbol + " a la case:");
 
        while (winner == null) {

            String saisie = in.nextLine().trim();

            try {
                int chiffre = Integer.parseInt(saisie);

                if(chiffre < 1 || chiffre > 9){
                    System.out.println("Veuillez entrer un chiffre entre 1 et 9");
                    continue;
                }

                if (echiquier[chiffre - 1].equals(String.valueOf(chiffre))) {
                    echiquier[chiffre - 1] = symbol;
                    symbol = symbol.equals("X") ? "O" : "X"; 
                    affichEchiquier();
                    winner = evalGagnant();
                }
                else
                    System.out.println("Case deja prise, entrez un autre chiffre");
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un chiffre entre 1 et 9.");
            }
        }
       
        if (winner.equals("egalite"))
            System.out.println("Egalite ! Merci d'avoir joue.");
        else
            System.out.println("Bravo a " + winner + ". Merci d'avoir joue.");
      in.close();
    }
}
