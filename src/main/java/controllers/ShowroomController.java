
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;
import domain.Item;
import domain.Showroom;
import services.CommentService;
import services.ItemService;
import services.ShowroomService;

@Controller
@RequestMapping("/showroom")
public class ShowroomController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ShowroomService showroomService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private CommentService commentService;
// Constructors -----------------------------------------------------------

    public ShowroomController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize, String word) {
        ModelAndView result;
        final Collection<Showroom> showrooms;
        showrooms = this.showroomService.findByKeyWord((word!=null)?word:"");
        result = new ModelAndView("showroom/list");
        result.addObject("showrooms", showrooms);
        result.addObject("requestUri", "showroom/list.do");
        result.addObject("word", word);
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }
    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ModelAndView list(HttpServletRequest req) {
        ModelAndView result;
        final Collection<Showroom> showrooms;
        showrooms = this.showroomService.findByKeyWord(req.getParameter("word").trim());
        result = new ModelAndView("showroom/list");
        result.addObject("showrooms", showrooms);
        result.addObject("requestUri", "showroom/list.do");
        result.addObject("word", req.getParameter("word"));
        result.addObject("pageSize", (req.getParameter("pageSize") != null) ? req.getParameter("pageSize") : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int showroomId) {
        ModelAndView result;
        try {
            final Showroom showroom = this.showroomService.findOne(showroomId);
            Assert.notNull(showroom, "msg.not.found.resource");
            result = this.createDisplaytModelAndView(showroom);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        return result;
    }


    protected ModelAndView createDisplaytModelAndView(final Showroom model) {
        final ModelAndView result;
        result = this.createDisplaytModelAndView(model, null);
        return result;
    }

    protected ModelAndView createDisplaytModelAndView(final Showroom model, final String message) {
        final ModelAndView result;
        Collection<Item> items =  itemService.findByShowroom(model);
        Collection<Comment> comments = this.commentService.findByCommentedObjectId(model.getId());
        result = new ModelAndView("showroom/display");
        result.addObject("showroom", model);
        result.addObject("items", items);
        result.addObject("comments", comments);
        result.addObject("edition", false);
        result.addObject("creation", false);
        result.addObject("message", message);
        result.addObject("display", true);
        return result;

    }


}
