package model.Pricing;

public interface Pricing extends Cloneable {
    public double getPrice(int daysRented);
    public int getFrequentRenterPoints(int daysRented);
    public Pricing getClone();
    public boolean equals(Object o);
}
