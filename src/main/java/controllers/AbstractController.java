/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import exceptions.HackingException;

@Controller
public class AbstractController {
	
	private boolean enDesarrollo = true;

// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;
		if(oops.getCause() instanceof HackingException) {
			result = new ModelAndView("misc/hacking");
		}else if (enDesarrollo) {
			result = new ModelAndView("misc/panic");
			result.addObject("name", ClassUtils.getShortName(oops.getClass()));
			result.addObject("exception", oops.getMessage());
			result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		} else {			
			result = this.createMessageModelAndView("panic.message.text", "/");
		}

		return result;
	}

	
	public ModelAndView createMessageModelAndView(final String messageText, final String goBackUrl) {
		ModelAndView result;
		result = new ModelAndView("misc/message");
		result.addObject("messageText", messageText);
		result.addObject("goBackUrl", goBackUrl);		
		return result;
	}

}
