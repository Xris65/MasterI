public class SoldatProxy implements Soldat {
    private Soldat soldat;
    private int nbEpees = 0;
    private int nbBoucliers = 0;

    public SoldatProxy(String type, int vie) {
        if (type.trim().equalsIgnoreCase("cavalier")) {
            soldat = new Cavalier(vie);
        }
        else if (type.trim().equalsIgnoreCase("fantassin")) {
            soldat = new Fantassin(vie);
        }
        else {
            throw new Error("Type de soldat inconnu");
        }
    }

    public void ajouterEpee(int solidite) {
        int nbArmes = nbBoucliers + nbEpees;
        if (nbArmes < 2 && nbEpees < 1) {
            soldat = new SoldatEpee(soldat, solidite);
            nbEpees++;
        }
    }
    public void ajouterBouclier(int solidite) {
        int nbArmes = nbBoucliers + nbEpees;
        if (nbArmes < 2 && nbBoucliers <1) {
            soldat = new SoldatBouclier(soldat, solidite);
            nbBoucliers++;
        }
    }

    public int force() {
        return soldat.force();
    }

    public boolean parer(int force) {
        return soldat.parer(force);
    }

    @Override
    public void accept(SoldatVisitor visitor){
        soldat.accept(visitor);
    }

}
