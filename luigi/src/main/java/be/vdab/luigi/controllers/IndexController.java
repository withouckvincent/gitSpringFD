package be.vdab.luigi.controllers;

import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;

//hier komen imports.
//Eclipse voegt ontbrekende imports toe met de sneltoetsen shift+ctrl+o.

class IndexController {

	@GetMapping
	String index() {
		StringBuffer buffer = new StringBuffer("<!doctype html><html><title>Hallo</title><body>");
		int uur = LocalTime.now().getHour();
		if (uur < 12) {
			buffer.append("Goede morgen");
		} else if (uur < 18) {
			buffer.append("Goede middag");
		} else {
			buffer.append("Goede avond");
		}
		buffer.append("</body></html>"); 
		return buffer.toString();
	}

}
