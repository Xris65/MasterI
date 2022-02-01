package model.Pricing;

public class PricingChildren implements Pricing {
    static public double planCost = 1.5;
    static public int extraDays = 3;
    static public double extraCost = 1.5;

    @Override
    public double getPrice(int daysRented) {
        double rentalCost = planCost;
        if (daysRented > extraDays){
            rentalCost += (daysRented - extraDays) * extraCost;
        }
        return rentalCost;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return 1;
    }

    public Pricing getClone() {
        Pricing clone = null;
        try {
            clone = (Pricing) this.clone();
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
