package model.Pricing;

public class PricingRegular implements Pricing {
    static public int planCost = 2;
    static public int extraDays = 2;
    static public double supplementCost = 1.5;

    @Override
    public double getPrice(int daysRented) {
        double rentalCost = planCost;
        if (daysRented > extraDays) {
            rentalCost += (daysRented - extraDays) * supplementCost;
        }
        return rentalCost;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return 1;
    }

    @Override
    public Pricing clone() {
        Pricing clone = null;
        try {
            clone = (Pricing) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pricing pricing = (Pricing) o;
        return getPrice(10) == pricing.getPrice(10) && getFrequentRenterPoints(10) == pricing.getFrequentRenterPoints(10);
    }
}
