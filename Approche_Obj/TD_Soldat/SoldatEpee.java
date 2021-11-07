public class SoldatEpee extends SoldatDecorator{
    private int FORCE_EPEE = 3;
    private int DEFENSE = 3;
    private int USURE = 6; // Si la USURE == 0 , l'arme est inutile. Il ne peut donc pas defendre, ni attaquer(donner de la force).


    SoldatEpee(SoldatInterface soldat, int solidite) {
        super(soldat, solidite);
    }
    SoldatEpee(SoldatInterface soldat) { // Constructeur avec solidite de base
        super(soldat, 6);
    }

    @Override
    public int force() {
        int forceTotale = super.force();
        if (USURE > 0) {
            USURE--;
            forceTotale+=FORCE_EPEE;
        }
        return forceTotale;
    }

    public int defense(){
        if (USURE > 0) {
            USURE--;
            return DEFENSE;
        }
        return 0;
    }
}
