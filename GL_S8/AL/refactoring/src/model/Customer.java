package model;

import java.util.Vector;


public class Customer {
    private String name;
    private Vector<Rental> rentals = new Vector<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public Vector<Rental> getRentals() {
        return rentals;
    }

    public String getName() {
        return name;
    }


    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\n");
        for (Rental rental : rentals) {
            double thisAmount = 0;
            thisAmount += rental.getCost();
            frequentRenterPoints++;
            if (rental.getMovie().getPriceCode() == Movie.NEW_RELEASE && rental.getDaysRented() > 1)
                frequentRenterPoints++;
            result.append("\t").append(rental.getMovie().getTitle()).append("\t").append(thisAmount).append(" \n");
            totalAmount += thisAmount;
        }
        result.append("Amount owned is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
        return result.toString();

    }
}
 