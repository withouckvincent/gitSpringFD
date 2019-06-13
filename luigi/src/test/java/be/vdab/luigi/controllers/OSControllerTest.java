package be.vdab.luigi.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class OSControllerTest {
	private static final String USER_AGENT_REQUEST_HEADER_MET_WINDOWS = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0";
	private OSController controller;

	@Before
	public void before() {
		controller = new OSController();
	}

	@Test
	public void osGebruiktDeThymeleafPaginaOs() {
		ModelAndView modelAndView = controller.os(USER_AGENT_REQUEST_HEADER_MET_WINDOWS);
		assertEquals("os", modelAndView.getViewName());
	}
	
	@Test
	public void alsDeUserAgentRequestHeaderHetWoordWindowsBevatMoetDeOsDataWindowsBevatten() {
		ModelAndView modelAndView = controller.os(USER_AGENT_REQUEST_HEADER_MET_WINDOWS);
		String os = (String) modelAndView.getModel().get("os");
		assertEquals("Windows", os);
	}
	
	@Test
	public void alsDeUserAgentRequestHeaderEenOnBekendOSBevatMagDeControllerGeenOSDoorgeven() {
		ModelAndView modelAndView = controller.os("blabla");
		assertFalse(modelAndView.getModel().containsKey("os"));
	}
	
	
	

}
