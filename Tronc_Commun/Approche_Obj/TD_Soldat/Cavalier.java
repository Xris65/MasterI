public class Cavalier extends SoldatAbstrait {
    private static final int FORCE_CAVALIER = 2;

    public Cavalier(int vie) {
        super(vie);
    }

    public int force() {
        return FORCE_CAVALIER;
    }

    public void accept(SoldatVisitor visitor){
        visitor.visit(this);
    }
}