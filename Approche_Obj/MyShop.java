import java.util.ArrayList;
import java.util.Scanner;

public class MyShop {
    private static ArrayList<Stock> stocks = new ArrayList<Stock>();
    public static void printMainMenu(){
        System.out.println("Choississez entre plusieurs options : \n");
        System.out.println("1) créer un stock, ajouter des produits dans le stock\n");
        System.out.println("2) demander les caracteristiques d'un produit a partir de son nom\n");
        System.out.println("3) ajouter/retirer une quantité d'un produit donné au stock\n");
        System.out.println("4) arreter le programe\n");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;
        String tInput;
        Boolean quit = false;
        do {
            printMainMenu();
            try{
            input = scanner.nextInt();
            switch(input){
                case 1:
                    System.out.println("Tapez 1 si vous voulez ajouter un nouveau stock ou 2 si vous voulez ajouter un produit dans un stock existant \n");
                    input = scanner.nextInt();
                    if(input == 1){
                        System.out.println("Tapez le nom du stock que vous voulez ajouter : \n");
                        tInput = scanner.nextLine();
                        stocks.add(new Stock(tInput));
                    }
                    else if(input == 2) {
                        System.out.println("Tapez le nom du stock dans lequel vous voulez ajouter un produit : \n");
                        tInput = scanner.nextLine();
                        for(Stock s : stocks) {
                            if(s.getName().equals(tInput)) {
                                System.out.println("Tapez le nom du produit que vous voulez ajouter : \n");
                                tInput = scanner.nextLine();
                                s.addProduct(new Product(tInput,0));
                                System.out.println("Fait!\n");
                            }
                        }
                    }
                    else{
                        System.out.println("Le numero tapé ne correspond pas a une option valable\n");
                    }
                case 2:

            }
            }
            catch(Exception E){
                System.out.println("Mettez un bon numero qui identifie une operation s'il vous plait\n");
            }

        }while(input < 1 || input > 4);
        switch(input){
            case 1:
                System.out.println("Tapez 1 si vous voulez ajouter un nouveau stock ou 2 si vous voulez ajouter un produit dans un stock existant \n");
                do{
                    input = scanner.nextInt();
                }

        }
    }
}
