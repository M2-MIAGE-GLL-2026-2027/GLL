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

    static String choisirPremierJoueur(Scanner in) {
        while (true) {
            System.out.print("Choisir le premier joueur (C) ou tirer au sort (R) ? ");
            String mode = in.nextLine().trim().toUpperCase();

            if (mode.equals("R"))
                return new Random().nextBoolean() ? "X" : "O";

            if (mode.equals("C")) {
                while (true) {
                    System.out.print("Quel joueur commence (X ou O) ? ");
                    String choix = in.nextLine().trim().toUpperCase();
                    if (choix.equals("X") || choix.equals("O"))
                        return choix;
                    System.out.println("Choix invalide. Veuillez saisir X ou O.");
                }
            }

            System.out.println("Choix invalide. Veuillez saisir C ou R.");
        }
    }
 
    static int nbCoups;
    static boolean abandon;

    static String jouerPartie(Scanner in) {
        String winner = null;
        nbCoups = 0;
        abandon = false;
        echiquier = new String[9];

        for (int i = 0; i < 9; i++)
            echiquier[i] = String.valueOf(i + 1);
 
        System.out.println("Bienvenu au morpion.");
        symbol = choisirPremierJoueur(in);
        System.out.println("Le joueur " + symbol + " commence.");
        System.out.println("(Tapez Q pendant votre tour pour abandonner)");

        affichEchiquier();
        System.out.println("Joueur " + symbol + " commence."
            + " Placez " + symbol + " a la case:");

        while (winner == null) {

            String saisie = in.nextLine().trim();

            if (saisie.equalsIgnoreCase("Q")) {
                System.out.println("Le joueur " + symbol + " abandonne la partie.");
                winner = symbol.equals("X") ? "O" : "X";
                abandon = true;
                break;
            }

            try {
                int chiffre = Integer.parseInt(saisie);

                if(chiffre < 1 || chiffre > 9){
                    System.out.println("Veuillez entrer un chiffre entre 1 et 9");
                    continue;
                }

                if (echiquier[chiffre - 1].equals(String.valueOf(chiffre))) {
                    echiquier[chiffre - 1] = symbol;
                    nbCoups++;
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
        return winner;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenu au morpion.");

        boolean rejouer;
        do {
            String winner = jouerPartie(in);

            if (winner.equals("egalite"))
                System.out.println("Egalite en " + nbCoups + " coups !");
            else if (abandon)
                System.out.println("Victoire de " + winner + " par abandon apres " + nbCoups + " coups.");
            else
                System.out.println("Bravo a " + winner + " ! Victoire en " + nbCoups + " coups.");

            System.out.println("Voulez-vous rejouer ? (o/n)");
            String reponse = in.nextLine().trim().toLowerCase();
            rejouer = reponse.equals("o") || reponse.equals("oui");
        } while (rejouer);

        System.out.println("Merci d'avoir joue. A bientot !");
        in.close();
    }
}