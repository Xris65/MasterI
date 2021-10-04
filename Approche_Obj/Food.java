import java.time.Duration;
import java.time.LocalDate;

public class Food extends Product {
    private LocalDate expiryDate;

    public Food(String name, int quantity, LocalDate expiryDate) {
        super(name, quantity);
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return super.toString()+ ", Best Before : " + expiryDate + " ]";
    }

    @Override
    public String toSave() {
        return super.toSave() + ", " + expiryDate + "]";
    }

    public boolean canSell(){
        return Duration.between(LocalDate.now().atStartOfDay(),expiryDate.atStartOfDay()).toDays() > 3;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
