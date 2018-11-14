
package controllers.User;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Comment;
import domain.Item;
import domain.Showroom;
import services.ActorService;
import services.ChirpService;
import services.CommentService;
import services.ItemService;
import services.ShowroomService;

@Controller
@RequestMapping("/showroom/user")
public class ShowroomUserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ShowroomService showroomService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private ChirpService chirpService;
    @Autowired
    private CommentService commentService;
    // Constructors -----------------------------------------------------------

    public ShowroomUserController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer pageSize, String word) {
        ModelAndView result;
        Collection<Showroom> showrooms;
        result = new ModelAndView("showroom/list");
        try {
            showrooms = this.showroomService.findByLogedActor();
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        result.addObject("showrooms", showrooms);
        result.addObject("userList", true);
        result.addObject("word", word);result.addObject("requestUri", "showroom/user/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }



    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        Showroom showroom = showroomService.create();
        result = this.createEditModelAndView(showroom);
        result.setViewName("showroom/create");
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int showroomId, Integer pageSize, String word) {
        ModelAndView result;

        try {
            Showroom showroom = this.showroomService.findOne(showroomId);
            Assert.notNull(showroom, "msg.not.found.resource");
            Assert.notNull(showroom.getUser().equals(actorService.findByPrincipal()), "msg.not.owned.block");
            result = this.createEditModelAndView(showroom);
            result.addObject("display", false);
            result.addObject("pageSize", (pageSize != null) ? pageSize : 5);

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
    public ModelAndView save(@Valid Showroom showroom, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(showroom);
        else
            try {
                Boolean crearChirpAutomatico = showroom.getId()==0;
                showroom = this.showroomService.save(showroom);
                if(crearChirpAutomatico)
                    this.chirpService.createAutomaticChrip("Showroom", "Nuevo Escaparate","Se ha creado el nuevo escaparate " + showroom.getName());
                result = this.createEditModelAndView(showroom);
                result.addObject("info", "msg.commit.ok");
            } catch ( Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/list.do");
                } else {
                    return this.createMessageModelAndView("msg.commit.error", "/");
                }
            }
        return result;
    }
// Delete mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@Valid Showroom showroom) {
        ModelAndView result = erase(showroom.getId());
        return result;
    }

    //  Delete mediante GET  -----------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView remove(@RequestParam int showroomId) {
        ModelAndView result = erase(showroomId);
        return result;
    }

    // Auxiliary methods -----------------------------------------------------

    protected ModelAndView erase(Integer showroomId){
        ModelAndView result;
        try {
            this.showroomService.delete(showroomId);
            result = new ModelAndView("redirect:/showroom/user/list.do");
        } catch ( Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/list.do");
            } else {
                return this.createMessageModelAndView("msg.commit.error", "/");
            }
        }
        return result;
    }

    protected ModelAndView createEditModelAndView(Showroom model) {
         ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(Showroom model,  String message) {
        ModelAndView result;
        Collection<Item> items =  itemService.findByShowroom(model);

        result = new ModelAndView("showroom/edit");
        result.addObject("showroom", model);
        Collection<Comment> comments = this.commentService.findByCommentedObjectId(model.getId());
        result.addObject("items", items);
        result.addObject("comments", comments);
        result.addObject("requestUri", "showroom/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
