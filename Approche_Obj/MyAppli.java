import java.util.ArrayList;
import java.util.Scanner;

public class MyAppli {

    public static void printMainMenu(ArrayList<Stock> stocks){

            System.out.println("Choississez un option : \n");
            System.out.println("0) Arreter le programe\n");
            System.out.println("1) Créer un stock\n");
        if(stocks.size() > 0) {
            System.out.println("2) Afficher les stocks existants\n");
            System.out.println("3) Ajouter un produit dans un stock\n");
            System.out.println("4) Demander les caracteristiques d'un produit a partir de son nom\n");
            System.out.println("5) Ajouter/retirer une quantité d'un produit donné au stock\n");
        }
    }
    public static int scanInt(Scanner scanner){
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;

    }

    public static void main(String[] args) {
        MyShop shop = new MyShop();
        Scanner scanner = new Scanner(System.in);
        int input;
        String tInput;
        Boolean quit = false,found = false;
        do {
            printMainMenu(shop.getStocks());
            try {
                input = scanInt(scanner);
                if((shop.getStocks().size() < 1 && input > 1) ||(input < 0 || input > 5))
                    System.out.println("Saisissez un option correcte s'il vous plait\n");
                    else{
                        switch (input) {
                            case 0:
                                quit = true;
                                System.out.println("Au revoir !\n");
                                break;
                            case 1:
                                System.out.println("Saisissez le nom du stock que vous voulez ajouter : \n");
                                tInput = scanner.nextLine();
                                shop.getStocks().add(new Stock(tInput));
                                System.out.println("Le stock " + tInput + " a été ajouté!\n");
                                break;
                            case 2:
                                for (Stock s : shop.getStocks()) {
                                    System.out.println(s.toString());
                                }
                                break;
                            case 3:
                                System.out.println("Saisissez le nom du stock dans lequel vous voulez ajouter un produit : \n");
                                tInput = scanner.nextLine();
                                for (Stock s : shop.getStocks()) {
                                    if (s.getName().equals(tInput)) {
                                        System.out.println("Saisissez le nom du produit que vous voulez ajouter : \n");
                                        tInput = scanner.nextLine();
                                        s.addProduct(new Product(tInput, 0));
                                        System.out.println("Fait!\n");
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Erreur dans le nom du stock, ou stock inexistant \n");
                                    found = false;
                                }
                                break;
                            case 4:
                                System.out.println("Saississez le nom du produit dont vous voulez avoir les caracteristiques:\n");
                                tInput = scanner.nextLine();
                                for (Stock s : shop.getStocks()) {
                                    for (Product p : s.getProducts()) {
                                        if (p.getName().equals(tInput)) {
                                            System.out.println(p.toString());
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                                if (!found) {
                                    System.out.println("Produit inexistant!");
                                    found = false;
                                }
                                break;
                            case 5:
                                System.out.println("Saisissez le nom du produit duquel vous voulez modifier la quantité: \n");
                                tInput = scanner.nextLine();
                                for (Stock s : shop.getStocks()) {
                                    for (Product p : s.getProducts()) {
                                        if (p.getName().equals(tInput)) {
                                            System.out.println("Saisissez la quantité que vous voulez ajouter/soustraire:\n");
                                            input = scanInt(scanner);
                                            p.modifyQuantity(input);
                                            System.out.println("Modification effectué! \n");
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                                if (!found)
                                    System.out.println("Produit non trouvé, mettez le nom d'un produit existant s'il vous plait\n");
                                break;

                        }
                    }
            }
            catch(Exception e){
                System.out.println("Erreur dans l'input, respectez les formats s'il vous plait !\n");
                scanner.nextLine();
            }

        }while(!quit);
    }

}
