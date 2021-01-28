
package modele;

public class Couloir extends Case 
{
    private boolean pacGomme;
    private boolean superPacGomme;

    public Couloir(boolean pacGomme, boolean superPacGomme) 
    {
        this.pacGomme = pacGomme;
        this.superPacGomme = superPacGomme;
    }
    
    public boolean isPacGomme() {
        return pacGomme;
    }

    public void setPacGomme(boolean pacGomme) {
        this.pacGomme = pacGomme;
    }

    public boolean isSuperPacGomme() {
        return superPacGomme;
    }

    public void setSuperPacGomme(boolean superPacGomme) {
        this.superPacGomme = superPacGomme;
    }
    
    
}
