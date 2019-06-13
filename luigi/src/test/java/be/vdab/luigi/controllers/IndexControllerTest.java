package be.vdab.luigi.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class IndexControllerTest {
	private IndexController controller;

	@Before
	public void before() {
		controller = new IndexController();
		
		//aantalKeerBekeken = new AtomicInteger(1);
	}

	@Test
	public void indexGebruiktDeThymeleafPaginaIndex() {
		ModelAndView modelAndView = controller.index();
		assertEquals("index", modelAndView.getViewName());
	}

	
}
