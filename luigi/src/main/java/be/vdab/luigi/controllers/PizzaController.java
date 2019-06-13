package be.vdab.luigi.controllers;

import java.math.BigDecimal;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.forms.VanTotPrijsForm;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;

@Controller
@RequestMapping("pizzas")
class PizzaController {


	private final EuroService euroService;
	private final PizzaService pizzaService;

	PizzaController(EuroService euroService, PizzaService pizzaService) {
		this.euroService = euroService;
		this.pizzaService = pizzaService;
	}

	@GetMapping
	ModelAndView pizzas() {
		return new ModelAndView("pizzas", "pizzas", pizzaService.findAll());
	}

	@PostMapping
	String toevoegen(@Valid Pizza pizza, Errors errors, RedirectAttributes redirect) {
		// Onderstaande testen is nog niet van toepassing tot op heden (later)
		if (errors.hasErrors()) {
			return "toevoegen";
		}
		long id = pizzaService.create(pizza);
		redirect.addAttribute("toegevoegd", id); 
		return "redirect:/pizzas";
	}


	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("pizza");
		pizzaService.findById(id).ifPresent(pizza -> {
			modelAndView.addObject(pizza);
			modelAndView.addObject("inDollar", euroService.naarDollar(pizza.getPrijs()));

		});
		return modelAndView;
	}


	@GetMapping("prijzen")
	ModelAndView prijzen() {
		return new ModelAndView("prijzen", "prijzen", pizzaService.findUniekePrijzen());
	}


	@GetMapping("prijzen/{prijs}")
	ModelAndView pizzasMetEenPrijs(@PathVariable BigDecimal prijs) {
		return new ModelAndView("prijzen", "pizzas", pizzaService.findByPrijs(prijs)).addObject("prijzen",
				pizzaService.findUniekePrijzen());
	}

	@GetMapping("vantotprijs/form")
	ModelAndView vanTotPrijsForm() {
		return new ModelAndView("vantotprijs").addObject(new VanTotPrijsForm(null, null));
	}

	@GetMapping("vantotprijs")
	ModelAndView vanTotPrijs(@Valid VanTotPrijsForm form, Errors errors) {
		ModelAndView modelAndView = new ModelAndView("vantotprijs");
		if (errors.hasErrors()) {
			return modelAndView;
		}
		return modelAndView.addObject("pizzas", pizzaService.findByPrijsBetween(form.getVan(), form.getTot()));
	}

	@GetMapping("toevoegen/form")
	ModelAndView toevoegenForm() {
		return new ModelAndView("toevoegen").addObject(new Pizza(0, "", null, false));
	}

}
