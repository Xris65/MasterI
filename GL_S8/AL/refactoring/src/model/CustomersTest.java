package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CustomersTest {

	private Customers customer;

	@Before
	public void setUp() throws Exception {
		customer = new Customers("Client1");
	}

	@Test
	public void testCustomers() {
		
	}

	@Test
	public void testAddRental() {
		
	}

	@Test
	public void testGetName() {
		assertEquals("Client1",customer.getName());
	}

	@Test
	public void testStatement() {
		
	}

}
