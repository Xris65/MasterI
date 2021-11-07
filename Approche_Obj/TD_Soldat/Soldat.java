public class Soldat implements SoldatInterface {
    private SoldatInterface soldat;
    private int nbArmes = 0;

    public Soldat(String type,int vie) {
        if (type.equals("Cavalier")) {
            soldat = new Cavalier(vie);
        }
        else if (type.equals("Fantassin")) {
            soldat = new Fantassin(vie);
        }
        else {
            throw new Error("Type de soldat inconnu");
        }
    }

    public void ajouterEpee(int solidite) {
        System.out.println("nb armes : " + nbArmes);
        if (nbArmes < 2) {
            soldat = new SoldatEpee(soldat, solidite);
            nbArmes++;
        }
    }
    public void ajouterBouclier(int solidite) {
        System.out.println("nb armes : " + nbArmes);
        if (nbArmes < 2) {
            soldat = new SoldatBouclier(soldat, solidite);
            nbArmes++;
        }
    }

    public int force() {
        return soldat.force();
    }

    public boolean parer(int force) {
        return soldat.parer(force);
    }

}
