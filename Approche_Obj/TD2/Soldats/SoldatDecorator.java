package Soldats;

public class SoldatDecorator extends SoldatAbstrait{
    public Soldat soldat;
    @Override
    public int force() {
        return soldat.force();
    }

    public SoldatDecorator(Soldat soldat) {
        super(soldat.vie());
        this.soldat = soldat;
    }

    @Override
    public boolean parer(int force) {
        soldat.vie() -= force;
        return (soldat.vie() > 0) ;
    }

    @Override
    public int vie() {
        return soldat.vie();
    }

}
