import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Stock  extends ArrayList<Product>{
    private String name;
    private String addr;

    public Stock(String name) {
        this.name = name;
    }
    public ArrayList<Product> getProducts(){
        return this;
    }
    public void addFood(String name, int quantity, String BBD){
        LocalDate dateLC = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        try{
            dateLC = LocalDate.parse(BBD,formatter);
        }
        catch (Exception e){
            System.out.println("Saisissez une bonne date s'il vous plait !\n");
        }
        if(dateLC != null) {
            add(new Food(name, quantity, dateLC));
            System.out.println("Fait!\n");
        }
    }
    public void addSanitary(String name, int quantity){
        add(new Sanitary(name,quantity));
        System.out.println("Fait!\n");
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[ name: " + name + ", nombre d'articles : " + size() + "]\n";
    }
}
