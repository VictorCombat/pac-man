
package VueControleur;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import modele.Jeu;
import modele.Niveau;

public class EditeurNiveauVue extends AnchorPane
{
    private GridPane grille;
    private int nbColonne;
    private int nbLigne;
    private Jeu jeu;
    private AnchorPane paneInfo;
    private Label lbInfo;
    private Spinner<Integer> spinX;
    private Spinner<Integer> spinY;
    private String nom;
    
    // IMAGE
    private Image im_pacman;
    private Image im_mur;
    private Image im_couloir;
    private Image im_pacgomme;
    private Image im_superpacgomme;
    private Image im_fantome1;
    private Image im_fantome2;
    private Image im_fantome3;
    private ImageView im_v_pacman;
    private ImageView im_v_fantome1;
    private ImageView im_v_fantome2;
    private ImageView im_v_fantome3;
    private ImageView im_v_mur;
    private ImageView im_v_couloir;
    private ImageView im_v_superpacgomme;
    private ImageView im_v_pacgomme;
    
    private Image image_selec;
    
    private ArrayList<PanneauImage> tabPanIm;
    private ArrayList<ArrayList<Cellule>> tabCellule;
    
    public EditeurNiveauVue(Jeu jeu)
    {
        this.jeu = jeu;
        
        nbColonne = 20;
        nbLigne = 15;
        nom = "";
        
        init();
        initGrille();
        
        // Controleurs Cellule
        for(int i=0; i<tabCellule.size(); i++)
        {
            for(int j=0; j<tabCellule.get(i).size(); j++)
            {
                Cellule cell = tabCellule.get(i).get(j);
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        if(image_selec != null)
                        {
                            // Si l'image a mettre est un mur
                            if(image_selec == im_mur)
                                cell.getIm().setImage(image_selec);
                            // Si l'image a mettre est un pacman
                            else if(image_selec == im_pacman)
                            {
                                // Si un pacman est deja la
                                Cellule pacman = estDansGrille(im_pacman);
                                if(pacman != null)
                                {
                                    // On met un couloir a la position du pacman
                                    pacman.getIm().setImage(im_couloir);
                                    
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme ||
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                // Si y'a pas de pacman
                                else
                                {
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme ||
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                
                            }
                            // Si l'image a mettre est un couloir
                            else if(image_selec == im_couloir)
                            {
                                cell.getIm().setImage(image_selec);
                            }
                            // Si l'image a mettre est un fantome
                            else if(image_selec == im_fantome1 || image_selec == im_fantome2 || image_selec == im_fantome3)
                            {
                                // Si le fantome est deja present
                                Cellule fantome = estDansGrille(image_selec);
                                if(fantome != null)
                                {
                                    // On met un couloir a la place de l'ancienne pos du fantome
                                    fantome.getIm().setImage(im_couloir);
                                    
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme || 
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                // Si y'a pas le fantome
                                else
                                {
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme || 
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                
                            }
                            // Si l'image a mettre est une pac-gomme ou une super-pac-gomme
                            else if(image_selec == im_pacgomme || image_selec == im_superpacgomme)
                            {
                                // Si l'image target est un couloir, une pac-gomme ou une super pac-gomme
                                if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme || 
                                   cell.getIm().getImage() == im_superpacgomme)
                                {
                                    cell.getIm().setImage(image_selec);
                                }
                            }

                            event.consume();
                        }
                    }
                });
            }
        }
    }
    
