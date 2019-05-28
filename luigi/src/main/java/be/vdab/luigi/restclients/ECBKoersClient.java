package be.vdab.luigi.restclients;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;


import be.vdab.luigi.exceptions.KoersClientException;


@Order(2)


class ECBKoersClient implements KoersClient {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final URL url;

	public ECBKoersClient(@Value("${ecbKoersURL}") URL url) { 
		this.url = url;
		}

	
/* Vorige constructor hoofdstuk 25	
	ECBKoersClient() {
		try {
			this.url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
		} catch (MalformedURLException ex) {
			String fout = "ECB URL is verkeerd.";
			logger.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}*/

	@Override
	public BigDecimal getDollarKoers() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try (InputStream stream = url.openStream()) {
			XMLStreamReader reader = factory.createXMLStreamReader(stream);
			try {
				while (reader.hasNext()) {
					if (reader.next() == XMLStreamConstants.START_ELEMENT
							&& "USD".equals(reader.getAttributeValue(null, "currency"))) {
						logger.info("koers gelezen via ECB");
						return new BigDecimal(reader.getAttributeValue(null, "rate"));
					}
				}
				String fout = "XML van ECB bevat geen USD";
				logger.error(fout);
				throw new KoersClientException(fout);
			} finally {
				reader.close();
			}
		} catch (IOException | NumberFormatException | XMLStreamException ex) {
			String fout = "kan koers niet lezen via ECB";
			logger.error(fout, ex);
			throw new KoersClientException(fout);
		}
	}
}