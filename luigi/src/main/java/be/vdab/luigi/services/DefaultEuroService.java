package be.vdab.luigi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import be.vdab.luigi.exceptions.KoersClientException;
import be.vdab.luigi.restclients.KoersClient;

@Service
class DefaultEuroService implements EuroService {
	//private final KoersClient koersClient;
	private final KoersClient[] koersClients;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	DefaultEuroService(KoersClient[] koersClients) {
		this.koersClients  = koersClients ;
	}

//	@Override
//	public BigDecimal naarDollar(BigDecimal euro) {
//		return euro.multiply(koersClient.getDollarKoers()).setScale(2, RoundingMode.HALF_UP);
//	}

	@Override
	public BigDecimal naarDollar(BigDecimal euro) {
		for (KoersClient koersClient : koersClients) {
			try {
				return euro.multiply(koersClient.getDollarKoers()).setScale(2, RoundingMode.HALF_UP);
			} catch (KoersClientException ex) {
				logger.error("kan dollar koers niet lezen", ex);
			}
		}
		logger.error("kan dollar koers van geen enkele bron lezen");
		return null;
	}

}