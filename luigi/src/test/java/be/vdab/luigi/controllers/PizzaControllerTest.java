package be.vdab.luigi.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.services.EuroService;
import be.vdab.luigi.services.PizzaService;


@RunWith(MockitoJUnitRunner.class)

public class PizzaControllerTest {
	private PizzaController controller;

	@Mock
	private EuroService euroService;

	@Mock
	private PizzaService pizzaService;

	
	@Before
	public void before() {
		when(pizzaService.findById(1)).thenReturn(Optional.of(new Pizza(1, "Test", BigDecimal.ONE, true)));
		controller = new PizzaController(euroService, pizzaService);
		
	}
	
 
	@Test
	public void pizzasGebruiktDeThymeleafPaginaPizzas() {
		assertEquals("pizzas", controller.pizzas().getViewName());
	}

	@Test
	public void pizzasGeeftPizzasDoorAanDeThymeleafPagina() {
		assertTrue(controller.pizzas().getModel().get("pizzas") instanceof List);
	}

	@Test
	public void pizzaGebruiktDeThymeleafPaginaPizza() {
		assertEquals("pizza", controller.pizza(1).getViewName());
	}

	@Test
	public void pizzaGeeftGevondenPizzaDoorAanDeThymeleafPagina() {
		Pizza pizza = (Pizza) controller.pizza(1).getModel().get("pizza");
		assertEquals(1, pizza.getId());
	}

	@Test
	public void pizzaGeeftOnbestaandePizzaNietDoorAanDeThymeleafPagina() {
		assertFalse(controller.pizza(-1).getModel().containsKey("pizza"));
	}
	
	@Test
	public void pizzaGeeftUniekePrijzen() {
			List<BigDecimal> prijzen=(List<BigDecimal>) controller.prijzen().getModel().get("prijzen");
			Set<BigDecimal> uniekePrijzen=new TreeSet<>(prijzen);
			assertEquals(uniekePrijzen.size(), prijzen.size());
			int i=0;
			for (BigDecimal prijs:uniekePrijzen) {
				assertEquals(prijs, prijzen.get(i++));
			}
	}
}