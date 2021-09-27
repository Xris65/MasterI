import java.util.ArrayList;

public class Stock {
    private ArrayList<Product> products;
    private String name;
    private String addr;

    public Stock(String name) {
        this.name = name;
    }
    public ArrayList<Product> getProducts(){
        return products;
    }
    public void addProduct(Product p){
        products.add(new Product(p.getName(),p.getQuantity()));
    }
    public String getName() {
        return name;
    }
}
