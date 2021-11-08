public class SoldatBouclier extends SoldatDecorator{
    private int DEFENSE = 6;
    private int USURE = 6; // Si la USURE == 0, le bouclier est inutil. Il ne pourra donc plus defendre.

    SoldatBouclier(Soldat soldat, int solidite) {
        super(soldat, solidite);
    }


    public int defense(){
        if (USURE > 0) {
            USURE--;
            return DEFENSE;
        }
        return 0;
    }

    @Override
    public void accept(SoldatVisitor visitor) {

    }
}
