
package modele;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Entite extends Observable implements Runnable
{
    protected String currentDirection;
    protected String[] tabDirPossible;
    protected boolean actif;
    protected int tempsEntreAction;
    protected Position currPos;
    protected Position startPos;
    protected Case[][] plateau;
    protected Niveau currNiveau;
    protected boolean perduVie;
    protected int indice;
    protected Object pauseLock = new Object();
    protected boolean paused = false;
    
    public Entite()
    {
        actif = true;
        tabDirPossible = new String[4];
        tabDirPossible[0] = "haut";
        tabDirPossible[1] = "gauche";
        tabDirPossible[2] = "droite";
        tabDirPossible[3] = "bas";
    }
    
    @Override
    public void run() 
    {
        indice = 0;
        perduVie = false;
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Entite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while(actif)
        {
            // Si c'est en pause
            synchronized(pauseLock)
            {
                if(paused)
                {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Entite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            // Si on a perdu une vie
            if(perduVie)
            {   
                        
                // On retourne a la position initiale
                resetPos();
                
                // Notification vue
                setChanged();
                notifyObservers();
                
                // On s'endort pendant 2s
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Entite.class.getName()).log(Level.SEVERE, null, ex);
                }
                perduVie = false;
            }
            
            realiserAction();
            
            // Notifie la vue
            setChanged();
            notifyObservers();
            try {
                Thread.interrupted();
                Thread.sleep(tempsEntreAction);
            } catch (InterruptedException ex) 
            {
                actif = false;
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(Entite.class.getName()).log(Level.SEVERE, null, ex1);
                }
                System.out.println(Thread.currentThread().getName() + " interrupted");
                //Logger.getLogger(Entite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void pause()
    {
        paused = true;
    }
    
    public void resume()
    {
        synchronized(pauseLock)
        {
            paused = false;
            pauseLock.notifyAll();
        }
    }
    
    private void resetPos()
    {
        currPos.setX(startPos.getX());
        currPos.setY(startPos.getY());
    }
    
    protected abstract void realiserAction();

    public boolean estLibre(int x, int y)
    {
        if(x >= plateau.length || x < 0 || y >= plateau[x].length || y < 0)
        {
            return false;
        }
        else if(plateau[x][y] instanceof Mur)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean peutAvancer(int x, int y, String sens)
    {
        switch(sens)
        {
            case "gauche":
                if(estLibre(x-1, y)) 
                    return true;
                else
                    return false;
                
            case "droite":
                if(estLibre(x+1, y))
                    return true;
                else
                    return false;
                
            case "haut":
                if(estLibre(x, y-1))
                    return true;
                else
                    return false;
            
            case "bas":
                if(estLibre(x, y+1))
                    return true;
                else
                    return false;
                
            default:
                System.err.println("ERR: peutAvancer");
                return false;
                
        }
    }
    
    public String getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public int getTempsEntreAction() {
        return tempsEntreAction;
    }

    public void setTempsEntreAction(int tempsEntreAction) {
        this.tempsEntreAction = tempsEntreAction;
    }

    public Case[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(Case[][] plateau) {
        this.plateau = plateau;
    }

    public Position getCurrPos() {
        return currPos;
    }

    public void setCurrPos(Position currPos) {
        this.currPos = currPos;
    }

    public Position getStartPos() {
        return startPos;
    }

    public void setStartPos(Position startPos) {
        this.startPos = startPos;
    }
    
    public Niveau getCurrNiveau() {
        return currNiveau;
    }

    public void setCurrNiveau(Niveau currNiveau) {
        this.currNiveau = currNiveau;
    }

    public boolean isPerduVie() {
        return perduVie;
    }

    public void setPerduVie(boolean perduVie) {
        this.perduVie = perduVie;
    }
    
    
}
