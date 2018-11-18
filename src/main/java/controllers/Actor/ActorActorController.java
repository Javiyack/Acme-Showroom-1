
package controllers.Actor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Chirp;
import domain.Showroom;
import security.Authority;
import services.ActorService;
import services.ChirpService;
import services.ShowroomService;

@Controller
@RequestMapping("/actor/actor")
public class ActorActorController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ActorService actorService;
    @Autowired
    private ShowroomService showroomService;
    @Autowired
    private ChirpService chirpService;

    // Constructors -----------------------------------------------------------

    public ActorActorController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;

        final Collection<Actor> users = this.actorService.findAll();
        final Collection<Actor> actorSubscriptions = this.actorService.findFollows();
        final Collection<Actor> followers = this.actorService.findFollowers();
        Map<Actor,Boolean> userIsFollowedMap = new HashMap<>();
        for (Actor user:users) {
            userIsFollowedMap.put(user, actorSubscriptions.contains(user));
        }
        result = new ModelAndView("actor/list");
        result.addObject("legend", "label.actors");
        result.addObject("actors", users);
        result.addObject("followers", followers);
        result.addObject("userIsFollowedMap", userIsFollowedMap);
        result.addObject("requestUri", "actor/actor/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display")
    public ModelAndView display(@RequestParam final int actorId, final Integer pageSize, final Integer word) {
        ModelAndView result;

        final Actor actor;
        actor = this.actorService.findOneIfActive(actorId);
        String authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
        result = new ModelAndView("actor/display");
        result.addObject("actorForm", actor);
        result.addObject("actorAuthority", authority);
        result.addObject("display", true);
        Boolean subscribedToActor =  this.actorService.checkIfSubscribedToActor(actor);
        result.addObject("subscribedToActor", subscribedToActor);
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        Collection <Chirp> chirps = chirpService.findByActorId(actorId);
        result.addObject("chirps", chirps);
        if(authority.equals(Authority.USER)){
            Collection <Showroom> showrooms = showroomService.findByUserId(actorId);
            result.addObject("showrooms", showrooms);
        }

        return result;
    }



}
