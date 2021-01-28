
package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class Jeu extends Observable
{
    private ArrayList<Niveau> niveaux;
    private Niveau currNiveau;
    private EditeurNiveau editeurNiveau;
    
    public Jeu()
    {
        editeurNiveau = new EditeurNiveau();
        chargerNiveaux();
    }

    public void chargerNiveaux()
    {
        niveaux = new ArrayList<>();
        
        //File f = new File("../src/resources/niveaux");
        File f = new File("./src/resources/niveaux");
        ArrayList<File> arrFiles = new ArrayList<>(Arrays.asList(f.listFiles()));

        for(int i=0; i<arrFiles.size(); i++)
        {
            niveaux.add(new Niveau(arrFiles.get(i).getPath()));
        }
    }
    
    public void supprimerNiveau(String path)
    {
        File f = new File(path);
        f.delete();
    }
    
    /*** GET | SET ***/

    public ArrayList<Niveau> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(ArrayList<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public Niveau getCurrNiveau() {
        return currNiveau;
    }

    public void setCurrNiveau(Niveau currNiveau) {
        this.currNiveau = currNiveau;
    }

    public EditeurNiveau getEditeurNiveau() {
        return editeurNiveau;
    }

    public void setEditeurNiveau(EditeurNiveau editeurNiveau) {
        this.editeurNiveau = editeurNiveau;
    }
    
    
}
