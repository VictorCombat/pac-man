
package VueControleur;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import modele.Jeu;
import modele.Niveau;


public class MenuNiveau extends AnchorPane
{
    private HBox topPane;
    private Button btnRetour;
    private Button btnAdd;
    private AnchorPane panAdd;
    private Label labTitre;
    private ScrollPane scrollPane;
    private VBox vBox;
    private Jeu jeu;
    private ArrayList<PanneauNiveau> tabPanNiveaux;
    
    public MenuNiveau(Jeu jeu) 
    {
        this.jeu = jeu;
        tabPanNiveaux = new ArrayList<>();
        
        // Bouton Retour
        btnRetour = new Button("MENU PRINCIPAL");
        btnRetour.setFont(new Font(15));
        btnRetour.setPrefSize(150, 45);
        AnchorPane.setTopAnchor(btnRetour, 15.0);
        AnchorPane.setLeftAnchor(btnRetour, 10.0);
        // Controleur bouton RETOUR
        btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnRetour.getScene().setRoot(new MenuPrincipal(jeu));
            }
        });
        btnRetour.setTextFill(Color.valueOf("#e7ecef"));
        btnRetour.setStyle
        (
                "-fx-padding: 5 5 5 5;" +
                "-fx-border-radius: 5;" +
                "-fx-background-color: black;" +
                "-fx-border-color: blue;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 1.1em;" +
                "-fx-border-width: 0.3em;" +
                "-fx-text-fill: white;"
        );
        
        
        // Label Titre
        labTitre = new Label("SELECTIONNER UN NIVEAU");
        labTitre.setFont(new Font(60));
        labTitre.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(labTitre, 10.0);
        AnchorPane.setLeftAnchor(labTitre, 200.0);
        AnchorPane.setRightAnchor(labTitre, 200.0);
        labTitre.setStyle
        (
                "-fx-text-fill: yellow;" + 
                "-fx-font-weight: bold;"
        );
        
        // VBox
        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setStyle
        (
                "-fx-background-color: black;"
        ); 
        
        // Ajoute le panneau +
        btnAdd = new Button("+");
        btnAdd.setFont(new Font(30));
        AnchorPane.setTopAnchor(btnAdd, 10.0);
        AnchorPane.setLeftAnchor(btnAdd, 200.0);
        AnchorPane.setRightAnchor(btnAdd, 200.0);
        AnchorPane.setBottomAnchor(btnAdd, 10.0);
        // Controleur bouton AJOUT
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnAdd.getScene().setRoot(new EditeurNiveauVue(jeu));
            }
        });
        btnAdd.setStyle
        (
                "-fx-padding: 10 10 10 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-color: black;" +
                "-fx-border-color: blue;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 2.4em;" +
                "-fx-border-width: 0.3em;" +
                "-fx-text-fill: white;"
        );
        panAdd = new AnchorPane(btnAdd);
        panAdd.setStyle
        (
                "-fx-background-color: black;"
        );
        
        //vBox.getChildren().add(panAdd);
        
        
        majNiveaux();
        
        
        // ScrollPane
        scrollPane = new ScrollPane(vBox);
        AnchorPane.setTopAnchor(scrollPane, 100.0);
        AnchorPane.setBottomAnchor(scrollPane, 5.0);
        AnchorPane.setLeftAnchor(scrollPane, 100.0);
        AnchorPane.setRightAnchor(scrollPane, 100.0);
        scrollPane.setStyle
        (
                "-fx-fit-to-height: true;" +
                "-fx-fit-to-width: true;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: #6096ba;"
        );
        
        
        this.setStyle("-fx-background-color: black;");
        this.getChildren().addAll(btnRetour, labTitre, scrollPane);
    }
    
    private void majNiveaux()
    {
        tabPanNiveaux.removeAll(tabPanNiveaux);
        vBox.getChildren().clear();
        
        jeu.chargerNiveaux();
        ArrayList<Niveau> niveaux = jeu.getNiveaux();
        for(int i=0; i<niveaux.size(); i++)
        {
            tabPanNiveaux.add(new PanneauNiveau(niveaux.get(i)));
            vBox.getChildren().add(tabPanNiveaux.get(i));
        }
        
        // Controleur PanneauNiveau
        for(int i=0; i<tabPanNiveaux.size(); i++)
        {
            final int indice = i;
            // BOUTON JOUER
            tabPanNiveaux.get(i).getBtnJouer().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) 
                {
                    tabPanNiveaux.get(indice).getScene().setRoot(new JeuVue(jeu, indice));
                }
            });
            
            // BOUTON EDITER
            tabPanNiveaux.get(i).getBtnEditer().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) 
                {
                    EditeurNiveauVue editNiv = new EditeurNiveauVue(jeu, tabPanNiveaux.get(indice).getNiveau());
                    tabPanNiveaux.get(indice).getScene().setRoot(editNiv);
                }
            });
            
            // BOUTON SUPPRIMER
            tabPanNiveaux.get(i).getBtnSuppr().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) 
                {
                    jeu.supprimerNiveau(tabPanNiveaux.get(indice).getNiveau().getPath());
                    majNiveaux();
                }
            });
        }
        
        vBox.getChildren().add(panAdd);
        
    }
    
    public ArrayList<PanneauNiveau> getTabPanNiveaux() {
        return tabPanNiveaux;
    }

    
    public Button getBtnRetour() {
        return btnRetour;
    }
    
    
    // Classe interne pour chaque panneau de niveau
    class PanneauNiveau extends AnchorPane
    {
        private Niveau niveau;
        private Label nom;
        private Label meilleurScore;
        private Button btnJouer;
        private Button btnEditer;
        private Button btnSuppr;
        private HBox hBox;
        
        public PanneauNiveau(Niveau niveau)
        {
            this.niveau = niveau;
            
            // Label nom
            nom = new Label(this.niveau.getNom());
            nom.setFont(new Font(22));
            nom.setAlignment(Pos.CENTER);
            AnchorPane.setTopAnchor(nom, 10.0);
            AnchorPane.setLeftAnchor(nom, 10.0);
            nom.setStyle
            (
                    "-fx-text-fill: white;" + 
                    "-fx-font-weight: bold"
            );
            
            // Label meilleur score
            meilleurScore = new Label();
            if(this.niveau.getMeilleurScore() == 0)
                meilleurScore.setText("Pas de meilleur score disponible !");
            else
                meilleurScore.setText("Meilleur score : " + this.niveau.getMeilleurScore());
            meilleurScore.setFont(new Font(16));
            meilleurScore.setAlignment(Pos.CENTER);
            AnchorPane.setTopAnchor(meilleurScore, 50.0);
            AnchorPane.setLeftAnchor(meilleurScore, 20.0);
            meilleurScore.setStyle
            (
                    "-fx-text-fill: yellow;" + 
                    "-fx-font-weight: bold"
            );
            
            // Button jouer
            btnJouer = new Button("JOUER");
            btnJouer.setFont(new Font(15));
            //btnJouer.setPrefHeight(30);
            btnJouer.setStyle
            (
                    "-fx-padding: 10 10 10 10;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 1.4em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            // Button editer
            btnEditer = new Button("EDITER");
            btnEditer.setFont(new Font(15));
            btnEditer.setPrefHeight(30);
            btnEditer.setStyle
            (
                    "-fx-padding: 10 10 10 10;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 1.4em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            // Button supprimer
            btnSuppr = new Button("SUPPRIMER");
            btnSuppr.setFont(new Font(15));
            btnSuppr.setPrefHeight(30);
            btnSuppr.setStyle
            (
                    "-fx-padding: 10 10 10 10;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 1.4em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            // HBox
            hBox = new HBox(30.0, btnJouer, btnEditer, btnSuppr);
            AnchorPane.setTopAnchor(hBox, 60.0);
            AnchorPane.setRightAnchor(hBox, 10.0);
            AnchorPane.setBottomAnchor(hBox, 10.0);
            
            //this.setStyle("-fx-fit-to-height: true;");
            //this.setStyle("-fx-fit-to-width: true;");
            
            this.setStyle
            (
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;"
            );
            this.getChildren().addAll(nom, meilleurScore, hBox);
        }

        public Niveau getNiveau() {
            return niveau;
        }

        public void setNiveau(Niveau niveau) {
            this.niveau = niveau;
        }
        
        public Button getBtnJouer() {
            return btnJouer;
        }

        public Button getBtnEditer() {
            return btnEditer;
        }

        public void setBtnEditer(Button btnEditer) {
            this.btnEditer = btnEditer;
        }

        public Button getBtnSuppr() {
            return btnSuppr;
        }

        public void setBtnSuppr(Button btnSuppr) {
            this.btnSuppr = btnSuppr;
        }
        
    }
}


