package be.vdab.luigi.sessions;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Mandje implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Te bewaren gegevens per sessie // Zo weinig mogelijk !
	private final Set<Long> ids = new LinkedHashSet<>();

	public void voegToe(long id) {
		ids.add(id);
	}

	public boolean bevat(long id) {
		return ids.contains(id);
	}

	public boolean isGevuld() {
		return !ids.isEmpty();
	}
}
