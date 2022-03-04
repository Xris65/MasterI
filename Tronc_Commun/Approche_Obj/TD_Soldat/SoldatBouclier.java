public class SoldatBouclier extends SoldatDecorator{
    private int RESISTANCE = 6;
    private int USURE = 6; // Si la USURE == 0, le bouclier est inutil. Il ne pourra donc plus defendre.

    SoldatBouclier(Soldat soldat, int resistance) {
        super(soldat,resistance);
    }

    public int resistance(){
        if (USURE > 0) {
            USURE--;
            return RESISTANCE;
        }
        return 0;
    }

    @Override
    public void accept(SoldatVisitor visitor) {

    }
}
