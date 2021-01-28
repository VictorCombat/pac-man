
package VueControleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modele.Jeu;


public class MenuPrincipal extends AnchorPane
{
    private Jeu jeu;
    private MenuNiveau mn;
    private Label pacman;
    private Button btnJouer;
    private Button btnCreationNiveau;
    private Button btnQuitter;
    
    public MenuPrincipal(Jeu jeu) 
    {
        this.jeu = jeu;
        
        // Label PACMAN
        pacman = new Label("PACMAN");
        pacman.setFont(new Font(100));
        pacman.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(pacman, 75.0);
        AnchorPane.setRightAnchor(pacman, 50.0);
        AnchorPane.setLeftAnchor(pacman, 50.0);
        pacman.setStyle
        (
                "-fx-text-fill: yellow;" +
                "-fx-font-weight: bold;"
        );
        
        
        // Bouton 1 : JOUER
        btnJouer = new Button("JOUER");
        btnJouer.setFont(new Font(24));
        btnJouer.setAlignment(Pos.CENTER);
        btnJouer.setPrefSize(500, 50);
            // Controlleur BOUTON JOUER
            btnJouer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) 
                {
                    btnJouer.getScene().setRoot(new MenuNiveau(jeu));
                }
            });
        
        btnJouer.setStyle
        (
                "-fx-padding: 15 15 15 15;" +
                "-fx-border-radius: 5;" +
                "-fx-background-color: black;" +
                "-fx-border-color: blue;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 3.1em;" +
                "-fx-border-width: 0.3em;" +
                "-fx-text-fill: white;"
        );
        
        // Bouton 2 : Creation niveau
        btnCreationNiveau = new Button("CREATEUR DE NIVEAU");
        btnCreationNiveau.setFont(new Font(24));
        btnCreationNiveau.setAlignment(Pos.CENTER);
        btnCreationNiveau.setPrefSize(500, 50);
            // Controlleur BOUTON CREATEUR DE NIVEAU
            btnCreationNiveau.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    btnCreationNiveau.getScene().setRoot(new EditeurNiveauVue(jeu));
                }
            });
        
        btnCreationNiveau.setStyle
        (
                "-fx-padding: 15 15 15 15;" +
                "-fx-border-radius: 5;" +
                "-fx-background-color: black;" +
                "-fx-border-color: blue;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 3.1em;" +
                "-fx-border-width: 0.3em;" +
                "-fx-text-fill: white;"
        );
        
        // Bouton 3 : Quitter
        btnQuitter = new Button("QUITTER");
        btnQuitter.setFont(new Font(24));
        btnQuitter.setAlignment(Pos.CENTER);
        btnQuitter.setPrefSize(500, 50);
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
                "-fx-padding: 15 15 15 15;" +
                "-fx-border-radius: 5;" +
                "-fx-background-color: black;" +
                "-fx-border-color: blue;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 3.1em;" +
                "-fx-border-width: 0.3em;" +
                "-fx-text-fill: white;"
        );
        
        // VBox
        VBox vb = new VBox(30.0, btnJouer, btnCreationNiveau, btnQuitter);
        vb.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(vb, 250.0);
        AnchorPane.setRightAnchor(vb, 50.0);
        AnchorPane.setLeftAnchor(vb, 50.0);
        AnchorPane.setBottomAnchor(vb, 10.0);
        
        
        
        
        this.setStyle("-fx-background-color: black;");
        this.setPrefSize(700, 700);
        this.getChildren().addAll(pacman, vb);
        
    }
    
    public MenuNiveau getMenuNiveau(){
        return mn;
    }
}
