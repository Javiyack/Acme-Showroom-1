
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Agent;
import forms.AgentForm;
import security.Authority;
import services.ActorService;
import services.AgentService;

@Controller
@RequestMapping("/agent")
public class AgentController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private AgentService agentService;


	// Constructors -----------------------------------------------------------

	public AgentController() {
		super();
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;

		final Collection<Agent> agents = this.agentService.findAllActive();
		result = new ModelAndView("actor/list");
		result.addObject("legend", "label.agents");
		result.addObject("actors", agents);
		result.addObject("requestUri", "agent/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

		 // Create ---------------------------------------------------------------

	    @RequestMapping("/create")
	    public ModelAndView create() {
	        ModelAndView result;
	        final AgentForm registerForm = new AgentForm();
	        result = this.createEditModelAndView(registerForm, null);
	        return result;
	    }

	    // Edit ---------------------------------------------------------------

	    @RequestMapping("/edit")
	    public ModelAndView edit() {
	        ModelAndView result;
	        AgentForm model;

	        try {
	            final Agent agent = (Agent) this.actorService.findByPrincipal();
	            model = new AgentForm(agent);
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
	public ModelAndView save(@ModelAttribute("actorForm") final AgentForm agentForm, final BindingResult binding) {
		ModelAndView result;
		Agent agent;

		try {
			agent = this.agentService.reconstruct(agentForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(agentForm);
			else
				try {
					this.actorService.save(agent);
					result = new ModelAndView("redirect:/");
				} catch (final Throwable oops) {
					if (oops.getCause().getCause() != null
							&& oops.getCause().getCause().getMessage().startsWith("Duplicate"))
						result = this.createEditModelAndView(agentForm, "msg.duplicate.agentname");
					else
						result = this.createEditModelAndView(agentForm, "msg.commit.error");
				}

		} catch (final Throwable oops) {
			if (oops.getLocalizedMessage().startsWith("msg."))
				result = this.createEditModelAndView(agentForm, oops.getLocalizedMessage());
			else if (oops.getCause().getCause() != null
					&& oops.getCause().getCause().getMessage().startsWith("Duplicate"))
				result = this.createEditModelAndView(agentForm, "msg.duplicate.agentname");
			else
				result = this.createEditModelAndView(agentForm, "agent.reconstructActor.error");
		}

		return result;
	}

	    // Auxiliary methods -----------------------------------------------------
	    protected ModelAndView createEditModelAndView(final AgentForm model) {
	        final ModelAndView result;
	        result = this.createEditModelAndView(model, null);
	        return result;
	    }

	    protected ModelAndView createEditModelAndView(final AgentForm model, final String message) {
			final ModelAndView result;
			result = new ModelAndView("actor/create");
	        result.addObject("actorForm", model);
	        result.addObject("actorAuthority", Authority.AGENT);
	        result.addObject("requestUri", "agent/create.do");
	        result.addObject("edition", true);
	        result.addObject("creation", model.getId() == 0);
	        result.addObject("message", message);

	        return result;

	    }


}
