import Domain.Panier;
import Infrastructure.DepotPanierFichier;

public class Main {
    
    public static void main(String[] args) {
        // Create a new instance of the class Main
        Panier panier = new Panier("32");
        DepotPanierFichier depotPanier = new DepotPanierFichier();
        depotPanier.sauvegarder(panier, "panier");
    }
}
