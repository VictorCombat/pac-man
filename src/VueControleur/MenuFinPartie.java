
package VueControleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modele.Jeu;


public class MenuFinPartie extends AnchorPane
{
    private boolean isVictorious;
    private Jeu jeu;
    private Button btnChoixNiveau;
    private Button btnCreationNiveau;
    private Button btnQuitter;
    private Label lb;
    
    public MenuFinPartie(Jeu jeu, boolean isVictorious) 
    {
        this.isVictorious = isVictorious;
        this.jeu = jeu;
        
        // Bouton : Choix Niveau
        btnChoixNiveau = new Button("CHOIX NIVEAUX");
        btnChoixNiveau.setFont(new Font(24));
        btnChoixNiveau.setAlignment(Pos.CENTER);
        btnChoixNiveau.setPrefSize(400, 50);
            // Controlleur BOUTON CHOIX NIVEAU
            btnChoixNiveau.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    btnChoixNiveau.getScene().setRoot(new MenuNiveau(jeu));
                }
            });
            btnChoixNiveau.setStyle
            (
                    "-fx-padding: 10 10 10 10;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.8em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
        
        // Bouton : Creation niveau
        btnCreationNiveau = new Button("CREATEUR DE NIVEAU");
        btnCreationNiveau.setFont(new Font(24));
        btnCreationNiveau.setAlignment(Pos.CENTER);
        btnCreationNiveau.setPrefSize(400, 50);
            // Controlleur BOUTON CREATEUR DE NIVEAU
            btnCreationNiveau.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    btnCreationNiveau.getScene().setRoot(new EditeurNiveauVue(jeu));
                }
            });
            btnCreationNiveau.setStyle
            (
                    "-fx-padding: 10 10 10 10;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.8em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
        
        // Bouton : Quitter
        btnQuitter = new Button("QUITTER");
        btnQuitter.setFont(new Font(24));
        btnQuitter.setAlignment(Pos.CENTER);
        btnQuitter.setPrefSize(400, 50);
            // Controlleur BOUTON QUITTER
            btnQuitter.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage stage = (Stage) btnQuitter.getScene().getWindow();
                    stage.close();
                }
            });
            btnQuitter.setStyle
            (
                    "-fx-padding: 10 10 10 10;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.8em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
        
        
        // Label
        lb = new Label();
        lb.setFont(new Font(56));
        lb.setAlignment(Pos.CENTER);
        if(this.isVictorious)
        {
            lb.setText("VOUS AVEZ GAGNÉ !");
            lb.setTextFill(Color.GREEN);
        }
        else
        {
            lb.setText("VOUS AVEZ PERDU !");
            lb.setTextFill(Color.RED);

        }
        
        // VBox
        VBox vb = new VBox(15.0, lb, btnChoixNiveau, btnCreationNiveau, btnQuitter);
        vb.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(vb, 1.0);
        AnchorPane.setRightAnchor(vb, 50.0);
        AnchorPane.setLeftAnchor(vb, 50.0);
        AnchorPane.setBottomAnchor(vb, 1.0);
        
        
        // AnchorPane (this)
        this.setStyle("-fx-background-color: black;");
        this.setPrefSize(700, 550);
        this.getChildren().addAll(vb);
    
    }
    
}
