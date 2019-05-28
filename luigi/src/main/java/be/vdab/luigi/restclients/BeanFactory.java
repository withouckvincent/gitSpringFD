package be.vdab.luigi.restclients;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BeanFactory {
	private final URL ecbKoersURL;
	private final URL fixerKoersURL;

	BeanFactory(@Value("${ecbKoersURL}") URL ecbKoersURL,

			@Value("${fixerKoersURL}") URL fixerKoersURL) {
		this.ecbKoersURL = ecbKoersURL;
		this.fixerKoersURL = fixerKoersURL;
	}

	@Bean
	ECBKoersClient ecbKoersClient() {
		return new ECBKoersClient(ecbKoersURL);
	}

	@Bean
	FixerKoersClient fixerKoersClient() {
		return new FixerKoersClient(fixerKoersURL);
	}
}
