
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/term")
public class TermController extends AbstractController {

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("term/cookies");
		return result;
	}

	@RequestMapping(value = "/termsAndConditions")
	public ModelAndView termsAndConditions() {
		ModelAndView result;

		result = new ModelAndView("term/termsAndConditions");
		return result;
	}
}
