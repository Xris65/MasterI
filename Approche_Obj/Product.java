public abstract class  Product {
    private String name;
    private int quantity;
    private static int count = 0;
    private int id;

    public int getQuantity() {
        return quantity;
    }

    public Product(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
        this.id = count++;
    }

    public String getName(){
        return name;
    }
    public void modifyQuantity(int num){
        if(quantity + num < 0)
            quantity = 0;
        else
            quantity += num;
    }


    public int getId() {
        return id;
    }

    public boolean canSell() {
        return true;
    }

    @Override
    public String toString() {
        return "[ id: "+ id +", " + name + ", quantity: " + quantity;
    }
    public String toSave(){
        return "[" + name + "," + quantity;
    }
}
