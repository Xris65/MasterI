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

    public double getTotalPrice() {
        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.getCost();
        }
        return totalAmount;
    }

    public int getTotalFrequentRenterPoints() {
        int frequentRenterPoints = 0;
        for (Rental rental : rentals) {
            frequentRenterPoints += rental.getFrequentRenterPoints();
        }
        return frequentRenterPoints;
    }

    public String printRentals(){
        StringBuilder rentalInfos = new StringBuilder("");
        for (Rental rental : rentals) {
            rentalInfos.append("\t").append(rental.getMovie().getTitle()).append("\t").append(rental.getCost()).append(" \n");
        }
        return rentalInfos.toString();
    }

    public String statement() {
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\n");
        result.append(printRentals());
        result.append("Amount owned is ").append(getTotalPrice()).append("\n");
        result.append("You earned ").append(getTotalFrequentRenterPoints()).append(" frequent renter points");
        return result.toString();

    }
}
 