package be.vdab.luigi.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.luigi.sessions.Identificatie;

@Controller
@RequestMapping("identificatie")
class IdentificatieController {
	private final Identificatie identificatie;

	IdentificatieController(Identificatie identificatie) {
		this.identificatie = identificatie;
	}

	@GetMapping
	ModelAndView identificatie() {
		return new ModelAndView("identificatie", "identificatie", identificatie);
	}

	@PostMapping
	String identificatie(@Valid Identificatie identificatie, Errors errors) {
		if (errors.hasErrors()) {
			return "identificatie";
		}
		this.identificatie.setEmailAdres(identificatie.getEmailAdres());
		return "redirect:/";
	}
}
