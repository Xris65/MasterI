public class Fantassin extends SoldatAbstrait {
    private static final int FORCE_FANTASSIN = 1;

    public Fantassin(int vie) {
        super(vie);
    }

    public int force() {
        return FORCE_FANTASSIN;
    }

    public void accept(SoldatVisitor visitor){
        visitor.visit(this);
    }
}