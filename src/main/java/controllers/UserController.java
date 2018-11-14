
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import forms.UserForm;
import security.Authority;
import services.ActorService;
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ActorService actorService;
    @Autowired
    private UserService userService;


    // Constructors -----------------------------------------------------------

    public UserController() {
        super();
    }


    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        final UserForm registerForm = new UserForm();
        result = new ModelAndView("actor/create");
        result.addObject("actorForm", registerForm);
        result.addObject("actorAuthority", Authority.USER);
        result.addObject("requestUri", "user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", registerForm.getId() == 0);
        return result;
    }

    // Edit ---------------------------------------------------------------

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView result;
        UserForm model;

        try {
            final User user = (User) this.actorService.findByPrincipal();
            model = new UserForm(user);
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
    public ModelAndView save(@ModelAttribute("actorForm") final UserForm userForm, final BindingResult binding) {
        ModelAndView result;
        User user;
        try {
            user = this.userService.reconstruct(userForm, binding);
            if (binding.hasErrors())
                result = this.createEditModelAndView(userForm);
            else
                try {
                    this.actorService.save(user);
                    result = new ModelAndView("redirect:/");
                } catch (final Throwable oops) {
                    if (oops.getCause().getCause() != null
                            && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                        result = this.createEditModelAndView(userForm, "msg.duplicate.username");
                    else
                        result = this.createEditModelAndView(userForm, "msg.commit.error");
                }

        } catch (final Throwable oops) {
            if (oops.getLocalizedMessage().startsWith("msg."))
                result = this.createEditModelAndView(userForm, oops.getLocalizedMessage());
            else if (oops.getCause().getCause() != null
                    && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                result = this.createEditModelAndView(userForm, "msg.duplicate.username");
            else
                result = this.createEditModelAndView(userForm, "user.reconstructActor.error");
        }

        return result;
    }


    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final UserForm model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final UserForm model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("actor/edit");
        result.addObject("actorForm", model);
        result.addObject("actorAuthority", Authority.USER);
        result.addObject("requestUri", "user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }


}
