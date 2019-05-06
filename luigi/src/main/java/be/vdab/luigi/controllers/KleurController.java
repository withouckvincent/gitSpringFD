package be.vdab.luigi.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("kleuren")
class KleurController {
	@GetMapping
	ModelAndView toonPagina(@CookieValue(name = "Cookiekleur", required = false) String kleur) {
		return new ModelAndView("kleuren", "kleur", kleur);
	}

	@GetMapping("{kleur}")
	ModelAndView kiesKleur(@PathVariable String kleur, HttpServletResponse response) {
		if (kleur.equals("default")) {
			Cookie cookie = new Cookie("Cookiekleur", "");
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("Cookiekleur", kleur);
			cookie.setMaxAge(31_536_000);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return new ModelAndView("kleuren");
	}
}
