
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import forms.ActorForm;
import forms.AdminForm;
import security.Authority;
import services.ActorService;
import services.AdministratorService;

@Controller
@RequestMapping("/admin/administrator")
public class AdminAdministratorController extends AbstractController {

    // Supporting services -----------------------------------------------------
    @Autowired
    protected ActorService actorService;
    @Autowired
    protected AdministratorService adminService;

    // Constructors -----------------------------------------------------------

    public AdminAdministratorController() {
        super();
    }


    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        final AdminForm registerForm = new AdminForm();
        result = this.createEditModelAndView(registerForm, null);
        return result;
    }

    // Edit ---------------------------------------------------------------

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView result;
        ActorForm model;

        try {
            final Actor actor = this.actorService.findByPrincipal();
            model = new AdminForm(actor);
            result = this.createEditModelAndView(model, null);
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }

        return result;
    }

    // Save mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("actorForm") final AdminForm adminForm, final BindingResult binding) {
        ModelAndView result;
        Administrator actor;

        try {
            actor = this.adminService.reconstruct(adminForm, binding);
            if (binding.hasErrors())
                result = this.createEditModelAndView(adminForm);
            else
                try {
                    this.actorService.save(actor);
                    result = new ModelAndView("redirect:/");
                } catch (final Throwable oops) {
                    if (oops.getCause().getCause() != null
                            && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                        result = this.createEditModelAndView(adminForm, "msg.duplicate.username");
                    else
                        result = this.createEditModelAndView(adminForm, "msg.commit.error");
                }

        } catch (final Throwable oops) {
            if (oops.getLocalizedMessage().startsWith("msg."))
                result = this.createEditModelAndView(adminForm, oops.getLocalizedMessage());
            else if (oops.getCause().getCause() != null
                    && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                result = this.createEditModelAndView(adminForm, "msg.duplicate.username");
            else
                result = this.createEditModelAndView(adminForm, "actor.reconstruct.error");
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
        result.addObject("actorAuthority", Authority.ADMINISTRATOR);
        result.addObject("requestUri", "admin/administrator/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }



}
