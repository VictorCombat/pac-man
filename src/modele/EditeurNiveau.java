
package modele;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditeurNiveau 
{
    private String path;
    private File f;
    private ArrayList<String> tabNomsFichiers;
    
    public EditeurNiveau()
    {
        path = "./src/resources/niveaux/";
        f = new File(path);
    }
    
    public void enregistrer(String nom, ArrayList<String> tabStr)
    {
        Path fichier = Paths.get(path+nom);
        try {
            Files.write(fichier, tabStr, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(EditeurNiveau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean nomEstDispo(String nom)
    {
        // On recup les noms de fichiers
        tabNomsFichiers = new ArrayList<>(Arrays.asList(f.list()));
        // On teste
        for(int i=0; i<tabNomsFichiers.size(); i++)
        {
            System.out.println(i + ": " + tabNomsFichiers.get(i));
            if(nom == tabNomsFichiers.get(i))
            {
                return false;
            }
        }
        
        return true;
        
    }
    
}
