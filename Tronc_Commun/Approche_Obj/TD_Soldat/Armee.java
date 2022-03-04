import java.util.ArrayList;

public class Armee implements Soldat {
    private String nom = "Armee";
    private ArrayList<SoldatProxy> soldats = new ArrayList<>();

    public ArrayList<SoldatProxy> getSoldats() {
        return soldats;
    }

    public String getNom() {
        return nom;
    }

    public Armee(ArrayList<SoldatProxy> soldats, String nom) {
        this.soldats = soldats;
        this.nom = nom;
    }

    @Override
    public int force() {
        int forceTotale = 0;
        for(Soldat soldat : soldats){
            forceTotale+= soldat.force();
        }
        return forceTotale;
    }

    public void ajouterSoldatOuArmee(SoldatProxy soldat){
        soldats.add(soldat);
    }

    public void ajouterSoldatOuArmee(Armee armee){
        soldats.add(new SoldatProxy(armee));
    }

    @Override
    public boolean parer(int force) {
        int forceIndivid = force/soldats.size();
        soldats.removeIf(soldat -> !soldat.parer(forceIndivid));
        return soldats.size() > 0;
    }

    @Override
    public void accept(SoldatVisitor visitor) {
            for(Soldat soldat : soldats){
                soldat.accept(visitor);
            }
    }

    public void ajouterEpee(int solidite){
        soldats.forEach(soldat -> soldat.ajouterEpee(solidite));
    }
    public void ajouterBouclier(int solidite){
        soldats.forEach(soldat -> soldat.ajouterBouclier(solidite));
    }

}
