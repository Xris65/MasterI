import java.util.ArrayList;
import java.util.Random;

public class Main {
        public static void main(String[] args) {
        /*
            //Tests pour les soldats individuels.

            SoldatProxy fantassin = new SoldatProxy("Fantassin", 30);
            SoldatProxy cavalier = new SoldatProxy("Cavalier", 30);
            fantassin.ajouterEpee(10);
            fantassin.ajouterBouclier(5);

            boolean fantassinVivant = true;
            int ncoups = 0;
            int forceCavalier;
            int forceFantassin;

            for (;(fantassinVivant = fantassin.parer(forceCavalier = cavalier.force())) && (cavalier.parer(forceFantassin = fantassin.force())); ncoups++){
                System.out.println("Le fantassin pare un coup de " + forceCavalier + " et le cavalier pare un coup de " + forceFantassin);

            };

            System.out.println("Mort du " + (fantassinVivant ? "cavalier" : "fantassin")
                    + " en " + ncoups + " coups");
            */

            //Tests pour les armees de soldats
            ArrayList<SoldatProxy> groupe1 = new ArrayList<>();
            ArrayList<SoldatProxy> groupe2 = new ArrayList<>();
            ArrayList<SoldatProxy> renfort = new ArrayList<>();
            for(int i = 0 ; i < 50 ; i++ ){
                groupe1.add(new SoldatProxy("Fantassin",new Random().nextInt(4) + 3));
                groupe2.add(new SoldatProxy("Cavalier",new Random().nextInt(10) + 6));
                renfort.add(new SoldatProxy(i%2==1 ? "Cavalier" : "Fantassin",new Random().nextInt(4) + 6));

            }

            Armee armeeFantassins = new Armee(groupe1,"Fantassins");
            Armee armeeCavaliers = new Armee(groupe2,"Cavaliers");

            Armee renfortFantassins = new Armee(renfort,"RenfortFantassins");
            armeeFantassins.ajouterSoldatOuArmee(renfortFantassins);
            //armeeFantassins.ajouterEpee(5);
            //armeeCavaliers.ajouterEpee(5);
            CompositionArmeeVisitor visitorComposition = new CompositionArmeeVisitor();
            visitorComposition.visit(armeeFantassins);
            CompteurSoldatsVisitor visitorStats = new CompteurSoldatsVisitor();
            visitorStats.visit(armeeFantassins);
            boolean armee1V = true;
            int ncoups = 0;
            boolean armeeFVivante = true;
            boolean armeeCVivante = true;
            int forceCavalier;
            int forceFantassin;
            for (; (armeeFVivante && armeeCVivante); ncoups++) {
                armeeFVivante = armeeFantassins.parer(forceCavalier = armeeCavaliers.force());
                System.out.println("Les fantassins parent une attaque de " + forceCavalier);
                visitorComposition.visit(armeeFantassins);
                armeeCVivante = armeeCavaliers.parer(forceFantassin = armeeFantassins.force());
                System.out.println("Les cavaliers parent une attaque de " + forceFantassin);
                visitorComposition.visit(armeeCavaliers);

            }
            System.out.println("Defaite de l'armee des " + (armeeFVivante ? "cavaliers " : "fantassins ")
                    + "en " + ncoups + " coups");

        }


}
