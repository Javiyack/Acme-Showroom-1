
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
import services.CommentService;
import services.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

    // Supporting services -----------------------------------------------------
    @Autowired
    private ItemService itemService;
    @Autowired
    private CommentService commentService;

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer showroomId, String word, String showroomName, final Integer pageSize) {
        ModelAndView result;
        result = new ModelAndView("item/list");
        final Collection<Item> items;
        if(showroomId!=null){
            items = this.itemService.findByKeyWordAndShowroom((word!=null)?word:"", showroomId);
            result.addObject("showroomId", showroomId);
            result.addObject("showroomName", showroomName);
        }
        else
            items = this.itemService.findByKeyWord((word!=null)?word:"");
        result.addObject("items", items);
        result.addObject("word", word);
        result.addObject("requestUri", "item/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }
    // List ------------------------------------------------------------------
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
            items = this.itemService.findByKeyWord(req.getParameter("word").trim());
        result.addObject("items", items);
        result.addObject("requestUri", "item/list.do");
        result.addObject("word", req.getParameter("word"));
        result.addObject("pageSize", (req.getParameter("pageSize") != null) ? req.getParameter("pageSize") : 5);
        return result;
    }

    public ItemController() {
        super();
    }
    // Display  -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int itemId) {
        ModelAndView result;
        try {
            final Item item = this.itemService.findOne(itemId);
            Assert.notNull(item, "msg.not.found.resource");

            result = this.createEditModelAndView(item);
            result.addObject("display", true);
            result.addObject("hasRequest", this.itemService.hasRequests(item.getId()));
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        return result;
    }



    // Auxiliary methods -----------------------------------------------------


    protected ModelAndView createEditModelAndView(final Item model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Item model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("item/edit");
        Collection<Comment> comments = this.commentService.findByCommentedObjectId(model.getId());
        result.addObject("item", model);
        result.addObject("comments", comments);
        result.addObject("message", message);

        return result;

    }

}
