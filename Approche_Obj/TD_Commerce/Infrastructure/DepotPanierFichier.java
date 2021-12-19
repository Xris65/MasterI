package Infrastructure;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import Domain.DepotPanier;
import Domain.Panier;

public class DepotPanierFichier implements DepotPanier {
    public void charger(String filename) {
        final File fichier = new File(filename + ".txt");
        try {
            FileReader reader = new FileReader(fichier);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        
    }

    public void sauvegarder(Panier panier, String filename) {
        // sauvegarder le panier dans un fichier
        try {
            final File fichier = new File(filename + ".txt");
            FileWriter writer = new FileWriter(fichier);
            writer.write(panier.getId());
            writer.write(panier.getId());
            writer.write(panier.getId());
            writer.close();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        }
    }


}
