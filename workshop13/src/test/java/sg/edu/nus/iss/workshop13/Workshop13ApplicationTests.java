package sg.edu.nus.iss.workshop13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.nus.iss.workshop13.model.Contact;

@SpringBootTest
class Workshop13ApplicationTests {

    @Test
	void contextLoads() {
	}

    @Test
    void testContact() {
        Contact c = new Contact();
		c.setName("Kenneth");
		c.setEmail("a@a.com");
		c.setPhone("1234567");
		// assert equals to the setter value
        assertEquals(c.getEmail(), "a@a.com");
    }

}
