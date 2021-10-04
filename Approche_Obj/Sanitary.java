import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sanitary extends Product{
    public Sanitary(String name, int quantity) {
        super(name, quantity);
    }

    @Override
    public String toString() {
        return super.toString() + " ]";
    }

    @Override
    public String toSave() {
        return super.toSave() + "]";
    }
}
