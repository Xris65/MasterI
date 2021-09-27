import java.util.ArrayList;

public class Stock  extends ArrayList<Product>{
    private String name;
    private String addr;

    public Stock(String name) {
        this.name = name;
    }
    public ArrayList<Product> getProducts(){
        return this;
    }
    public void addProduct(Product p){
        add(new Product(p.getName(),p.getQuantity()));
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[ name: " + name + ", nombre d'articles : " + size() + "]\n";
    }
}
