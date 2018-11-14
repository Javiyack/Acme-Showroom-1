
package controllers.User;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
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
import services.ActorService;
import services.ChirpService;
import services.CommentService;
import services.ItemService;
import services.ShowroomService;

@Controller
@RequestMapping("/item/user")
public class ItemUserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ItemService itemService;
    @Autowired
    private ShowroomService showroomService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private ChirpService chirpService;
    @Autowired
    private CommentService commentService;
    // Constructors -----------------------------------------------------------

    public ItemUserController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer showroomId, String word, Integer pageSize) {
        ModelAndView result;
        Collection<Item> items;
        result = new ModelAndView("item/list");
        try {
            if(showroomId!=null){
                items = this.itemService.findByShowroomId(showroomId);
            }else{
                items = this.itemService.findByLogedActor();
            }
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/edit.do?showroomId=" + showroomId);
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        result.addObject("items", items);
        result.addObject("userList", true);
        result.addObject("word", word);
        result.addObject("requestUri", "item/user/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }
    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(HttpServletRequest req) {
        ModelAndView result;
        final Collection<Item> items;
        result = new ModelAndView("item/list");
        if(!req.getParameter("showroomId").isEmpty()){
            items = this.itemService.findByKeyWordAndShowroom(req.getParameter("word").trim(), Integer.valueOf(req.getParameter("showroomId")));
            result.addObject("showroomId", req.getParameter("showroomId"));
            result.addObject("showroomName", req.getParameter("showroomName"));
        }
        else
            items = this.itemService.findByKeyWordAndLogedActor(req.getParameter("word").trim());
        result.addObject("items", items);
        result.addObject("requestUri", "item/list.do");
        result.addObject("word", req.getParameter("word"));
        result.addObject("pageSize", (req.getParameter("pageSize") != null) ? req.getParameter("pageSize") : 5);
        result.addObject("items", items);
        result.addObject("userList", true);
        result.addObject("requestUri", "item/user/list.do");
        return result;
    }


    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create(int showroomId) {
        ModelAndView result;
        Item item = itemService.create(showroomService.findOne(showroomId));
        result = new ModelAndView("item/edit");
        result.addObject("item", item);
        result.addObject("requestUri", "item/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", true);
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int itemId) {
        ModelAndView result;

        try {
             Item item = this.itemService.findOne(itemId);
            Assert.notNull(item, "msg.not.found.resource");
            Assert.notNull(item.getShowroom().getUser().equals(actorService.findByPrincipal()), "msg.not.owned.block");
            result = this.createEditModelAndView(item);
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
    public ModelAndView save(@Valid Item item, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(item);
        else
            try {
                Boolean crearChirpAutomatico = item.getId()==0;
                item = this.itemService.save(item);
                if(crearChirpAutomatico)
                    this.chirpService.createAutomaticChrip("Artículos", "Nuevo Artículo","Se ha añadido el nuevo artículo "
                        + item.getTitle() + " al escaparate " + item.getShowroom().getName());
                result = new ModelAndView("redirect:/showroom/user/edit.do?showroomId=" + item.getShowroom().getId());
            } catch ( Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/edit.do?showroomId=" + item.getShowroom().getId());
                } else {
                    return this.createMessageModelAndView("msg.commit.error", "/showroom/user/edit.do?showroomId=" + item.getShowroom().getId());
                }
            }
        return result;
    }
// Delete mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@Valid  Item item) {
        ModelAndView result = erase(item.getId(), item.getShowroom().getId());
        return result;
    }

    //  Delete mediante GET  -----------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView remove(@RequestParam  int itemId,  int showroomId) {
        ModelAndView result = erase(itemId, showroomId);
        return result;
    }

    // Auxiliary methods -----------------------------------------------------

    protected ModelAndView erase( Integer itemId,  Integer showroomId) {
        ModelAndView result;
        try {
            this.itemService.delete(itemId);
            result = new ModelAndView("redirect:/showroom/user/edit.do?showroomId=" + showroomId);
        } catch ( Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/edit.do?showroomId=" + showroomId);
            } else {
                return this.createMessageModelAndView("msg.commit.error", "/showroom/user/edit.do?showroomId=" + showroomId);
            }
        }
        return result;
    }


    protected ModelAndView createEditModelAndView( Item model) {
         ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView( Item model,  String message) {
         ModelAndView result;

        result = new ModelAndView("item/edit");
        Collection<Comment> comments = this.commentService.findByCommentedObjectId(model.getId());
        result.addObject("item", model);
        result.addObject("hasRequest", this.itemService.hasRequests(model.getId()));
        result.addObject("comments", comments);
        result.addObject("requestUri", "item/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
