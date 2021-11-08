import java.util.ArrayList;

public class Armee implements Soldat {

    private ArrayList<Soldat> soldats = new ArrayList<Soldat>();

    public ArrayList<Soldat> getSoldats() {
        return soldats;
    }

    public Armee(ArrayList<Soldat> soldats) {
        this.soldats = soldats;
    }

    @Override
    public int force() {
        int forceTotale = 0;
        for(Soldat soldat : soldats){
            forceTotale+= soldat.force();
        }
        return forceTotale;
    }

    public void ajouterSoldat(Soldat soldat){
        soldats.add(soldat);
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
        soldats.forEach(soldat -> ((SoldatProxy)soldat).ajouterEpee(solidite));
    }
    public void ajouterBouclier(int solidite){
        soldats.forEach(soldat -> ((SoldatProxy)soldat).ajouterBouclier(solidite));
    }

}
