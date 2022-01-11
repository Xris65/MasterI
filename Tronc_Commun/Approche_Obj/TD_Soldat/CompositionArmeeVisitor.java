public class CompositionArmeeVisitor implements SoldatVisitor{
    private int nbFantassins = 0;
    private int nbCavaliers = 0;

    @Override
    public void visit(Fantassin fantassin) {
        this.nbFantassins++;
    }

    @Override
    public void visit(Cavalier cavalier) {
        this.nbCavaliers++;
    }

    public void visit(Armee armee) {
        for(Soldat soldat : armee.getSoldats()) {
            soldat.accept(this);
        }
        String compositionArmee = "L'armee "+ armee.getNom() + " est composée par ";
        compositionArmee += (nbCavaliers > 0 && nbFantassins > 0) ? (nbCavaliers + " Cavaliers et " + nbFantassins + " fantassins") : (nbFantassins > 0) ? (nbFantassins + " fantassins"):(nbCavaliers + " cavaliers");
        System.out.println(compositionArmee);
        reset();
    }
    public void reset(){
        nbFantassins = 0;
        nbCavaliers = 0;
    }
}
