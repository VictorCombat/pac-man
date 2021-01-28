
package VueControleur;

import modele.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Vue extends Application
{
    private Jeu jeu;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        // Initialise modele (Jeu)
        jeu = new Jeu();
        
        MenuPrincipal menuPrincipal = new MenuPrincipal(jeu);
        scene = new Scene(menuPrincipal);
        
        
        primaryStage.setTitle("PacMan");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
       
        
    }

    @Override
    public void stop() throws Exception 
    {
        // Stop tous les threads de tous les niveaux s'il y'en a
        for(int i=0; i<jeu.getNiveaux().size(); i++)
        {
            jeu.getNiveaux().get(i).finPartie();
        }
        super.stop();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
