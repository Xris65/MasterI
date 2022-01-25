package model;

import java.util.Objects;

public class Rental {
    public static final double PrixForfaitRegular = 2;
    public static final double PrixForfaitNewrelease = 3;
    public static final double PrixForfaitChildren = 1.5;
    public static final int NombreJoursForfaitRegular = 2;
    public static final int NombreJoursForfaitChildren = 3;

    private Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getCost() {
        double rentalCost = 0;
        switch (movie.getPriceCode()) {
            case Movie.REGULAR -> {
                rentalCost += PrixForfaitRegular;
                if (daysRented > NombreJoursForfaitRegular) {
                    rentalCost += (daysRented - NombreJoursForfaitRegular) * PrixForfaitChildren;
                }
            }
            case Movie.NEW_RELEASE -> rentalCost += daysRented * PrixForfaitNewrelease;
            case Movie.CHILDREN -> {
                rentalCost += PrixForfaitChildren;
                if (daysRented > NombreJoursForfaitChildren)
                    rentalCost += (daysRented - NombreJoursForfaitChildren) * PrixForfaitChildren;
            }
        }
        return rentalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return daysRented == rental.daysRented && Objects.equals(movie, rental.movie);
    }


    @Override
    public int hashCode() {
        return Objects.hash(movie, daysRented);
    }
}
