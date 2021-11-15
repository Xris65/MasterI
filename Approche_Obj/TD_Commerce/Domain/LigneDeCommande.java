package Domain;
public class LigneDeCommande {
    private Reference referenceCible;
    private int quantite;
    public String id(){
        return referenceCible.getRef();
    }

    public LigneDeCommande(Reference referenceCible, int quantite) {
        this.referenceCible = referenceCible;
        this.quantite = quantite;
    }
    public int prix(){
        return quantite * referenceCible.getPrix();
    }

    public Reference getReferenceCible() {
        return referenceCible;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
