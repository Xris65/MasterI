package model.Pricing;

public class PricingNewRelease implements Pricing {
    static public double planCost = 3;

    @Override
    public double getPrice(int daysRented) {
        return daysRented * planCost;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return (daysRented > 1) ? 2 : 1;
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
