package be.vdab.luigi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// hoofdstuk 37.1
//@ImportResource("restclients.xml")

@SpringBootApplication
public class LuigiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuigiApplication.class, args);
	}

}
