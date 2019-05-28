package be.vdab.luigi.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import be.vdab.luigi.sessions.Identificatie;

@ControllerAdvice
class MyControllerAdvice {
	private final Identificatie identificatie;

	MyControllerAdvice(Identificatie identificatie) {
		this.identificatie = identificatie;
	}

	@ModelAttribute
	void extraDataToevoegenAanModel(Model model) {
		model.addAttribute(identificatie);
	}
}
