public abstract class SoldatDecorator implements Soldat{
    protected Soldat soldat;

    SoldatDecorator(Soldat soldat) {
        this.soldat = soldat;
    }
    public boolean parer(int force) {
        return soldat.parer(force);
    }

    @Override
    public int force() {
        return soldat.force();
    }
    public abstract int defense();
}
