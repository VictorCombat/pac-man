
package modele;

import java.util.Timer;
import java.util.TimerTask;

public class Pacman extends Entite
{
    private int nbVie;
    private int score;
    private int nbGoms;
    private Entite[] tabFantome;
    private boolean modeSuper;
    private int pacmanNbGoms = 0;
    
    public Pacman()
    {
        modeSuper = false;
        this.score = 0;
        this.nbVie = 3;
        this.currentDirection = "droite";
        this.tempsEntreAction = 300;
    }
    
    @Override
    protected void realiserAction()
    {
        
        // Collision
        for(int i=0; i<tabFantome.length; i++)
        {
            Fantome fantome = (Fantome) tabFantome[i];
            if((currPos.getX() == fantome.getCurrPos().getX()) && 
                    (currPos.getY() == fantome.getCurrPos().getY()))
            {
                // Si on est en mode super
                if(modeSuper)
                {
                    // Si le fantome est deja mort, on fait rien
                    if(fantome.isMort())
                    {
                    }
                    else
                    {
                        ajoutScore(200);
                        fantome.setMort(true);
                    }
                    
                }
                // Si le fantome est mort, on fait rien
                else if(fantome.isMort())
                {
                }
                else
                {
                    nbVie--;
                    currNiveau.viePerdu();
                    return;
                }
                
            }
        }
        
        
        switch(currentDirection)
        {
            case "gauche":
                if(peutAvancer(currPos.getX(), currPos.getY(), "gauche"))
                    currPos.setX(currPos.getX()-1);
                break;
                
            case "droite":
                if(peutAvancer(currPos.getX(), currPos.getY(), "droite"))
                    currPos.setX(currPos.getX()+1);
                break;
                
            case "haut":
                if(peutAvancer(currPos.getX(), currPos.getY(), "haut"))
                    currPos.setY(currPos.getY()-1);
                break;
                
            case "bas":
                if(peutAvancer(currPos.getX(), currPos.getY(), "bas"))
                    currPos.setY(currPos.getY()+1);
                break;
                
            default: break;
        }
        
        if(plateau[currPos.getX()][currPos.getY()] instanceof Couloir)
        {
            Couloir couloir = (Couloir) plateau[currPos.getX()][currPos.getY()];
            if(couloir.isPacGomme())
            {
                // Enleve la pac-gomme
                couloir.setPacGomme(false);
                
                // Augmente le score
                ajoutScore(10);
                this.nbGoms++;
            }
            else if(couloir.isSuperPacGomme())
            {
                // Passe en mode Super
                modeSuper = true;
                // Reduit le tempsEntreAction
                tempsEntreAction -= 100;
                // Enleve la superPacGomme
                couloir.setSuperPacGomme(false);
                // Met les fantomes vulnerable
                fantomeVulnerable(true);
                
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Sort du mode Super
                        modeSuper = false;
                        // Remet le temps entre Action
                        tempsEntreAction += 100;
                        // Remet la superPacGomme
                        couloir.setSuperPacGomme(true);
                        // Remet les fantomes non vulnerable
                        fantomeVulnerable(false);
                    }
                }, 5000);
            }
            
        }
    }

    private void fantomeVulnerable(boolean vulnerable)
    {
        for(int i=0; i<tabFantome.length; i++)
        {
            Fantome fantome = (Fantome) tabFantome[i];
            // Si le fantome est deja mort, on passe pas en vulnerable
            if(fantome.isMort())
            {
                // On fait rien
            }
            else
            {
                fantome.setVulnerable(vulnerable);
            }
            
        }
    }
    
    private void ajoutScore(int montant)
    {
        this.score += montant;
    }
    
    public int getScore() {
        return score;
    }

    public int getGomms() {
        return nbGoms;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public int getNbVie() {
        return nbVie;
    }

    public void setNbVie(int nbVie) {
        this.nbVie = nbVie;
    }

    public Entite[] getTabFantome() {
        return tabFantome;
    }

    public void setTabFantome(Entite[] tabFantome) {
        this.tabFantome = tabFantome;
    }

    public boolean isModeSuper() {
        return modeSuper;
    }

    public void setModeSuper(boolean modeSuper) {
        this.modeSuper = modeSuper;
    }

    
    
}
