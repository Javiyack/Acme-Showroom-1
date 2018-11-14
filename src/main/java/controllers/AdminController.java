
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administrator")
public class AdminController extends AbstractController {

    // Supporting services -----------------------------------------------------

    // Constructors -----------------------------------------------------------

    public AdminController() {
        super();
    }


    // Edit ---------------------------------------------------------------

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("redirect:/admin/administrator/edit.do");
        return result;
    }
}
