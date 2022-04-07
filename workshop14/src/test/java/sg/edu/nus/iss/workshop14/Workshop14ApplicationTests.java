package sg.edu.nus.iss.workshop14;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import sg.edu.nus.iss.workshop14.controller.AddressBookController;

@SpringBootTest
class Workshop14ApplicationTests {

	@Autowired
	private AddressBookController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
