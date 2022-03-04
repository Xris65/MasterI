package Domain;
import java.util.ArrayList;

import Domain.RefTypes.Text;

public class Panier{

    private ArrayList<LigneDeCommande> lignesDeCommande = new ArrayList<LigneDeCommande>();
    private Text id;
    private boolean estModifiable = true;

    public Panier(String id) {
        this.id = new Text(id, 20);
    }

    public String getId(){
        return this.id.getText();
    }

    public int montantTotal(){
        int montant = 0;
        for(LigneDeCommande ligne : lignesDeCommande){
            montant += ligne.getQuantite() * ligne.getReferenceCible().getPrix();
        }
        return montant;
    }

    public void ajouterLigneDeCommande(Reference ref, int quantite){
        if(!estModifiable) throw new RuntimeException("Le panier est  déjà validé");
        LigneDeCommande ligneDeCommande = new LigneDeCommande(ref, quantite);
        for (LigneDeCommande ligne : lignesDeCommande) {
            if (ligne.getReferenceCible().equals(ligneDeCommande.getReferenceCible())) {
                ligne.setQuantite(ligne.getQuantite() + ligneDeCommande.getQuantite());
                return;
            }
        }
        lignesDeCommande.add(ligneDeCommande);
    }

    public void supprimerLigneDeCommande(Reference ref, int quantite){
        if(!estModifiable) throw new RuntimeException("Le panier est  déjà validé");
        for (LigneDeCommande ligne : lignesDeCommande) {
            if (ligne.getReferenceCible().equals(ref)) {
                ligne.setQuantite(ligne.getQuantite() - quantite);
                if (ligne.getQuantite() == 0) {
                    lignesDeCommande.remove(ligne);
                }
                return;
            }
        }
    }

    public void afficherPanier(){
        for (LigneDeCommande ligne : lignesDeCommande) {
            System.out.println(ligne.getReferenceCible().getNom() + " : " + ligne.getQuantite());
        }
    }

    public void validerPanier(){
        estModifiable = false;
    }
}
