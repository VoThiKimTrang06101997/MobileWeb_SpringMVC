package com.trang.UserController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping(value = { "/", "trang-chu" }, method = RequestMethod.GET)
	public ModelAndView homepage() {
		ModelAndView modelAndView = new ModelAndView("user/index");
		// modelAndView.setViewName("user/index");
		return modelAndView;
	}
}
