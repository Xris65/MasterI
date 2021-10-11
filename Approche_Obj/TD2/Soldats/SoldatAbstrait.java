package Soldats;

abstract class SoldatAbstrait implements Soldat {
    protected int vie = 100;

    public SoldatAbstrait(int vie) {
        this.vie = vie;
    }


    public int vie(){
        return vie;
    }
    public boolean parer(int force) {
        vie = (vie > force) ? vie - force : 0;
        return vie > 0;
    }
}