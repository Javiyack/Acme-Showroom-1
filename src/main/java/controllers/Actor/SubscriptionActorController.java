
package controllers.Actor;

import controllers.AbstractController;
import domain.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.ChirpService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
@RequestMapping("/subscription/actor")
public class SubscriptionActorController extends AbstractController {

	// Supporting services ----------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private ChirpService chirpService;

	// Constructors -----------------------------------------------------------

	public SubscriptionActorController() {
		super();
	}

	// List Subscribers -------------------------------------------------------
	@RequestMapping(value = "/subscribers/list", method = RequestMethod.GET)
	public ModelAndView followeds(final Integer pageSize) {
		ModelAndView result;
		final Collection<Actor> followers = this.actorService.findFollowers();

		result = new ModelAndView("actor/list");
		result.addObject("followers", followers);
		result.addObject("requestUri", "subscription/subscribers/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}
	// List Subcription --------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView followers(final Integer pageSize) {
		ModelAndView result;
		final Collection<Actor> subscribedActors = actorService.findFollows();
		final Collection<String> topicSubscriptions = this.actorService.findTopics();
		result = new ModelAndView("subscription/actor/list");
		Collection <String> topics = chirpService.findAllTopics();
		result.addObject("topics", topics);
		result.addObject("subscribedActors", subscribedActors);
		result.addObject("topicSubscriptions", topicSubscriptions);
		result.addObject("requestUri", "subscription/actor/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Subsribe to an actor -----------------------------------------------------------
	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(@RequestParam final int actorId, String redirectUrl) {
		ModelAndView result;
		try{
			this.actorService.follow(actorId);
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), redirectUrl);
			} else {
				return this.createMessageModelAndView("msg.commit.error", "/");
			}
		}
		result = new ModelAndView("redirect:" + redirectUrl);
		return result;
	}

	// Subsribe to a topic -----------------------------------------------------------
	@RequestMapping(value = "/topic/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(@RequestParam final String topic) {
		ModelAndView result;
		try{
			this.actorService.subscribe(topic);
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), "/subscription/actor/list.do");
			} else {
				return this.createMessageModelAndView("msg.commit.error", "/");
			}
		}
		result = new ModelAndView("redirect:/subscription/actor/list.do");
		return result;
	}
	// Subsribe to a topic -----------------------------------------------------------
	@RequestMapping(value = "/topic/subscribe", method = RequestMethod.POST)
	public ModelAndView topicSubscribe(HttpServletRequest req) {
		ModelAndView result;
		String redirectUrl;
		if(req.getParameter("redirectUrl")!=null)
			redirectUrl = req.getParameter("redirectUrl");
		else
			redirectUrl = "/subscription/actor/list.do";

		try{
			this.actorService.subscribe(req.getParameter("topic"));
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), redirectUrl);
			} else {
				return this.createMessageModelAndView("msg.commit.error", "/");
			}
		}
		result = new ModelAndView("redirect:" + redirectUrl);
		return result;
	}
	// UnSubsribe  -----------------------------------------------------------
	@RequestMapping(value = "/topic/unsubscribe", method = RequestMethod.GET)
	public ModelAndView topicUnsubscribe(@RequestParam final String topic) {
		ModelAndView result;
		try{
			this.actorService.unSubscribe(topic);
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), "/actor/actor/list.do");
			} else {
				return this.createMessageModelAndView("msg.commit.error", "/");
			}
		}
		result = new ModelAndView("redirect:/subscription/actor/list.do");
		return result;
	}
	// UnSubsribe  -----------------------------------------------------------
	@RequestMapping(value = "/topic/unsubscribe", method = RequestMethod.POST)
	public ModelAndView unsubscribe(HttpServletRequest req) {
		ModelAndView result;
		String redirectUrl;
		if(req.getParameter("redirectUrl")!=null)
			redirectUrl = req.getParameter("redirectUrl");
		else
			redirectUrl = "/subscription/actor/list.do";
		try{
			this.actorService.unSubscribe(req.getParameter("topic"));
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), redirectUrl);
			} else {
				return this.createMessageModelAndView("msg.commit.error", "/");
			}
		}
		result = new ModelAndView("redirect:" + redirectUrl);
		return result;
	}



}
