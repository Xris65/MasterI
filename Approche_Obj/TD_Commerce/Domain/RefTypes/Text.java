package Domain.RefTypes;
public class Text{
    private String text;
    public Text(String text, int tailleMax) {
        if(text == null) throw new IllegalArgumentException("Reference cannot be null");
        else if(text.length() > tailleMax && text.length() == 0 && tailleMax <= 0) throw new IllegalArgumentException("La taille du texte n'est pas correct");
        this.text = text;
    }
    public String getText(){
        return text;
    }
}