package be.vdab.luigi.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.luigi.domain.Adres;
import be.vdab.luigi.domain.Persoon;

//hier komen imports.
//Eclipse voegt ontbrekende imports toe met de sneltoetsen shift+ctrl+o.

@Controller
@RequestMapping("/")
class IndexController {

	private final AtomicInteger aantalKeerBekeken = new AtomicInteger();
	// Controller advice maakt dit onderstande overbodig.
	/*
	 * private final Identificatie identificatie; // sessie
	 * 
	 * IndexController(Identificatie identificatie) { // sessie this.identificatie =
	 * identificatie; // sessie } // sessie
	 */

	// hoofdstuk 12
	private String boodschap() {
		int uur = LocalTime.now().getHour();
		if (uur < 12) {
			return "morgen";
		}
		if (uur < 18) {
			return "middag";
		}
		return "avond";
	}

	@GetMapping
	ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index", "boodschap", boodschap());
		modelAndView.addObject("zaakvoerder", new Persoon("Luigi", "Peperone", 7, true, LocalDate.of(1966, 1, 31),
				new Adres("Grote markt", "3", 9700, "Oudenaarde")));
		modelAndView.addObject("aantalKeerBekeken", aantalKeerBekeken.incrementAndGet());
		// Controller advice maakt dit onderstande overbodig.
		// modelAndView.addObject(identificatie); // sessie
		return modelAndView;
	}

	// hoofdstuk 11
//			@GetMapping
//			ModelAndView index() {			
//		ModelAndView modelAndView=new ModelAndView("index");
//		
//		int uur = LocalTime.now().getHour();
//		if (uur < 12) {
//			modelAndView.addObject("boodschap","morgen");
//		}
//		if (uur < 18 && uur >= 12) {			
//			modelAndView.addObject("boodschap","middag");
//		}
//		if (uur >= 18) {			
//			modelAndView.addObject("boodschap","avond");
//		}
//		
//		modelAndView.addObject("kinderen",3);
//		modelAndView.addObject("koersEuro", BigDecimal.valueOf(40.3399));
//		modelAndView.addObject("getal",8);
//		modelAndView.addObject("namen1", new String[] {"Joe", "Averell"});
//		modelAndView.addObject("namen2", Arrays.asList("Joe", "Averell"));
//		
//		Map<String,String> karakters = new HashMap<>();
//		karakters.put("Joe", "driftig");
//		karakters.put("Averell", "hongerig");
//		modelAndView.addObject("karakters",karakters);
//		
//		return modelAndView;
//	}
}