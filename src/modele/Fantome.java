
package modele;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Fantome extends Entite
{
    private Pacman pacman;
    private boolean vulnerable;
    private boolean mort;
    
    public Fantome(Entite pacman)
    {
        mort = false;
        this.vulnerable = false;
        this.pacman = (Pacman) pacman;
        // Génération aleatoire de la direction initiale
        this.currentDirection = randDirection();
        
        this.tempsEntreAction = 1000;
        
        
    }
      
    @Override
    protected void realiserAction() 
    {
        // Si le fantome est temporairement mort
        if(mort)
        {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // Passe le boolean mort à false
                    mort = false;
                }
            }, 4000);
            return;
        }
        
        // Si un fantome est a la meme position que pacman
        if( (currPos.getX() == pacman.getCurrPos().getX()) && (currPos.getY() == pacman.getCurrPos().getY()) )
        {
            // Si pacman est en mode super
            if(pacman.isModeSuper())
            {
                // Si le fantome est deja mort, on fait rien
                if(mort)
                {
                }
                else
                {
                    vulnerable = false;
                    mort = true;
                }
                
            }
            else
            {
                currNiveau.viePerdu();
                // Baisse la vie
                pacman.setNbVie(pacman.getNbVie() - 1);
                return;
            }
            
        }
        
        switch(currentDirection)
        {
            case "gauche":
                if(peutAvancer(currPos.getX(), currPos.getY(), "gauche"))
                    currPos.setX(currPos.getX()-1);
                else this.currentDirection = randDirection();
                break;
                
            case "droite":
                if(peutAvancer(currPos.getX(), currPos.getY(), "droite"))
                    currPos.setX(currPos.getX()+1);
                else this.currentDirection = randDirection();
                break;
                
            case "haut":
                if(peutAvancer(currPos.getX(), currPos.getY(), "haut"))
                    currPos.setY(currPos.getY()-1);
                else this.currentDirection = randDirection();
                break;
                
            case "bas":
                if(peutAvancer(currPos.getX(), currPos.getY(), "bas"))
                    currPos.setY(currPos.getY()+1);
                else this.currentDirection = randDirection();
                break;
                
            default: break;
        }
    }

    private String randDirection()
    {
        Random rand = new Random();
        int dir = rand.nextInt(4);
        return tabDirPossible[dir];
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean isMort() {
        return mort;
    }

    public void setMort(boolean mort) {
        this.vulnerable = false;
        this.mort = mort;
    }
    
}
