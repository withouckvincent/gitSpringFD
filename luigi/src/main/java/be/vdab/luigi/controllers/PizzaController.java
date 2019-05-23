package be.vdab.luigi.controllers;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.luigi.forms.VanTotPrijsForm;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;

@Controller
@RequestMapping("pizzas")
class PizzaController {

	// private final String[] pizzas = new String[] { "Prosciutto", "Margherita",
	// "Calzone" };

	// aanpassing bij hoofdstuk 30.6
	/*
	 * private final Pizza[] pizzas = { new Pizza(1, "Prosciutto",
	 * BigDecimal.valueOf(4), true), new Pizza(2, "Margherita",
	 * BigDecimal.valueOf(5), false), new Pizza(3, "Calzone", BigDecimal.valueOf(4),
	 * false) };
	 * 
	 * private final EuroService euroService;
	 * 
	 * PizzaController(EuroService euroService) { this.euroService = euroService; }
	 */

	private final EuroService euroService;
	private final PizzaService pizzaService;

	PizzaController(EuroService euroService, PizzaService pizzaService) {
		this.euroService = euroService;
		this.pizzaService = pizzaService;
	}

	@GetMapping
	ModelAndView pizzas() {
		// hoofdstuk 30.6 : return new ModelAndView("pizzas", "pizzas", pizzas);
		return new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
	}

	// nieuw hoofdstuk 16

	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("pizza");
		/*
		 * hoofdstuk 30.6 Arrays.stream(pizzas).filter(pizza -> pizza.getId() ==
		 * id).findFirst().ifPresent(pizza -> { modelAndView.addObject("pizza", pizza);
		 * modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));
		 */
		pizzaService.findById(id).ifPresent(pizza -> {
			modelAndView.addObject(pizza);
			modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));

		});

		return modelAndView;
	}

	/*
	 * hoofdstuk 30.6 // hoofdstuk 21 private List<BigDecimal> uniekePrijzen() {
	 * return Arrays.stream(pizzas).map(pizza ->
	 * pizza.getPrijs()).distinct().sorted().collect(Collectors.toList()); // return
	 * new ModelAndView("prijzen", "prijzen", pizzaService.findUniekePrijzen());
	 * 
	 * }
	 */

	@GetMapping("prijzen")
	ModelAndView prijzen() {
		// hoofdstuk 30.6 :return new ModelAndView("prijzen", "prijzen",
		// uniekePrijzen());
		return new ModelAndView("prijzen", "prijzen", pizzaService.findUniekePrijzen());
	}

	/*
	 * hoofdstuk 30.6 private List<Pizza> pizzasMetPrijs(BigDecimal prijs) { return
	 * Arrays.stream(pizzas).filter(pizza -> pizza.getPrijs().compareTo(prijs) ==
	 * 0).collect(Collectors.toList()); }
	 */

//	@GetMapping("prijzen/{prijs}")
//	ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
//		return new ModelAndView("prijzen", "pizzas", pizzasMetPrijs(prijs));
//	}

	/*
	 * hoofdstuk 30.6
	 * 
	 * @GetMapping("prijzen/{prijs}") ModelAndView pizzasMetEenPrijs(@PathVariable
	 * BigDecimal prijs) { ModelAndView modelAndView = new ModelAndView("prijzen",
	 * "pizzas", pizzasMetPrijs(prijs)); modelAndView.addObject("prijzen",
	 * uniekePrijzen()); // of : return new ModelAndView("prijzen", "pizzas", //
	 * pizzasMetPrijs(prijs)).addObject("prijzen", uniekePrijzen());
	 * 
	 * return modelAndView; }
	 */

	@GetMapping("prijzen/{prijs}")
	ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
		return new ModelAndView("prijzen", "pizzas", pizzaService.findByPrijs(prijs)).addObject("prijzen",
				pizzaService.findUniekePrijzen());
	}

	@GetMapping("vantotprijs/form")
	ModelAndView vanTotPrijsForm() {
		// return new ModelAndView("vantotprijs").addObject(new
		// VanTotPrijsForm(BigDecimal.ONE, BigDecimal.TEN));
		return new ModelAndView("vantotprijs").addObject(new VanTotPrijsForm(null, null));
	}

	@GetMapping("vantotprijs")
	ModelAndView vanTotPrijs( @Valid VanTotPrijsForm form, Errors errors) {
		ModelAndView modelAndView = new ModelAndView("vantotprijs");
		if (errors.hasErrors()) {
			return modelAndView;
		}
		return modelAndView.addObject("pizzas", pizzaService.findByPrijsBetween(form.getVan(), form.getTot()));
	}

}
