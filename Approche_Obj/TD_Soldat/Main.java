public class Main {
        public static void main(String[] args) {
// create a decorated Window with horizontal and vertical scrollbars
            Soldat fantassin = new Soldat("Fantassin",30);
            Soldat cavalier = new Soldat("Cavalier",30);
            fantassin.ajouterEpee(3);
            fantassin.ajouterBouclier(5);

            boolean vf = true;
            int ncoups = 0;

            for (; (vf = fantassin.parer(cavalier.force())) && (cavalier.parer(fantassin.force())); ncoups++)
                ;

            System.out.println("Mort du " + (vf ? "cavalier" : "fantassin")
                    + " en " + ncoups + " coups");
// print the Window’s description
            System.out.println(fantassin.force());
            System.out.println(cavalier.force());
        }
}
