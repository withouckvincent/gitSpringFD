package be.vdab.luigi.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import be.vdab.luigi.domain.Pizza;

// enkele imports
public class PizzaControllerTest {
	private PizzaController controller;

	@Before
	public void before() {
		controller = new PizzaController();
	}

	@Test
	public void pizzasGebruiktDeThymeleafPaginaPizzas() {
		assertEquals("pizzas", controller.pizzas().getViewName());
	}

	@Test
	public void pizzasGeeftPizzasDoorAanDeThymeleafPagina() {
		assertTrue(controller.pizzas().getModel().get("pizzas") instanceof Pizza[]);
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
}