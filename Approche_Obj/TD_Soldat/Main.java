import java.util.ArrayList;
import java.util.Random;

public class Main {
        public static void main(String[] args) {
            /*Soldat fantassin = new Soldat("Fantassin",30);
            Soldat cavalier = new Soldat("Cavalier",30);
            fantassin.ajouterEpee(3);
            fantassin.ajouterBouclier(5);

            boolean fantassinVivant = true;
            int ncoups = 0;

            for (; (fantassinVivant = fantassin.parer(cavalier.force())) && (cavalier.parer(fantassin.force())); ncoups++)
                ;

            System.out.println("Mort du " + (fantassinVivant ? "cavalier" : "fantassin")
                    + " en " + ncoups + " coups")
            System.out.println(fantassin.force());
            System.out.println(cavalier.force());
            */
            ArrayList<Soldat> groupe1 = new ArrayList<Soldat>();
            ArrayList<Soldat> groupe2 = new ArrayList<Soldat>();
            for(int i = 0 ; i < 50 ;i++ ){
                groupe1.add(new SoldatProxy("Fantassin",new Random().nextInt(16,24)));
                groupe2.add(new SoldatProxy("Cavalier",new Random().nextInt(8,12)));

            }

            Armee armeeFantassins = new Armee(groupe1);
            Armee armeeCavaliers = new Armee(groupe2);

            armeeFantassins.ajouterSoldat(armeeCavaliers);
            //armeeFantassins.ajouterEpee(5);
            CompositionArmeeVisitor visitor = new CompositionArmeeVisitor();
            visitor.visit(armeeFantassins);
            boolean armee1V = true;
            int ncoups = 1;
            boolean armeeFVivante = true;
            boolean armeeCVivante = true;

            for (; (armeeFVivante && armeeCVivante); ncoups++) {
                armeeFVivante = armeeFantassins.parer(armeeCavaliers.force());
                System.out.println("Les fantassins parent une attaque de " + armeeCavaliers.force());
                armeeCVivante = armeeCavaliers.parer(armeeFantassins.force());
                System.out.println("Les cavaliers parent une attaque de " + armeeFantassins.force());
            }
            System.out.println("Defaite de l'armee des " + (armeeFVivante ? "cavaliers " : "fantassins ")
                    + "en " + ncoups + " coups");



        }
}