    public EditeurNiveauVue(Jeu jeu, Niveau niveau)
    {
        this.jeu = jeu;
        
        nbColonne = niveau.getNbColonne();
        nbLigne = niveau.getNbLigne();
        nom = niveau.getNom();
        
        init();
        parseIntoCell(niveau.getTabStrPlateau());
        
        // Controleurs Cellule
        for(int i=0; i<tabCellule.size(); i++)
        {
            for(int j=0; j<tabCellule.get(i).size(); j++)
            {
                Cellule cell = tabCellule.get(i).get(j);
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        if(image_selec != null)
                        {
                            // Si l'image a mettre est un mur
                            if(image_selec == im_mur)
                                cell.getIm().setImage(image_selec);
                            // Si l'image a mettre est un pacman
                            else if(image_selec == im_pacman)
                            {
                                // Si un pacman est deja la
                                Cellule pacman = estDansGrille(im_pacman);
                                if(pacman != null)
                                {
                                    // On met un couloir a la position du pacman
                                    pacman.getIm().setImage(im_couloir);
                                    
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme ||
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                // Si y'a pas de pacman
                                else
                                {
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme ||
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                
                            }
                            // Si l'image a mettre est un couloir
                            else if(image_selec == im_couloir)
                            {
                                cell.getIm().setImage(image_selec);
                            }
                            // Si l'image a mettre est un fantome
                            else if(image_selec == im_fantome1 || image_selec == im_fantome2 || image_selec == im_fantome3)
                            {
                                // Si le fantome est deja present
                                Cellule fantome = estDansGrille(image_selec);
                                if(fantome != null)
                                {
                                    // On met un couloir a la place de l'ancienne pos du fantome
                                    fantome.getIm().setImage(im_couloir);
                                    
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme || 
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                // Si y'a pas le fantome
                                else
                                {
                                    // Si l'image target est un couloir, une pac-gomme, une super pac-gomme ou un fantome
                                    if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme || 
                                       cell.getIm().getImage() == im_superpacgomme || cell.getIm().getImage() == im_fantome1 ||
                                       cell.getIm().getImage() == im_fantome2 || cell.getIm().getImage() == im_fantome3)
                                    {
                                        cell.getIm().setImage(image_selec);
                                    }
                                }
                                
                            }
                            // Si l'image a mettre est une pac-gomme ou une super-pac-gomme
                            else if(image_selec == im_pacgomme || image_selec == im_superpacgomme)
                            {
                                // Si l'image target est un couloir, une pac-gomme ou une super pac-gomme
                                if(cell.getIm().getImage() == im_couloir || cell.getIm().getImage() == im_pacgomme || 
                                   cell.getIm().getImage() == im_superpacgomme)
                                {
                                    cell.getIm().setImage(image_selec);
                                }
                            }

                            event.consume();
                        }
                    }
                });
            }
        }
    }
    
    private void init()
    {
        initImage();
        tabPanIm = new ArrayList<>();
        tabPanIm.add(new PanneauImage(im_v_mur));
        tabPanIm.add(new PanneauImage(im_v_couloir));
        tabPanIm.add(new PanneauImage(im_v_pacgomme));
        tabPanIm.add(new PanneauImage(im_v_superpacgomme));
        tabPanIm.add(new PanneauImage(im_v_pacman));
        tabPanIm.add(new PanneauImage(im_v_fantome1));
        tabPanIm.add(new PanneauImage(im_v_fantome2));
        tabPanIm.add(new PanneauImage(im_v_fantome3));
        
        tabCellule = new ArrayList<>();
        
        // Controleur tabPanIm (images du bas)
        for(int i=0; i<tabPanIm.size(); i++)
        {
            final int indice = i;
            tabPanIm.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) 
                {
                    image_selec = tabPanIm.get(indice).getIm_v().getImage();
                    tabPanIm.get(indice).setBgcolor(Color.LIGHTBLUE);
                    
                    for(int j=0; j<tabPanIm.size(); j++)
                    {
                        if(indice == j)
                            continue;
                        
                        PanneauImage panIm = tabPanIm.get(j);
                        panIm.setBgcolor(panIm.getDefault_bg_color());
                    }
                }
            });
        }
        
        grille = new GridPane();
        grille.setStyle("-fx-background-color: red;");
        grille.setStyle("-fx-fit-to-height: true;");
        grille.setStyle("-fx-fit-to-width: true;");
        
        // VBox general
        VBox vbMain = new VBox();
            // TopPane info
            paneInfo = new AnchorPane();
            // Label info
            lbInfo = new Label();
            lbInfo.setTextFill(Color.WHITESMOKE);
            lbInfo.setFont(new Font(20));
            lbInfo.setAlignment(Pos.CENTER);
            lbInfo.setTextAlignment(TextAlignment.CENTER);
            lbInfo.setStyle("-fx-font-weight: bold;");
            AnchorPane.setTopAnchor(lbInfo, 1.0);
            AnchorPane.setRightAnchor(lbInfo, 5.0);
            AnchorPane.setLeftAnchor(lbInfo, 10.0);
            AnchorPane.setBottomAnchor(lbInfo, 1.0);
            
            paneInfo.getChildren().add(lbInfo);
            paneInfo.setVisible(false);
            paneInfo.setStyle("-fx-background-color: rgb(32, 115, 188);");
        
        BorderPane border = new BorderPane();
        AnchorPane.setTopAnchor(border, 1.0);
        AnchorPane.setRightAnchor(border, 1.0);
        AnchorPane.setLeftAnchor(border, 1.0);
        AnchorPane.setBottomAnchor(border, 1.0);
        
        
        // Top Pane
        AnchorPane topPane = new AnchorPane();
            // Bouton Retour
            Button btnRetour = new Button("MENU PRINCIPAL");
            btnRetour.setFont(new Font(15));
            btnRetour.setPrefSize(150, 45);
            // Controleur bouton RETOUR
            btnRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Quitter vers menu principal");
                alert.setHeaderText("Avez-vous enregistré vos modifications ?");
                alert.setContentText("Si vous avez des modifications non enregistré, vous les perdrez.\n"
                        + "Etes-vous sûr de vouloir le faire ?");

                ButtonType buttonQuitter = new ButtonType("Quitter", ButtonBar.ButtonData.YES);
                ButtonType buttonAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonQuitter, buttonAnnuler);
                
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonQuitter)
                {
                    btnRetour.getScene().setRoot(new MenuPrincipal(jeu));
                }
                else { }
            }
        });
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
        
            Label lbTopPane = new Label("Bienvenue dans l'éditeur/créateur de niveaux !");
            lbTopPane.setFont(new Font(22));
            lbTopPane.setStyle
            (
                    "-fx-text-fill: white;"
            );
            
            HBox hbTop = new HBox(15.0, btnRetour, lbTopPane);
            hbTop.setStyle
            (
                    "-fx-background-color: black;"
            );
            
        AnchorPane.setTopAnchor(hbTop, 1.0);
        AnchorPane.setRightAnchor(hbTop, 1.0);
        AnchorPane.setLeftAnchor(hbTop, 10.0);
        AnchorPane.setBottomAnchor(hbTop, 1.0);
        
        topPane.setStyle("-fx-background-color: black;");
        topPane.getChildren().add(hbTop);
        
        // Right Pane
        AnchorPane rightPane = new AnchorPane();
            // VBox : Taille / x / y / btnGenerer
            // Label Taille
            Label lbTaille = new Label("Taille :");
            lbTaille.setFont(new Font(20));
            lbTaille.setAlignment(Pos.BASELINE_LEFT);
            lbTaille.setStyle
            (
                    "-fx-text-fill: white;"
            );
            
            // HBox taille X
            Label lbX = new Label("x");
            lbX.setFont(new Font(15));
            lbX.setTextFill(Color.WHITE);
            
            spinX = new Spinner<>(5, 20, 10);
            spinX.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
            
            HBox hbX = new HBox(5, lbX, spinX);
            
            // HBox taille Y
            Label lbY = new Label("y");
            lbY.setFont(new Font(15));
            lbY.setTextFill(Color.WHITE);
            
            spinY = new Spinner<>(5, 15, 8);
            spinY.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
            
            HBox hbY = new HBox(5, lbY, spinY);
            
            // Bouton Generer
            Button btnGenerer = new Button("Générer");
            // Controleur bouton GENERER
            btnGenerer.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event) 
                {

                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Générer une grille");
                    alert.setHeaderText("Etes-vous sûr ?");
                    alert.setContentText("La génération de la grille va effacer la grille actuelle.\n"
                            + "Etes-vous sûr de vouloir le faire ?");

                    ButtonType buttonOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                    ButtonType buttonAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonOui, buttonAnnuler);


                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonOui)
                    {
                        nbColonne = (int)spinX.getValue();
                        nbLigne = (int)spinY.getValue();
                        grille.getChildren().removeAll(grille.getChildren());
                        initGrille();
                    }
                    else { }
                }
            });
            btnGenerer.setStyle
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
            
            
            // Bouton Enregistrer
            Button btnEnregistrer = new Button("Enregistrer");
            // Controleur bouton ENREGISTRER
            btnEnregistrer.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event) 
                {
                    String nomTxt = nom + ".txt";
                    // Si y'a pas de pacman
                    if(estDansGrille(im_pacman) == null)
                    {
                        lbInfo.setText("Il manque un PacMan à votre niveau !");
                        paneInfo.setStyle("-fx-background-color: rgb(229, 36, 46);");
                        paneInfo.setVisible(true);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                paneInfo.setVisible(false);
                            }
                        }, 3000);
                        
                    }
                    else
                    {
                        // Si le nom est vide (pas encore enregistrer)
                        if("".equals(nom))
                        {
                            enregistrerSous();
                        }
                        else
                        {
                            
                            jeu.getEditeurNiveau().enregistrer(nomTxt, parseIntoStr());
                        }
                    }
                    
                }
            });
            btnEnregistrer.setStyle
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
            
            // Bouton Enregistrer sous
            Button btnEnregistrerSous = new Button("Enregistrer sous");
            // Controleur bouton ENREGISTRER SOUS
            btnEnregistrerSous.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) 
                {
                    // Si y'a pas de pacman
                    if(estDansGrille(im_pacman) == null)
                    {
                        lbInfo.setText("Il manque un PacMan à votre niveau !");
                        paneInfo.setStyle("-fx-background-color: rgb(229, 36, 46);");
                        paneInfo.setVisible(true);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                paneInfo.setVisible(false);
                            }
                        }, 3000);
                        
                    }
                    else
                    {
                        enregistrerSous();
                    }
                    
                }
            });
            btnEnregistrerSous.setStyle
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
            
            VBox vb1 = new VBox(10.0, lbTaille, hbX, hbY, btnGenerer, btnEnregistrer, btnEnregistrerSous);
            vb1.setAlignment(Pos.CENTER);
            AnchorPane.setTopAnchor(vb1, 5.0);
            AnchorPane.setRightAnchor(vb1, 5.0);
            AnchorPane.setLeftAnchor(vb1, 5.0);
            AnchorPane.setBottomAnchor(vb1, 5.0);
            vb1.setStyle("-fx-background-color: black;");
        
        rightPane.setStyle("-fx-border-color: blue;");
        rightPane.getChildren().add(vb1);
        
        // Bottom Pane
        AnchorPane bottomPane = new AnchorPane();
            // FlowPane image
            FlowPane fPaneIm = new FlowPane();
            for(int i=0; i<tabPanIm.size(); i++)
            {
                fPaneIm.getChildren().add(tabPanIm.get(i));
            }
            AnchorPane.setTopAnchor(fPaneIm, 10.0);
            AnchorPane.setRightAnchor(fPaneIm, 100.0);
            AnchorPane.setLeftAnchor(fPaneIm, 100.0);
            AnchorPane.setBottomAnchor(fPaneIm, 1.0);
            fPaneIm.setStyle("-fx-background-color: black;");
            
        bottomPane.getChildren().add(fPaneIm);
        
        
        bottomPane.setStyle("-fx-background-color: black;");
        
        border.setFocusTraversable(true);
        this.setFocusTraversable(true);
        
        // Center Pane
        AnchorPane centerPane = new AnchorPane();
        grille.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(grille, 1.0);
        AnchorPane.setRightAnchor(grille, 100.0);
        AnchorPane.setLeftAnchor(grille, 100.0);
        AnchorPane.setBottomAnchor(grille, 1.0);
        centerPane.setStyle
        (
                "-fx-background-color: gray;" +
                "-fx-border-color: blue;"
        );
        centerPane.getChildren().add(grille);
        
        
        
        border.setTop(topPane);
        border.setCenter(centerPane);
        border.setRight(rightPane);
        border.setBottom(bottomPane);
        
        
        AnchorPane.setTopAnchor(vbMain, 1.0);
        AnchorPane.setRightAnchor(vbMain, 1.0);
        AnchorPane.setLeftAnchor(vbMain, 1.0);
        AnchorPane.setBottomAnchor(vbMain, 1.0);
        vbMain.getChildren().addAll(paneInfo, border);
        
        this.setStyle("-fx-background-color: black;");
        this.getChildren().add(vbMain);
    }
    
    private void initGrille()
    {
        for(int i=0; i<nbColonne; i++)
        {
            tabCellule.add(new ArrayList<>());
            for(int j=0; j<nbLigne; j++)
            {
                tabCellule.get(i).add(new Cellule(new ImageView(im_couloir)));
                grille.add(tabCellule.get(i).get(j), i, j);
            }
        }
        grille.setGridLinesVisible(true);
    }
    
    private void enregistrerSous()
    {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enregistrer sous");
        dialog.setHeaderText("Création du fichier...");
        dialog.setContentText("Entrez un nom valide :");

        ButtonType buttonValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().setAll(buttonValider, buttonAnnuler);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            String nomTxt = result.get() + ".txt";
            // Si le nom est vide
            if("".equals(result.get()))
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Enregistrer sous");
                alert.setHeaderText("Le nom est vide !");
                alert.setContentText("Le nom ne doit pas être vide.");
            }
            // Si le nom n'est pas dispo
            else if(!jeu.getEditeurNiveau().nomEstDispo(nomTxt))
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Enregistrer sous");
                alert.setHeaderText("Le nom est déjà pris !");
                alert.setContentText("Un fichier du même nom existe déjà.");
            }
            // Si tout va bien
            else
            {
                nom = result.get();
                jeu.getEditeurNiveau().enregistrer(nomTxt, parseIntoStr());
            }
            
        }
        else {  }
    }
    
    private void initImage()
    {
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
            // Fantome 1
            im_fantome1 = new Image(new URL(getClass().getResource("/resources/img/fantome1.png").toExternalForm()).toExternalForm()); 
            // Fantome 2
            im_fantome2 = new Image(new URL(getClass().getResource("/resources/img/fantome2.png").toExternalForm()).toExternalForm());
            // Fantome 3
            im_fantome3 = new Image(new URL(getClass().getResource("/resources/img/fantome3.png").toExternalForm()).toExternalForm());
            // ImageView pacman
            im_v_pacman = new ImageView(im_pacman);
            // ImageView fantome1
            im_v_fantome1 = new ImageView(im_fantome1);
            // ImageView fantome2
            im_v_fantome2 = new ImageView(im_fantome2);
            // ImageView fantome3
            im_v_fantome3 = new ImageView(im_fantome3);
            
            im_v_mur = new ImageView(im_mur);
            im_v_couloir = new ImageView(im_couloir);
            im_v_pacgomme = new ImageView(im_pacgomme);
            im_v_superpacgomme = new ImageView(im_superpacgomme);
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(JeuVue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Cellule estDansGrille(Image im)
    {
        for(int i=0; i<tabCellule.size(); i++)
        {
            for(int j=0; j<tabCellule.get(i).size(); j++)
            {
                if(im == tabCellule.get(i).get(j).getIm().getImage())
                {
                    return tabCellule.get(i).get(j);
                }
            }
        }
        
        return null;
    }
    
    public void parseIntoCell(ArrayList<String> tabStr) 
    {
        for(int i=0; i<nbLigne; i++)
        {
            tabCellule.add(new ArrayList<>());
            String[] line = tabStr.get(i).split("-");
            for(int j=0; j<line.length; j++)
            {
                switch(line[j])
                {
                    case "M": 
                        tabCellule.get(i).add(new Cellule(new ImageView(im_mur)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "C":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_couloir)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "S":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_superpacgomme)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "G":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_pacgomme)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "P":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_pacman)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "F1":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_fantome1)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "F2":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_fantome2)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    case "F3":
                        tabCellule.get(i).add(new Cellule(new ImageView(im_fantome3)));
                        grille.add(tabCellule.get(i).get(j), j, i);
                        break;
                        
                    default: break;
                }
            }
        }
    }
    
    private ArrayList<String> parseIntoStr()
    {
        ArrayList<String> tabStr = new ArrayList<>();
        
        // On ajoute le nom d'abord
        tabStr.add("nom:" + nom);
        // On ajoute le meilleur score (à 0)
        tabStr.add("meilleurscore:0");
        // On ajoute le nombre de colonne
        tabStr.add("nbcolonne:" + nbColonne);
        // On ajoute le nombre de ligne
        tabStr.add("nbligne:" + nbLigne);
        
        // On ajoute toute la grille
        for(int i=0; i<nbLigne; i++)
        {
            String line = "";
            for(int j=0; j<nbColonne; j++)
            {
                Cellule cell = (Cellule) getNodeFromGridPane(grille, j, i);
                
                // Si c'est un mur
                if(cell.getIm().getImage() == im_mur)
                    line += "M-";
                // Si c'est une pac-gomme
                else if(cell.getIm().getImage() == im_pacgomme)
                    line += "G-";
                // Si c'est une super pac-gomme
                else if(cell.getIm().getImage() == im_superpacgomme)
                    line += "S-";
                // Si c'est un pacman
                else if(cell.getIm().getImage() == im_pacman)
                    line += "P-";
                // Si c'est un fantome rouge (1)
                else if(cell.getIm().getImage() == im_fantome1)
                    line += "F1-";
                // Si c'est un fantome orange (2)
                else if(cell.getIm().getImage() == im_fantome2)
                    line += "F2-";
                // Si c'est un fantome bleu (3)
                else if(cell.getIm().getImage() == im_fantome3)
                    line += "F3-";
                else if(cell.getIm().getImage() == im_couloir)
                    line += "C-";
                
            }
            line = line.substring(0, line.length() - 1);
            tabStr.add(line);
        }
        
        return tabStr;
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
    
    // Classe interne pour chaque cellule
    class Cellule extends AnchorPane
    {
        private ImageView im;
        
        public Cellule(ImageView im)
        {
            this.im = im;
            
            
            this.setStyle("-fx-background-color: black;");
            this.getChildren().add(im);
            
        }

        public ImageView getIm() {
            return im;
        }

        public void setIm(ImageView im) {
            this.im = im;
        }
        
        
    }
    
    // Classe interne pour les panneaux du bas
    class PanneauImage extends AnchorPane
    {
        private ImageView im_v;
        private Color default_bg_color;
        
        public PanneauImage(ImageView im_v)
        {
            this.im_v = im_v;
            default_bg_color = Color.BLACK;
            setBgcolor(default_bg_color);
            
            
            this.getChildren().add(im_v);
            AnchorPane.setTopAnchor(im_v, 20.0);
            AnchorPane.setRightAnchor(im_v, 20.0);
            AnchorPane.setLeftAnchor(im_v, 20.0);
            AnchorPane.setBottomAnchor(im_v, 20.0);
            this.setStyle("-fx-border-color: blue;");
        }
        
        public void setBgcolor(Color color)
        {
            this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        public ImageView getIm_v() {
            return im_v;
        }

        public void setIm_v(ImageView im_v) {
            this.im_v = im_v;
        }

        public Color getDefault_bg_color() {
            return default_bg_color;
        }

        public void setDefault_bg_color(Color default_bg_color) {
            this.default_bg_color = default_bg_color;
        }
    }
    
    
}
