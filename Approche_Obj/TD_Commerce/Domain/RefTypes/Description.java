package Domain.RefTypes;
public class Description extends Text{
    public Description(String description) {
        super(description,220);
    }
    public String getDescription(){
        return super.getText();
    }
}