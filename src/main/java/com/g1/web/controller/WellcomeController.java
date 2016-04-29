package com.g1.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class WellcomeController {

	private static final Logger logger = Logger.getLogger(WellcomeController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String hello(ModelMap model) {
		logger.info("wellcomeController request");
		model.addAttribute("helloMsg", "Hello user");
		return "welcome";
	}
}
