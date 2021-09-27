import java.util.Scanner;

public class ShopHandler {
    public static MyShop shop = new MyShop();
    
    private void printMainMenu(){
        System.out.println("Choississez entre plusieurs options : \n");
        System.out.println("1) créer un stock, ajouter des produits dans le stock\n");
        System.out.println("2) demander les caracteristiques d'un produit a partir de son nom\n");
        System.out.println("3) ajouter/retirer une quantité d'un produit donné au stock\n");
    }
    private String handleInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choississez entre plusieurs options : \n");
        System.out.println("1) créer un stock, ajouter des produits dans le stock\n");
        System.out.println("2) demander les caracteristiques d'un produit a partir de son nom\n");
        System.out.println("3) ajouter/retirer une quantité d'un produit donné au stock\n");
        int input = scanner.nextInt();
        if(input < 0 || input < 3){
            System.out.println("Mettez un bon numero qui identifie une operation s'il vous plait\n");
            printMainMenu();
        }
        switch(input){
            case 1:
                System.out.println("Tapez 1 si vous voulez ajouter un nouveau stock ou 2 si vous voulez ajouter un produit dans un stock existant \n");
                input = scanner.nextInt();
                if(input < 0 || input >2){
                    
                }

        }
        

    }
    private void printMenus(int num){
        switch(num) {
        case 1:
            System.out.println("Tapez 1 si vous voulez ajouter un nouveau stock ou 2 si vous voulez ajouter un produit dans un stock existant \n");
        case 2:
            System.out.println("Tapez le nom du produit que vous voulez consulter\n");
        case 3:
            System.out.println("Tapez le nom \n");
    }
}
