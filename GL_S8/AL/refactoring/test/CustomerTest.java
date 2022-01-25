import model.Customer;
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
        rentals.add(new model.Rental(new model.Movie("Movie1", 10), 2));
        customer.addRental(new model.Rental(new model.Movie("Movie1", 10), 2));
        assertEquals(rentals, customer.getRentals());
    }

    @Test
    public void testGetName() {
        assertEquals("Client1", customer.getName());
    }

    @Test
    public void testStatement() {
        assertEquals("Rental Record for Client1\n" + "Amount owned is 0.0\n" + "You earned 0 frequent renter points", customer.statement());
        customer.addRental(new model.Rental(new model.Movie("Rogue One", 1), 5));
        customer.addRental(new model.Rental(new model.Movie("Reine des neiges", 2), 7));
        customer.addRental(new model.Rental(new model.Movie("Star Wars III", 0), 4));
        assertEquals("Rental Record for Client1\n" + "	Rogue One	15.0 \n" + "	Reine des neiges	7.5 \n" + "	Star Wars III	5.0 \n" + "Amount owned is 27.5\n" + "You earned 4 frequent renter points", customer.statement());
    }
}
