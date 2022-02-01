import model.Customer;
import model.Pricing.PricingChildren;
import model.Pricing.PricingNewRelease;
import model.Pricing.PricingRegular;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("Client1");
    }

    @Test
    public void testCustomers() {

    }

    @Test
    public void testAddRental() {
        Vector<model.Rental> rentals = new Vector<model.Rental>();
        rentals.add(new model.Rental(new model.Movie("Movie1", new PricingNewRelease()), 2));
        customer.addRental(new model.Rental(new model.Movie("Movie1", new PricingNewRelease()), 2));
        assertEquals(rentals.size(), customer.getRentals().size());
        for(int i = 0; i < rentals.size(); i++) {
            assertEquals(rentals.get(i), customer.getRentals().get(i));
        }
    }

    @Test
    public void testGetName() {
        assertEquals("Client1", customer.getName());
    }

    @Test
    public void testStatement() {
        assertEquals("""
                Rental Record for Client1
                Amount owned is 0.0
                You earned 0 frequent renter points""", customer.statement());
        customer.addRental(new model.Rental(new model.Movie("Rogue One", new PricingNewRelease()), 5));
        customer.addRental(new model.Rental(new model.Movie("Reine des neiges", new PricingChildren()), 7));
        customer.addRental(new model.Rental(new model.Movie("Star Wars III", new PricingRegular()), 4));
        assertEquals("""
                Rental Record for Client1
                	Rogue One	15.0\s
                	Reine des neiges	7.5\s
                	Star Wars III	5.0\s
                Amount owned is 27.5
                You earned 4 frequent renter points""", customer.statement());
    }
}
