public abstract class SoldatDecorator extends SoldatAbstrait{
    protected SoldatInterface soldat;

    SoldatDecorator(SoldatInterface soldat, int vie) {
        super(vie);
        this.soldat = soldat;
    }
    public boolean parer(int force) {
        if (vie != 0) {
            if (force > defense()) {
                if (vie < defense()) {
                    force -= vie;
                    vie = 0;
                } else {
                    vie -= defense();
                    force  -= defense();
                }
            } else {
                vie -= force;
                force = 0;
            }
        }
        return soldat.parer(force);
    }

    @Override
    public int force() {
        return soldat.force();
    }
    public abstract int defense();
}
