
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Showroom;
import forms.ActorForm;
import security.Authority;
import services.ActorService;
import services.ShowroomService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ActorService actorService;
    @Autowired
    private ShowroomService showroomService;

    // Constructors -----------------------------------------------------------

    public ActorController() {
        super();
    }


    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display")
    public ModelAndView display(@RequestParam final int actorId, final Integer pageSize, final Integer word) {
        ModelAndView result;

        final Actor actor;
        actor = this.actorService.findOneIfActive(actorId);
        String authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
        if(authority.equals(Authority.USER)){
            Collection <Showroom> showrooms = showroomService.findByUserId(actorId);
            result = new ModelAndView("actor/display");
            result.addObject("actorForm", actor);
            result.addObject("actorAuthority", authority);
            result.addObject("display", true);
            result.addObject("showrooms", showrooms);
            result.addObject("display", true);
            result.addObject("pageSize", (pageSize != null) ? pageSize : 5);

        }else {
            result = new ModelAndView("redirect:/");
        }


        return result;
    }


    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final ActorForm model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final ActorForm model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("actor/create");
        result.addObject("actorForm", model);
        result.addObject("actorAuthority", model.getAuthority());
        result.addObject("requestUri", "actor/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}