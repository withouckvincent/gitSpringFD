package be.vdab.luigi.controllers;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.luigi.domain.Pizza;

@Controller
@RequestMapping("pizzas")
class PizzaController {
	
	
	//private final String[] pizzas = new String[] { "Prosciutto", "Margherita", "Calzone" };

	private final Pizza[] pizzas = {
			new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
			new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
			new Pizza(3, "Calzone", BigDecimal.valueOf(4), false)};

	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView("pizzas", "pizzas", pizzas);
	}
}
