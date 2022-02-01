package model;

import model.Pricing.Pricing;

import java.util.Objects;

public class Movie {
    private Pricing pricing;
    private String title;
    public Movie(String title, Pricing pricing) {
        this.title = title;
        this.pricing = pricing;
    }
    public Pricing getPricing(){
        return pricing.getClone();
    }
    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public String getTitle() {
        return title;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return pricing.equals(movie.pricing) && Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, pricing);
    }
}
