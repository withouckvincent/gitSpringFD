package be.vdab.luigi.restclients;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.vdab.luigi.exceptions.KoersClientException;



@Order(1)

@Component
class FixerKoersClient implements KoersClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
// importeer Logger en LoggerFactory uit org.slf4j
	private final URL url;
 
	FixerKoersClient(@Value("${fixerKoersURL}") URL url) {
		this.url = url;
		}

	
	/* vorige versie constructor hoofdstuk 25
	FixerKoersClient() {
		try {
			url = new URL(
			"http://data.fixer.io/api/latest?access_key=8aa4eecb235902ddbaed52a27a131d27&symbols=USD");
		} catch (MalformedURLException ex) {
			String fout = "Fixer URL is verkeerd.";
			logger.error(fout, ex); 
			throw new KoersClientException(fout);
		}
	}*/

	@Override
	public BigDecimal getDollarKoers() {
		try (Scanner scanner = new Scanner(url.openStream())) {
			String lijn = scanner.nextLine();
			int beginPositieKoers = lijn.indexOf("USD") + 5;
			int accoladePositie = lijn.indexOf('}', beginPositieKoers);
			return new BigDecimal(lijn.substring(beginPositieKoers, accoladePositie));
		} catch (IOException | NumberFormatException ex) {
			String fout = "kan koers niet lezen via Fixer";
			logger.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
}
