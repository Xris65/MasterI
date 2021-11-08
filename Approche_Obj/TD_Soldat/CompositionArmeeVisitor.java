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
        System.out.println("L'armee est composée par " + nbFantassins + " fantassins et " + nbCavaliers + " cavaliers.");
    }
}
