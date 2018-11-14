
package controllers.User;

import controllers.AbstractController;
import domain.Actor;
import domain.CreditCard;
import domain.Request;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import java.util.Arrays;
import java.util.Collection;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {
    /* 	Manage the requests for items in their showrooms, which includes listing them and
        updating them. The status of a request may be either pending, accepted, or reject-
        ed. A pending request can be accepted or rejected; no other status changes can be
        performed.
        4. Manage his or her own requests for items, which includes listing and creating them.
    */
    // Supporting services -----------------------------------------------------

    @Autowired
    private RequestService requestService;
    @Autowired
    private ActorService actorService;



    // Constructors -----------------------------------------------------------

    public RequestUserController() {
        super();
    }

    // List created requests -------------------------------------------------------
    @RequestMapping(value = "/created/list", method = RequestMethod.GET)
    public ModelAndView createdList(final Integer pageSize) {
        ModelAndView result;
        final Collection <Request> requests = this.requestService.findCreatedRequests();

        result = new ModelAndView("request/user/list");
        result.addObject("requests", requests);
        result.addObject("requestUri", "request/user/created/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        result.addObject("legend", "created");
        return result;
    }

    // List recived requests -------------------------------------------------------
    @RequestMapping(value = "/received/list", method = RequestMethod.GET)
    public ModelAndView receivedList(final Integer pageSize) {
        ModelAndView result;
        final Collection <Request> requests = this.requestService.findRecivedRequests();

        result = new ModelAndView("request/user/list");
        result.addObject("requests", requests);
        result.addObject("requestUri", "request/user/received/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        result.addObject("legend", "received");
        return result;
    }

    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create(int itemId) {
        ModelAndView result;
        try {
            Request request = requestService.create(itemId);
            result = this.createEditModelAndView(request);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int requestId) {
        ModelAndView result;

        try {
            Request request = this.requestService.findOne(requestId);
            Assert.notNull(request, "msg.not.found.resource");
            result = this.createEditModelAndView(request);
            result.addObject("display", false);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        return result;
    }

    // Save mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Request request, BindingResult binding) {
        ModelAndView result;
        request = requestService.reconstruct(request, binding);
        if (binding.hasErrors())
            result = this.createEditModelAndView(request);
        else
            try {
                request = this.requestService.save(request);
                result = this.redirectModel(request);
            } catch (Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    result = this.createEditModelAndView(request, oops.getLocalizedMessage());
                }
                else if (oops.getCause().getCause() != null
                        && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                    result = this.createEditModelAndView(request, "msg.duplicate.request");
                else{
                    result = this.createEditModelAndView(request, "msg.commit.error");
                }
            }
        return result;
    }

    private ModelAndView redirectModel(Request request) {
        Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        if (actor.getId() == request.getUser().getId())
            return new ModelAndView("redirect:/request/user/created/list.do");
        else if (actor.getId() == request.getItem().getShowroom().getUser().getId())
            return new ModelAndView("redirect:/request/user/received/list.do");
        else
            return new ModelAndView("redirect:/");
    }

    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final Request model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Request model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("request/user/create");
        result.addObject("request", model);
        result.addObject("requestUri", "request/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        String[] states = {Request.ACCEPTED, Request.PENDING, Request.REJECTED};
        Collection <String> estados = Arrays.asList(states);
        result.addObject("states", states);
        result.addObject("estados", estados);
        String[] acceptedCreditCards = {CreditCard.AMEX, CreditCard.VISA, CreditCard.DINERS, CreditCard.MASTERCARD};
        Collection <String> validCreditCards = Arrays.asList(acceptedCreditCards);
        result.addObject("acceptedCreditCards", acceptedCreditCards);
        result.addObject("validCreditCards", validCreditCards);

        result.addObject("message", message);
        return result;
    }
}
