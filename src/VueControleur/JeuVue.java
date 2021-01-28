
package VueControleur;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import modele.Case;
import modele.Couloir;
import modele.Jeu;
import modele.Mur;
import modele.Niveau;
import modele.Entite;
import modele.Fantome;
import modele.Pacman;


public class JeuVue extends AnchorPane
{
    private Jeu jeu;
    private GridPane grille;
    private StackPane stackPaneCenter;
    private Image im_pacman;
    private Image im_mur;
    private Image im_couloir;
    private Image im_pacgomme;
    private Image im_superpacgomme;
    private ImageView im_v_pacman;
    private ImageView im_v_fantome1;
    private ImageView im_v_fantome2;
    private ImageView im_v_fantome3;
    private Image im_fantome_vuln_1;
    private Image im_fantome_vuln_2;
    private Image im_fantome_eyes;
    private boolean fantome_vuln;
    private ArrayList<ImageView> tabImFantome;
    
    private BorderPane menu;
    
    private Button btnMenuNiveau;
    private Button btnMenuPrincipal;
    private Button btnRecommencer;
    private Button btnReprendre;
    
    private int indiceNiveau;
    private Niveau currNiveau;
    private Label lbScore;
    private HBox hBoxVie;
    private int nbGomm;
    
    public JeuVue(Jeu jeu, int indiceNiveau)
    {
        
        // Initialise l'indice du niveau
        this.indiceNiveau = indiceNiveau;
        
        tabImFantome = new ArrayList<>();
        fantome_vuln = false;
        try {
            // Recupere les images
            // Mur
            im_mur = new Image(new URL(getClass().getResource("/resources/img/mur.png").toExternalForm()).toExternalForm());
            // Couloir
            im_couloir = new Image(new URL(getClass().getResource("/resources/img/couloir.png").toExternalForm()).toExternalForm());
            // Pacman
            im_pacman = new Image(new URL(getClass().getResource("/resources/img/pacman.png").toExternalForm()).toExternalForm());
            // Pac-Gomme
            im_pacgomme = new Image(new URL(getClass().getResource("/resources/img/pacgomme.png").toExternalForm()).toExternalForm());
            // Super Pac-Gomme
            im_superpacgomme = new Image(new URL(getClass().getResource("/resources/img/superpacgomme.png").toExternalForm()).toExternalForm());
            // ImageView pacman
            im_v_pacman = new ImageView(im_pacman);
            // ImageView fantome1
            im_v_fantome1 = new ImageView(new Image(new URL(getClass().getResource("/resources/img/fantome1.png").toExternalForm()).toExternalForm()));
            // ImageView fantome2
            im_v_fantome2 = new ImageView(new Image(new URL(getClass().getResource("/resources/img/fantome2.png").toExternalForm()).toExternalForm()));
            // ImageView fantome3
            im_v_fantome3 = new ImageView(new Image(new URL(getClass().getResource("/resources/img/fantome3.png").toExternalForm()).toExternalForm()));
            // Image fantome vulnerable 1
            im_fantome_vuln_1 = new Image(new URL(getClass().getResource("/resources/img/fantome_vulnerable_1.png").toExternalForm()).toExternalForm());
            // Image fantome vulnerable 2
            im_fantome_vuln_2 = new Image(new URL(getClass().getResource("/resources/img/fantome_vulnerable_2.png").toExternalForm()).toExternalForm());
            // Image fantome eye
            im_fantome_eyes = new Image(new URL(getClass().getResource("/resources/img/fantome_eyes.png").toExternalForm()).toExternalForm());
            
            tabImFantome.add(im_v_fantome1);
            tabImFantome.add(im_v_fantome2);
            tabImFantome.add(im_v_fantome3);
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(JeuVue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Initialise Jeu
        this.jeu = jeu;
        
        
        // Initialise StackPaneCenter
        stackPaneCenter = new StackPane();
        // Initialise GridPane
        grille = new GridPane();
        initGrille();
        grille.setStyle("-fx-background-color: black;");
        
        // StackPaneCenter + menu
        stackPaneCenter.getChildren().add(grille);
        AnchorPane.setTopAnchor(stackPaneCenter, 1.0);
        AnchorPane.setRightAnchor(stackPaneCenter, 1.0);
        AnchorPane.setLeftAnchor(stackPaneCenter, 1.0);
        AnchorPane.setBottomAnchor(stackPaneCenter, 1.0);
        
        // MENU EN JEU
        menu = new BorderPane();
            AnchorPane topMenu = new AnchorPane();
            // Label Pause
            Label lbPause = new Label("PAUSE");
            lbPause.setFont(new Font(75));
            lbPause.setAlignment(Pos.CENTER);
            lbPause.setTextAlignment(TextAlignment.CENTER);
            lbPause.setTextFill(Color.YELLOW);
            AnchorPane.setTopAnchor(lbPause, 50.0);
            AnchorPane.setRightAnchor(lbPause, 50.0);
            AnchorPane.setLeftAnchor(lbPause, 50.0);
            AnchorPane.setBottomAnchor(lbPause, 1.0);
            topMenu.getChildren().add(lbPause);
            
            AnchorPane centerMenu = new AnchorPane();
            // Button reprendre
            btnReprendre = new Button("Reprendre");
            btnReprendre.setFont(new Font(20));
            btnReprendre.setPrefSize(300, 50);
            btnReprendre.setAlignment(Pos.CENTER);
            // Controleur bouton REPRENDRE
            btnReprendre.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menu.setVisible(false);
                    
                    // REVEILLE LES THREADS
                    currNiveau.reveilleThread();
                }
            });
            btnReprendre.setStyle
            (
                    "-fx-padding: 7 7 7 7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.1em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            // Button recommencer
            btnRecommencer = new Button("Recommencer");
            btnRecommencer.setFont(new Font(20));
            btnRecommencer.setPrefSize(300, 50);
            btnRecommencer.setAlignment(Pos.CENTER);
            // Controleur bouton RECOMMENCER
            btnRecommencer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menu.setVisible(false);
                    
                    // Reveille tous les threads
                    currNiveau.reveilleThread();
                    
                    // Stop tous les threads
                    currNiveau.finPartie();
                    
                    // Reinitialise le niveau
                    currNiveau = new Niveau(currNiveau.getPath());
                    jeu.setCurrNiveau(currNiveau);
                    currNiveau.lancerPartie();
                    ajoutObserver();
                    
                }
            });
            btnRecommencer.setStyle
            (
                    "-fx-padding: 7 7 7 7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.1em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            
            // Button menu niveau
            btnMenuNiveau = new Button("Choisir niveau");
            btnMenuNiveau.setFont(new Font(20));
            btnMenuNiveau.setPrefSize(300, 50);
            btnMenuNiveau.setAlignment(Pos.CENTER);
             // Controleur bouton CHOISIR NIVEAU
            btnMenuNiveau.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menu.setVisible(false);
                    
                    // REVEILLE LES THREADS
                    currNiveau.reveilleThread();
                    // On met fin a la partie
                    currNiveau.finPartie();
                    
                    btnMenuNiveau.getScene().setRoot(new MenuNiveau(jeu));
                    
                }
            });
            btnMenuNiveau.setStyle
            (
                    "-fx-padding: 7 7 7 7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.1em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            // Button menu principal
            btnMenuPrincipal = new Button("Menu principal");
            btnMenuPrincipal.setFont(new Font(20));
            btnMenuPrincipal.setPrefSize(300, 50);
            btnMenuPrincipal.setAlignment(Pos.CENTER);
             // Controleur bouton MENU PRINCIPAL
            btnMenuPrincipal.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menu.setVisible(false);
                    
                    // REVEILLE LES THREADS
                    currNiveau.reveilleThread();
                    // On met fin a la partie
                    currNiveau.finPartie();
                    
                    btnMenuPrincipal.getScene().setRoot(new MenuPrincipal(jeu));
                }
            });
            btnMenuPrincipal.setStyle
            (
                    "-fx-padding: 7 7 7 7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-color: black;" +
                    "-fx-border-color: blue;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 2.1em;" +
                    "-fx-border-width: 0.3em;" +
                    "-fx-text-fill: white;"
            );
            
            // VBox MENU
            VBox vbMenu = new VBox(30.0, btnReprendre, btnRecommencer, btnMenuNiveau, btnMenuPrincipal);
            //vbMenu.setStyle("-fx-background-color: green;");
            vbMenu.setAlignment(Pos.CENTER);
            AnchorPane.setTopAnchor(vbMenu, 1.0);
            AnchorPane.setRightAnchor(vbMenu, 50.0);
            AnchorPane.setLeftAnchor(vbMenu, 50.0);
            AnchorPane.setBottomAnchor(vbMenu, 1.0);
            centerMenu.getChildren().add(vbMenu);
            
        menu.setTop(topMenu);
        menu.setCenter(centerMenu);
        menu.setVisible(false);
        menu.setStyle("-fx-background-color: rgba(35, 35, 35, 0.8);");
        menu.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        stackPaneCenter.getChildren().add(menu);
        
        
        
        // Declaration panneaux du borderPane
        AnchorPane topPane = new AnchorPane();
        AnchorPane leftPane = new AnchorPane();
        AnchorPane centerPane = new AnchorPane();
        AnchorPane rightPane = new AnchorPane();
        AnchorPane bottomPane = new AnchorPane();
        
        BorderPane border = new BorderPane();
        AnchorPane.setTopAnchor(border, 1.0);
        AnchorPane.setRightAnchor(border, 1.0);
        AnchorPane.setLeftAnchor(border, 1.0);
        AnchorPane.setBottomAnchor(border, 1.0);
        
        // Top Pane
            // Composant Top Pane
            // Bouton menu
            Button btnMenu = new Button("MENU");
            btnMenu.setFont(new Font(15));
            btnMenu.setPrefSize(75, 35);
            btnMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menu.setVisible(true);
                // MET EN PAUSE LES THREADS
                for(int i=0; i<currNiveau.getTabEntite().length; i++)
                {
                    currNiveau.getTabEntite()[i].pause();
                }
            }
        });
            btnMenu.setStyle
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
            
            // Label Meilleur score
            Label meilleurScore = new Label("Meilleur score : " + currNiveau.getMeilleurScore());
            meilleurScore.setFont(new Font(25));
            meilleurScore.setAlignment(Pos.CENTER);
            meilleurScore.setStyle
            (
                    "-fx-text-fill: yellow;" +
                    "-fx-font-weight: bold;"
            );
            
            // Label score
            Pacman pacman = (Pacman) currNiveau.getTabEntite()[0];
            lbScore = new Label("Score : " + pacman.getScore());
            lbScore.setFont(new Font(25));
            lbScore.setAlignment(Pos.CENTER);
            lbScore.setStyle
            (
                    "-fx-text-fill: white;" +
                    "-fx-font-weight: bold;"
            );
            
            // HBox vie
            hBoxVie = new HBox(10.0);
            for(int i=0; i<pacman.getNbVie(); i++)
            {
                hBoxVie.getChildren().add(new ImageView(im_pacman));
            }
            
            hBoxVie.setStyle("-fx-background-color: black;");
            
            // HBox principale
            HBox hbMain = new HBox(60.0, btnMenu, meilleurScore, lbScore, hBoxVie);
            AnchorPane.setTopAnchor(hbMain, 5.0);
            AnchorPane.setLeftAnchor(hbMain, 5.0);
            AnchorPane.setRightAnchor(hbMain, 5.0);
            AnchorPane.setBottomAnchor(hbMain, 5.0);
            hbMain.setStyle("-fx-background-color: black;");
            
        
        topPane.setStyle("-fx-background-color: black; -fx-border-color: blue;");
        topPane.getChildren().addAll(hbMain);
        topPane.setPrefHeight(50);
        
        // Left Pane  
        leftPane.setStyle("-fx-background-color: black;");
        leftPane.setPrefWidth(150);

        
        // CenterPane
        centerPane.setStyle("-fx-background-color: black;");
        grille.setAlignment(Pos.CENTER);
        centerPane.getChildren().add(stackPaneCenter);
        centerPane.setPrefSize(200, 150);
        
        
        // RightPane
        rightPane.setStyle("-fx-background-color: black;");
        rightPane.setPrefWidth(100);
        
        // BottomPane
        bottomPane.setStyle("-fx-background-color: black;");
        bottomPane.setPrefHeight(100);
        
        border.setTop(topPane);
        border.setCenter(centerPane);
        
        border.setFocusTraversable(true);
        this.setFocusTraversable(true);
        
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(null != event.getCode())
                {
                    switch(event.getCode())
                    {
                        case UP: currNiveau.getTabEntite()[0].setCurrentDirection("haut");
                            break;
                            
                        case LEFT: currNiveau.getTabEntite()[0].setCurrentDirection("gauche");
                            break;
                            
                        case RIGHT: currNiveau.getTabEntite()[0].setCurrentDirection("droite");
                            break;
                            
                        case DOWN: currNiveau.getTabEntite()[0].setCurrentDirection("bas");
                            break;
                            
                        case ESCAPE:
                            break;
                            
                        default: break;
                    }
                }
            }
        });
        
        
        
        
        
        this.getChildren().addAll(border);
            
        ajoutObserver();
        // Lance la partie
        currNiveau.lancerPartie();
        
        
    }
    
    private void ajoutObserver()
    {
        // Pattern observer -> update
        Entite[] tabEntites = currNiveau.getTabEntite();
        for(int i=0; i<tabEntites.length; i++)
        {
            tabEntites[i].addObserver(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    // Change de thread si on est pas dans le main thread
                    if(!Platform.isFxApplicationThread())
                    {
                        Platform.runLater(() -> update(o, arg));
                        return;
                    }
                    updateGrille();
                    updateScore();
                    updateVie();
                }
            });
        }
    }
    
    private void initGrille()
    {
        // recupere le niveau au bon indice
        currNiveau = jeu.getNiveaux().get(indiceNiveau);
        jeu.setCurrNiveau(currNiveau);
        
        // recupere le plateau
        Case[][] plateau = currNiveau.getPlateau();
        
        for(int i=0; i < plateau.length; i++)
        {
            for(int j=0; j < plateau[i].length; j++)
            {
                // Si c'est un mur
                if(plateau[i][j] instanceof Mur)
                {
                    grille.add(new ImageView(im_mur), i, j);
                }
                // Si c'est un couloir
                else if(plateau[i][j] instanceof Couloir)
                {
                    // On ajoute le couloir
                    grille.add(new ImageView(im_couloir), i, j);
                    Couloir couloir = (Couloir) plateau[i][j];
                    // Si c'est une pac gomme
                    if(couloir.isPacGomme())
                    {
                        grille.add(new ImageView(im_pacgomme), i, j);
                    }
                    // si c'est une super pac gomme
                    else if(couloir.isSuperPacGomme())
                    {
                        grille.add(new ImageView(im_superpacgomme), i, j);
                    }
                }
            }
        }
        
        // PACMAN
        grille.add(im_v_pacman, currNiveau.getTabEntite()[0].getCurrPos().getX(), currNiveau.getTabEntite()[0].getCurrPos().getY());
        
        // FANTOME 1 (rouge)
        grille.add(im_v_fantome1, currNiveau.getTabEntite()[1].getCurrPos().getX(), currNiveau.getTabEntite()[1].getCurrPos().getY());
        
        // FANTOME 2 (orange)
        grille.add(im_v_fantome2, currNiveau.getTabEntite()[2].getCurrPos().getX(), currNiveau.getTabEntite()[2].getCurrPos().getY());
        
        // FANTOME 3 (bleu)
        grille.add(im_v_fantome3, currNiveau.getTabEntite()[3].getCurrPos().getX(), currNiveau.getTabEntite()[3].getCurrPos().getY());
    }
    
    private void updateGrille()
    {
        // Supprimer toute la grille
        grille.getChildren().removeAll(grille.getChildren());
        
        Case[][] plateau = currNiveau.getPlateau();
        for(int i=0; i < plateau.length; i++)
        {
            for(int j=0; j < plateau[i].length; j++)
            {
                // Si c'est un mur
                if(plateau[i][j] instanceof Mur)
                {
                    grille.add(new ImageView(im_mur), i, j);
                }
                // Si c'est un couloir
                else if(plateau[i][j] instanceof Couloir)
                {
                    // On ajoute le couloir
                    grille.add(new ImageView(im_couloir), i, j);
                    Couloir couloir = (Couloir) plateau[i][j];
                    // Si c'est une pac gomme
                    if(couloir.isPacGomme())
                    {
                        grille.add(new ImageView(im_pacgomme), i, j);
                    }
                    // si c'est une super pac gomme
                    else if(couloir.isSuperPacGomme())
                    {
                        grille.add(new ImageView(im_superpacgomme), i, j);
                    }
                }
            }
        }
        
        // PACMAN
        switch(currNiveau.getTabEntite()[0].getCurrentDirection())
        {
            case "haut": im_v_pacman.setRotate(270);
                break;
                
            case "gauche": im_v_pacman.setRotate(180);
                break;
                
            case "droite": im_v_pacman.setRotate(0);
                break;
                
            case "bas": im_v_pacman.setRotate(90);
                break;
                
            default: break;
        }
        grille.add(im_v_pacman, currNiveau.getTabEntite()[0].getCurrPos().getX(), currNiveau.getTabEntite()[0].getCurrPos().getY());
        
        
        // Recup tabFantome
        Entite[] tabFantome = 
            {   currNiveau.getTabEntite()[1],
                currNiveau.getTabEntite()[2],
                currNiveau.getTabEntite()[3]
            };
        
        for(int i=0; i<tabFantome.length; i++)
        {
            Fantome fantome = (Fantome) tabFantome[i];
            if(fantome.isVulnerable())
            {
                if(fantome_vuln)
                {
                    grille.add(new ImageView(im_fantome_vuln_1), tabFantome[i].getCurrPos().getX(), tabFantome[i].getCurrPos().getY());
                    fantome_vuln = false;
                }
                else
                {
                    grille.add(new ImageView(im_fantome_vuln_2), tabFantome[i].getCurrPos().getX(), tabFantome[i].getCurrPos().getY());
                    fantome_vuln = true;
                }
                
            }
            else if(fantome.isMort())
            {
                grille.add(new ImageView(im_fantome_eyes), tabFantome[i].getCurrPos().getX(), tabFantome[i].getCurrPos().getY());
            }
            else
            {
                grille.add(tabImFantome.get(i), tabFantome[i].getCurrPos().getX(), tabFantome[i].getCurrPos().getY());
            }
        }
    }
    
    private void updateScore()
    {
        Pacman pacman = (Pacman) currNiveau.getTabEntite()[0];
        int score = pacman.getScore();
        
        // On a gagné
        if(pacman.getGomms() == currNiveau.getNbPacGoms())
        {
            currNiveau.finPartie();
            getScene().setRoot(new MenuFinPartie(jeu, true));
        }
        lbScore.setText("Score : " + score);
    }
    
    private void updateVie()
    {
        hBoxVie.getChildren().removeAll(hBoxVie.getChildren());
        Pacman pacman = (Pacman) currNiveau.getTabEntite()[0];
        for(int i=0; i<pacman.getNbVie(); i++)
        {
            hBoxVie.getChildren().add(new ImageView(im_pacman));
        }
        // On a perdu
        if(pacman.getNbVie() == 0)
        {
            currNiveau.finPartie();
            getScene().setRoot(new MenuFinPartie(jeu, false));
        }
        
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) 
    {
        for (Node node : gridPane.getChildren()) 
        {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
            {
                return node;
            }
                
        }
        return null;
    }
    
    public Niveau getCurrNiveau(){
        return currNiveau;
    }

    public int getNbGomm() {
        return nbGomm;
    }

    public void setNbGomm(int nbGomm) {
        this.nbGomm = nbGomm;
    }
    
}
