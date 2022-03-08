public class StatementBuilder implements Builder{
    private StringBuilder builder = new StringBuilder();

    @Override
    public void build() {

    }

    @Override
    public void reset() {
        
    }

    public void createNewStatement() {
        builder = new StringBuilder();
    }

    public void buildHeader(String name) {
        builder.append("Rental Record for ").append(name).append("\n");
    }

    public void buildRental(Rental rental) {
        builder.append("\t").append(rental.getMovie().getTitle()).append("\t").append(String.valueOf(rental.getAmount())).append("\n");
    }

    public void buildAmount(double totalAmount) {
        builder.append("Amount owed is ").append(String.valueOf(totalAmount)).append("\n");  
    }

    public void buildRenterPoints(int renterPoints) {
        builder.append("You earned ").append(String.valueOf(renterPoints)).append(" frequent renter points").append("\n");
    }

    public String getStatement() {
        return builder.toString();
    }


}
