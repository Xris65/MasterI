public class SoldatEpee extends SoldatDecorator{
    private int FORCE_EPEE = 3;
    private int RESISTANCE = 3;
    private int USURE = 6; // Si la USURE == 0 , l'arme est inutile. Il ne peut donc pas defendre, ni attaquer(donner de la force).



    SoldatEpee(Soldat soldat, int resistance) {
        super(soldat,resistance);
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

    @Override
    public void accept(SoldatVisitor visitor) {

    }


    public int resistance(){
        if (USURE > 0) {
            USURE--;
            return RESISTANCE;
        }
        return 0;
    }
}
