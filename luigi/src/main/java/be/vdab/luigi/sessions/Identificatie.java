package be.vdab.luigi.sessions;

import java.io.Serializable;

import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Identificatie implements Serializable {
	private static final long serialVersionUID = 1L;
	@Email
	private String emailAdres;

	
	public String getEmailAdres() {
		return emailAdres;
	}
	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}
	
	
}