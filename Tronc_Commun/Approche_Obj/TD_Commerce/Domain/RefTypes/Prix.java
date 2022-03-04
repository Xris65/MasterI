package Domain.RefTypes;
public class Prix{
    private int prix;
    public Prix(int prix) {
        if(prix <= 0) throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        this.prix = prix;
    }
    public int getPrix(){
        return prix;
    }
}