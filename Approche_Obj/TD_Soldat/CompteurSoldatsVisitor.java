public class CompteurSoldatsVisitor implements SoldatVisitor{
    private int nbSoldats;

    @Override
    public void visit(Fantassin fantassin) {
        this.nbSoldats++;
    }

    @Override
    public void visit(Cavalier cavalier) {
        this.nbSoldats++;
    }

    public void visit(Armee armee) {
        armee.accept(this);
        System.out.println("L'armee est composée par " + nbSoldats + " soldats.");
    }
}
