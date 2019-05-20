package be.vdab.luigi.restclients;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(ECBKoersClient.class)
@PropertySource("application.properties")


// enkele imports
public class ECBKoersClientTest {
	@Autowired
	private ECBKoersClient client;

/*	@Before
	public void before() {
		client = new ECBKoersClient(null);
	}*/

	@Test
	public void deKoersMoetPositiefZijn() {
		assertTrue(client.getDollarKoers().compareTo(BigDecimal.ZERO) > 0);
	}
}