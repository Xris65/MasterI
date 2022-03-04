package Domain;
import Domain.RefTypes.Description;
import Domain.RefTypes.Prix;
import Domain.RefTypes.Text;

public class Reference {
    private final Text ref;
    private final Text nom;
    private final Description description;
    private final Prix prix;

    public Reference(String ref, String nom, String description, int prix) {
        this.ref = new Text(ref,20);
        this.nom = new Text(nom,20);
        this.description = new Description(description);
        this.prix = new Prix(prix);
    }


    public String getRef() {
        return ref.getText();
    }

    public String getNom() {
        return nom.getText();
    }

    public String getDescription() {
        return description.getDescription();
    }

    public int getPrix() {
        return prix.getPrix();
    }

    @Override
    public boolean equals(Object obj) {
        Reference reference =(Reference) obj;
        return (hashCode() == obj.hashCode()) &&(reference.getRef().equals(getRef()) && (reference.getNom().equals(getNom())) && (reference.getDescription().equals(getDescription())) && (reference.getPrix() == getPrix()));
    }


}
