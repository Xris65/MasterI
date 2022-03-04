public abstract class SoldatDecorator extends SoldatAbstrait{
    protected Soldat soldat;

    SoldatDecorator(Soldat soldat, int resistance) {
        super(resistance);
        this.soldat = soldat;
    }

    public boolean parer(int force) {
                if (vie != 0) {
                    if (force > resistance()) {
                if (vie < resistance()) {
                    force -= vie;
                    vie = 0;
                } else {
                    vie -= resistance();
                    force  -= resistance();
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
    public abstract int resistance();
}
