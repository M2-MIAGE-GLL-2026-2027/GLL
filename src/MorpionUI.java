import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MorpionUI extends Application {
    private final Button[] cases = new Button[9];
    private final Label message = new Label();
    private final CheckBox modeOrdi = new CheckBox("Jouer contre l'ordinateur");
    private final Button boutonTheme = new Button();
    private VBox contenu;
    private HBox boutons;
    private String winner;
        private boolean themeSombre = false;

    @Override
    public void start(Stage stage) {
        construirePartie();

        GridPane grille = new GridPane();
        grille.setAlignment(Pos.CENTER);
        grille.setHgap(5);
        grille.setVgap(5);

        for (int i = 0; i < cases.length; i++) {
            Button bouton = new Button();
            bouton.setPrefSize(100, 100);
            bouton.setStyle("-fx-font-size: 32px; -fx-opacity: 1;");
            final int position = i;
            bouton.setOnAction(event -> jouer(position));
            cases[i] = bouton;
            grille.add(bouton, i % 3, i / 3);
        }

        Button nouvellePartie = new Button("Nouvelle partie");
        nouvellePartie.setOnAction(event -> construirePartie());

        // Bouton Quitter : affiche le bilan puis ferme l'application
        Button quitter = new Button("Quitter");
        quitter.setOnAction(event -> {
            afficherBilan();
            Platform.exit();
        });

         boutonTheme.setOnAction(event -> {
            themeSombre = !themeSombre;
            appliquerTheme();
        });

        // Fermeture par la croix de la fenetre : affiche aussi le bilan
        stage.setOnCloseRequest(event -> afficherBilan());

        boutons = new HBox(10, nouvellePartie, quitter);
        boutons.setAlignment(Pos.CENTER);

        contenu = new VBox(15, message, modeOrdi, grille, boutons, boutonTheme);
        contenu.setAlignment(Pos.CENTER);
        contenu.setPadding(new Insets(20));

        stage.setTitle("Morpion");
        stage.setScene(new Scene(contenu));
        appliquerTheme();
        stage.show();
    }

    private void appliquerTheme() {
        String fond, texte, fondCase, bordure, fondBouton;

        if (themeSombre) {
            fond = "#1E1E1E";
            texte = "#FFFFFF";
            fondCase = "#333333";
            bordure = "#555555";
            fondBouton = "#444444";
            boutonTheme.setText("☀ Thème clair");
        } else {
            fond = "#F4F4F4";
            texte = "#222222";
            fondCase = "#FFFFFF";
            bordure = "#CCCCCC";
            fondBouton = "#E0E0E0";
            boutonTheme.setText("☾ Thème sombre");
        }
        contenu.setStyle("-fx-background-color: " + fond + ";");
        message.setStyle("-fx-text-fill: " + texte + "; -fx-font-size: 20px;");
        modeOrdi.setStyle("-fx-text-fill: " + texte + ";");

        String styleBouton =
            "-fx-background-color: " + fondBouton + ";" +
            "-fx-text-fill: " + texte + ";" +
            "-fx-border-color: " + bordure + ";";

        boutonTheme.setStyle(styleBouton);

        for (Button bouton : cases) {
            bouton.setStyle(
                "-fx-font-size: 32px;" +
                "-fx-background-color: " + fondCase + ";" +
                "-fx-text-fill: " + texte + ";" +
                "-fx-border-color: " + bordure + ";"
            );
        }

        for (javafx.scene.Node node : boutons.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setStyle(styleBouton);
            }
        }
    }

    private void construirePartie() {
        Morpion.echiquier = new String[9];
        Morpion.symbol = "X";
        Morpion.nbCoups = 0;
        winner = null;

        for (int i = 0; i < Morpion.echiquier.length; i++) {
            Morpion.echiquier[i] = String.valueOf(i + 1);
            if (cases[i] != null) {
                cases[i].setText("");
                cases[i].setDisable(false);
            }
        }

        message.setText("Tour de X");
    }

    private void jouer(int position) {
        Morpion.echiquier[position] = Morpion.symbol;
        cases[position].setText(Morpion.symbol);
        cases[position].setDisable(true);
        Morpion.nbCoups++;

        Morpion.symbol = Morpion.symbol.equals("X") ? "O" : "X";
        winner = Morpion.evalGagnant();

        if (winner == null) {
            message.setText("Tour de " + Morpion.symbol);
            if (modeOrdi.isSelected() && Morpion.symbol.equals("O")) {
                int coupOrdi = Morpion.coupOrdinateur() - 1;
                jouer(coupOrdi);
            }
        } else if (winner.equals("egalite")) {
            Morpion.enregistrerPartie(winner);
            message.setText("Egalite !");
            desactiverCases();
        } else {
            Morpion.enregistrerPartie(winner);
            message.setText("Bravo, " + winner + " a gagne !");
            desactiverCases();
        }
    }

    private void desactiverCases() {
        for (Button bouton : cases) {
            bouton.setDisable(true);
        }
    }

    // Affiche le bilan de la session dans une fenetre
    private void afficherBilan() {
        Alert bilan = new Alert(Alert.AlertType.INFORMATION);
        bilan.setTitle("Bilan de la session");
        bilan.setHeaderText("Merci d'avoir joue !");
        bilan.setContentText(Morpion.texteBilan());
        bilan.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}