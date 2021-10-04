import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

public class MyAppli {
    private static final Object lock = new Object();


    public static void printMainMenu(ArrayList<Stock> stocks) {

        System.out.println("Choississez un option : \n");
        System.out.println("0) Arreter le programe\n");
        System.out.println("1) Créer un stock\n");
        System.out.println("2) Charger un stock\n");
        if (stocks.size() > 0) {
            System.out.println("3) Afficher les stocks existants\n");
            System.out.println("4) Ajouter un produit dans un stock\n");
            System.out.println("5) Demander les caracteristiques d'un produit a partir de son nom\n");
            System.out.println("6) Ajouter/retirer une quantité d'un produit donné au stock\n");
            System.out.println("7) Vendre un produit\n");
            System.out.println("8) Sauvegarder un stock actuel dans un fichier\n");
        }
    }

    public static int scanInt(Scanner scanner) {
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public static Boolean checkIfStockExists(ArrayList<Stock> stocks, String name) {
        if (stocks.size() == 0)
            return false;
        for (Stock stock : stocks) {
            if (stock.getName().equals(name))
                return true;
        }
        return false;
    }

    public static void loadConfig(String filename, ArrayList<Stock> stocks) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            scanner.useDelimiter("\\{");
            Stock stock = null;
            int id, quantity;
            String name, date, productInfo = null;
            if (scanner.hasNext()) {
                String stockName = scanner.next();
                if (!checkIfStockExists(stocks, stockName)) {
                    stock = new Stock(stockName);
                    stocks.add(stock);
                } else {
                    System.out.println("Le stock existe deja!\n");
                    return;
                }

            }
            scanner.useDelimiter("\\[");
            while (scanner.hasNext()) {
                scanner.next();
                scanner.useDelimiter("]");
                if (scanner.hasNext())
                    productInfo = scanner.next().split("\\[")[1];
                if (productInfo != null) {
                    String[] productInfos = productInfo.split(",");
                    name = productInfos[0];
                    quantity = Integer.parseInt(productInfos[1]);
                    if (productInfos.length == 3) {
                        date = productInfos[2];
                        assert stock != null;
                        stock.addFood(name, quantity, date);
                    } else {
                        assert stock != null;
                        stock.addSanitary(name, quantity);
                    }
                }
                productInfo = null;
                scanner.useDelimiter("\\[");
            }
            System.out.println(stock);

        } catch (IOException e) {
            System.out.println("Fichier non existant\n");
        }
    }


    public static void main(String[] args) {
        MyShop shop = new MyShop();
        Scanner scanner = new Scanner(System.in);
        int input;
        String textInput;
        boolean quit = false, found = false;
        do {
            printMainMenu(shop.getStocks());
            try {
                input = scanInt(scanner);
                switch (input) {
                    case 0:
                        quit = true;
                        System.out.println("Au revoir !\n");
                        break;
                    case 1:
                        System.out.println("Saisissez le nom du stock que vous voulez ajouter : \n");
                        textInput = scanner.nextLine();
                        if (!checkIfStockExists(shop.getStocks(), textInput)) {
                            shop.getStocks().add(new Stock(textInput));
                            System.out.println("Le stock " + textInput + " a été ajouté!\n");
                        } else
                            System.out.println("Le stock " + textInput + " existe deja !\n");
                        break;
                    case 2:
                        System.out.println("Saisissez le nom du fichier contenant le stock: \n");
                        textInput = scanner.nextLine();
                        loadConfig(textInput, shop.getStocks());
                        break;
                    case 3:
                        for (Stock stock : shop.getStocks()) {
                            System.out.println(stock.toString());
                        }
                        break;
                    case 4:
                        System.out.println("Saisissez le nom du stock dans lequel vous voulez ajouter un produit : \n");
                        textInput = scanner.nextLine();
                        for (Stock stock : shop.getStocks()) {
                            if (stock.getName().equals(textInput)) {
                                found = true;
                                System.out.println("Saississez 1 si votre produit est sanitaire ou 2 s'il est alimentaire\n");
                                input = scanInt(scanner);
                                if (input < 1 || input > 2) {
                                    System.out.println("Choisissez une option valable s'il vous plait\n");
                                    break;
                                }
                                System.out.println("Saisissez le nom du produit que vous voulez ajouter : \n");
                                String pName = scanner.nextLine();
                                if (input == 1)
                                    stock.addSanitary(pName, 0);
                                else {
                                    System.out.println("Saisissez la date limite de consommation du produit s'il vous plait :\n");
                                    textInput = scanner.nextLine();
                                    stock.addFood(pName, 0, textInput);
                                }
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Erreur dans le nom du stock, ou stock inexistant \n");
                            found = false;
                        }
                        break;
                    case 5:
                        System.out.println("Saississez le nom du produit dont vous voulez avoir les caracteristiques:\n");
                        textInput = scanner.nextLine();
                        for (Stock s : shop.getStocks()) {
                            for (Product p : s.getProducts()) {
                                if (p.getName().equals(textInput)) {
                                    System.out.println(p);
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found)
                            System.out.println("Le produit '" + textInput +"' n'a pas été trouvé dans le stock\n");
                        found = false;
                        break;
                    case 6:
                        System.out.println("Saisissez le nom du produit duquel vous voulez modifier la quantité: \n");
                        textInput = scanner.nextLine();
                        for (Stock s : shop.getStocks()) {
                            for (Product p : s.getProducts()) {
                                if (p.getName().equals(textInput)) {
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
                            System.out.println("Le produit '" + textInput +"' n'a pas été trouvé dans le stock\n");
                        found = false;
                        break;
                    case 7:
                        System.out.println("Saisissez le nom du produit que vous voulez vendre : \n");
                        textInput = scanner.nextLine();
                        for (Stock s : shop.getStocks()) {
                            for (Product p : s.getProducts()) {
                                if (p.getName().equals(textInput)) {
                                    found = true;
                                    if (p.canSell()) {
                                        System.out.println("On peut vendre\n");
                                    }
                                }
                            }
                        }
                        if(!found)
                            System.out.println("Le produit '" + textInput +"' n'a pas été trouvé dans le stock\n");
                        found = false;
                        break;
                    case 8: {
                        System.out.println("Saisissez le nom du fichier de sauvegarde :\n");
                        textInput = scanner.nextLine();
                        if (!textInput.contains(".")) {
                            String filename = textInput + ".stock";
                            String stockSave = "";
                            File sauvegarde = new File(filename);
                            System.out.println("Saisissez le nom du stock que vous voulez sauvegarder: \n");
                            textInput = scanner.nextLine();
                            for (Stock s : shop.getStocks()) {
                                if (s.getName().equals(textInput)) {
                                    stockSave += textInput + "{";
                                    for (Product p : s.getProducts()) {
                                        stockSave += p.toSave();
                                    }
                                    stockSave += "}";
                                }
                            }
                            try {
                                FileWriter myWriter = new FileWriter(filename);
                                myWriter.write(stockSave);
                                myWriter.close();
                                System.out.println("Le fichier a été crée avec succès.\n");
                            } catch (IOException e) {
                                System.out.println("Erreur dans la creation du fichier\n.");
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Le nom du fichier ne doit pas contenir des points.\n");
                        }
                        break;
                    }
                    default:
                        System.out.println("Saisissez une bonne option s'il vous plait \n");
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Erreur dans l'input, respectez les formats s'il vous plait !\n");
                scanner.nextLine();
            }
            synchronized (lock) {
                try {
                    lock.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.notify();
            }
        } while (!quit);
    }

}
