package Soldats;

public class Cavalier extends SoldatDecorator {
    private static final int FORCE_CAVALIER = 2;

    public Cavalier(int vie) {
        super(this);
    }

    public int force() {
        return FORCE_CAVALIER;
    }

}