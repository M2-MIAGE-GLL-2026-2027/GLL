import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MorpionUI extends Application {
    private final Button[] cases = new Button[9];
    private final Label message = new Label();
    private String winner;

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
            bouton.setStyle("-fx-font-size: 32px;");
            final int position = i;
            bouton.setOnAction(event -> jouer(position));
            cases[i] = bouton;
            grille.add(bouton, i % 3, i / 3);
        }

        Button nouvellePartie = new Button("Nouvelle partie");
        nouvellePartie.setOnAction(event -> construirePartie());

        VBox contenu = new VBox(15, message, grille, nouvellePartie);
        contenu.setAlignment(Pos.CENTER);
        contenu.setPadding(new Insets(20));

        stage.setTitle("Morpion");
        stage.setScene(new Scene(contenu));
        stage.show();
    }

    private void construirePartie() {
        Morpion.echiquier = new String[9];
        Morpion.symbol = "X";
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

        Morpion.symbol = Morpion.symbol.equals("X") ? "O" : "X";
        winner = Morpion.evalGagnant();

        if (winner == null) {
            message.setText("Tour de " + Morpion.symbol);
        } else if (winner.equals("egalite")) {
            message.setText("Egalite !");
            desactiverCases();
        } else {
            message.setText("Bravo, " + winner + " a gagne !");
            desactiverCases();
        }
    }

    private void desactiverCases() {
        for (Button bouton : cases) {
            bouton.setDisable(true);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
