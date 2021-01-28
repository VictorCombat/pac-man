
package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Niveau 
{
    private String path;
    private String nom;
    private int nbPacGoms;
    private int meilleurScore;
    private int nbColonne;
    private int nbLigne;
    private Case[][] plateau;
    private Entite[] tabEntite;
    private ArrayList<String> tabStrPlateau;
    private ArrayList<Thread> tabThreadEntite;
    
    public Niveau(String path)
    {
        this.path = path;
        this.nbPacGoms = 0;
        tabEntite = new Entite[4];
        tabEntite[0] = new Pacman();
        for(int i=1; i<4; i++)
        {
            tabEntite[i] = new Fantome(tabEntite[0]);
        }
        tabThreadEntite = new ArrayList<>();
        lireFichier();
        initPlateau();
        
    }
    
    private void lireFichier()
    {
        tabStrPlateau = new ArrayList<>();
        Path fichier = Paths.get(path);
        List<String> tabStr = null;
        try {
            tabStr = Files.readAllLines(fichier, Charset.forName("UTF-8"));
            nom = tabStr.get(0).split(":")[1];
            meilleurScore = Integer.valueOf(tabStr.get(1).split(":")[1]);
            nbColonne = Integer.valueOf(tabStr.get(2).split(":")[1]);
            nbLigne = Integer.valueOf(tabStr.get(3).split(":")[1]);
            
            for(int i=4; i<tabStr.size(); i++)
            {
                tabStrPlateau.add(tabStr.get(i));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Niveau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initPlateau()
    {
        plateau = new Case[nbColonne][nbLigne];
        for(int i=0; i<tabStrPlateau.size(); i++)
        {
            String[] tabLine = tabStrPlateau.get(i).split("-");
            for(int j=0; j<tabLine.length; j++)
            {
                switch(tabLine[j])
                {
                    case "C":
                        plateau[j][i] = new Couloir(false, false);
                        break;
                        
                    case "M":
                        plateau[j][i] = new Mur();
                        break;
                        
                    case "P":
                        plateau[j][i] = new Couloir(false, false);
                        tabEntite[0].setCurrPos(new Position(j, i));
                        tabEntite[0].setStartPos(new Position(j, i));
                        break;
                        
                    case "G": 
                        plateau[j][i] = new Couloir(true, false);
                        this.nbPacGoms++;
                        break;
                        
                    case "S":
                        plateau[j][i] = new Couloir(false, true);
                        break;
                        
                    case "F1":
                        plateau[j][i] = new Couloir(false, false);
                        tabEntite[1].setCurrPos(new Position(j, i));
                        tabEntite[1].setStartPos(new Position(j, i));
                        break;
                        
                    case "F2":
                        plateau[j][i] = new Couloir(false, false);
                        tabEntite[2].setCurrPos(new Position(j, i));
                        tabEntite[2].setStartPos(new Position(j, i));
                        break;
                        
                    case "F3":
                        plateau[j][i] = new Couloir(false, false);
                        tabEntite[3].setCurrPos(new Position(j, i));
                        tabEntite[3].setStartPos(new Position(j, i));
                        break;
                        
                    default: break;
                }
            }
        }
        // Envoi de la reference du plateau dans toutes les entites
        for(int i=0; i<tabEntite.length; i++)
        {
            tabEntite[i].setPlateau(plateau);
            tabEntite[i].setCurrNiveau(this);
        }
        
        // Envoi ref fantome -> pacman
        Pacman pacman = (Pacman) tabEntite[0];
        Entite[] tabFantome = { tabEntite[1], tabEntite[2], tabEntite[3] };
        pacman.setTabFantome(tabFantome);
    }
    
    public void lancerPartie()
    {
        
        // Met toutes les entites a ACTIF
        for(int i=0; i<tabEntite.length; i++)
        {
            tabEntite[i].setActif(true);
        }
        // Lance les threads et les ajoute au tableau
        for(int i=0; i<tabEntite.length; i++)
        {
            tabThreadEntite.add(new Thread(tabEntite[i]));
            tabThreadEntite.get(i).start();
            
        }
    }
    
    public void reveilleThread()
    {
        // REVEILLE LES THREADS
        for(int i = 0; i < tabEntite.length; i++)
        {
            tabEntite[i].resume();
        }
    }
    
    public void viePerdu()
    {
        for(int i=0; i<tabEntite.length; i++)
        {
            tabEntite[i].setPerduVie(true);
            
            if(tabEntite[i] instanceof Fantome)
            {
                // Reset vulnerable et mort
                Fantome fantome = (Fantome) tabEntite[i];
                fantome.setMort(false);
                fantome.setVulnerable(false);
            }
        }
    }
    
    public void enregistrerScore()
    {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.set(1, "meilleurscore:" + meilleurScore);
            Files.write(Paths.get(path), lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(Niveau.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void finPartie()
    {
        // Si le score est sup au meilleur score, on l'enregistre
        Pacman pacman = (Pacman) tabEntite[0];
        if(pacman.getScore() > meilleurScore)
        {
            meilleurScore = pacman.getScore();
            enregistrerScore();
        }
        
        // MET A FALSE ACTIF
        for(int i=0; i<tabEntite.length; i++)
        {
            tabEntite[i].setActif(false);
        }
        
        // JOIN
        for(int i=0; i<tabThreadEntite.size(); i++)
        {
            try {
                tabThreadEntite.get(i).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Niveau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    /*** GET & SET ***/
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public void setMeilleurScore(int meilleurScore) {
        this.meilleurScore = meilleurScore;
    }
    
    public int getNbPacGoms(){
       return nbPacGoms; 
    }

    public void setNbPacGoms(int nbPacGoms) {
        this.nbPacGoms = nbPacGoms;
    }
    
    public Case[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getNbColonne() {
        return nbColonne;
    }

    public void setNbColonne(int nbColonne) {
        this.nbColonne = nbColonne;
    }

    public int getNbLigne() {
        return nbLigne;
    }

    public void setNbLigne(int nbLigne) {
        this.nbLigne = nbLigne;
    }

    public Entite[] getTabEntite() {
        return tabEntite;
    }

    public void setTabEntite(Entite[] tabEntite) {
        this.tabEntite = tabEntite;
    }

    public ArrayList<String> getTabStrPlateau() {
        return tabStrPlateau;
    }

    public void setTabStrPlateau(ArrayList<String> tabStrPlateau) {
        this.tabStrPlateau = tabStrPlateau;
    }

    public ArrayList<Thread> getTabThreadEntite() {
        return tabThreadEntite;
    }

    public void setTabThreadEntite(ArrayList<Thread> tabThreadEntite) {
        this.tabThreadEntite = tabThreadEntite;
    }

    
}
