package be.vdab.luigi.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.services.EuroService;

@Controller
@RequestMapping("pizzas")
class PizzaController {

	// private final String[] pizzas = new String[] { "Prosciutto", "Margherita",
	// "Calzone" };

	private final Pizza[] pizzas = { new Pizza(1, "Prosciutto", BigDecimal.valueOf(4), true),
			new Pizza(2, "Margherita", BigDecimal.valueOf(5), false),
			new Pizza(3, "Calzone", BigDecimal.valueOf(4), false) };
	
	private final EuroService euroService;

	PizzaController(EuroService euroService) { 
		this.euroService = euroService;
		}

	
	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView("pizzas", "pizzas", pizzas);
	}
 
	// nieuw hoofdstuk 16

	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("pizza");
		Arrays.stream(pizzas).filter(pizza -> pizza.getId() == id).findFirst().ifPresent(pizza -> {
			modelAndView.addObject("pizza", pizza);
			modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
		});

		return modelAndView;
	}

	// hoofdstuk 21
	private List<BigDecimal> uniekePrijzen() {
		return Arrays.stream(pizzas).map(pizza -> pizza.getPrijs()).distinct().sorted().collect(Collectors.toList());
	}

	@GetMapping("prijzen")
	ModelAndView prijzen() {
		return new ModelAndView("prijzen", "prijzen", uniekePrijzen());
	}

	private List<Pizza> pizzasMetPrijs(BigDecimal prijs) {
		return Arrays.stream(pizzas).filter(pizza -> pizza.getPrijs().compareTo(prijs) == 0)
				.collect(Collectors.toList());
	}

//	@GetMapping("prijzen/{prijs}")
//	ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
//		return new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs));
//	}

	@GetMapping("prijzen/{prijs}")
	ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
		ModelAndView modelAndView = new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs));
		modelAndView.addObject("prijzen", uniekePrijzen());
		// of : return new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs)).addObject("prijzen", uniekePrijzen());
		
		return modelAndView;
	}

}
