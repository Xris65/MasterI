package model;

import model.Pricing.Pricing;

import java.util.Objects;

public class Rental {
    private Movie movie;
    private int daysRented;
    private Pricing pricing;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.pricing = movie.getPricing();
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }


    public double getPrice(double price, double fees, int daysRented) {
        return (price + fees) * daysRented;
    }
    public double getCost(){
        return pricing.getPrice(daysRented);
    }

    public int getFrequentRenterPoints() {
        return pricing.getFrequentRenterPoints(daysRented);
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rental rental = (Rental) o;
        return daysRented == rental.daysRented && movie.equals(rental.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, daysRented);
    }
}
