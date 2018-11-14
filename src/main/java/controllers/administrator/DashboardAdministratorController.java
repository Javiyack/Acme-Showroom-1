
package controllers.administrator;

import controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {


    @Autowired
    AdministratorService administratorService;


    // Constructors -----------------------------------------------------------

    public DashboardAdministratorController() {
        super();
    }

    // ---------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView result = new ModelAndView("dashboard/display");

        Map <String, Map <String, Map <String, Object>>> dashboard = new TreeMap <>();
        /*  DASHBOARD C */
        Map <String, Map <String, Object>> levelC = new TreeMap <>();
		/*	1. The average, the minimum, the maximum, and the standard deviation of the
			number of showrooms per user. */
        Map <String, Object> showroomsPerUser = new HashMap <>();
        showroomsPerUser.put("avg", administratorService.findAverageShowroomsPerUser() != null ? administratorService.findAverageShowroomsPerUser() : 0);
        showroomsPerUser.put("min", administratorService.findMinimunShowroomsPerUser() != null ? administratorService.findMinimunShowroomsPerUser() : 0);
        showroomsPerUser.put("max", administratorService.findMaximunShowroomsPerUser() != null ? administratorService.findMaximunShowroomsPerUser() : 0);
        showroomsPerUser.put("stdev", administratorService.findStdevShowroomsPerUser() != null ? administratorService.findStdevShowroomsPerUser() : 0);
        levelC.put("showroomsPerUser", showroomsPerUser);
		/*	2. The average, the minimum, the maximum, and the standard deviation of the
			number of items per showroom.		 */
        Map <String, Object> itemsPerUser = new HashMap <>();
        itemsPerUser.put("avg", administratorService.findAverageItemsPerUser() != null ? administratorService.findAverageItemsPerUser() : 0);
        itemsPerUser.put("min", administratorService.findMinimunItemsPerUser() != null ? administratorService.findMinimunItemsPerUser() : 0);
        itemsPerUser.put("max", administratorService.findMaximunItemsPerUser() != null ? administratorService.findMaximunItemsPerUser() : 0);
        itemsPerUser.put("stdev", administratorService.findStdevItemsPerUser() != null ? administratorService.findStdevItemsPerUser() : 0);
        levelC.put("itemsPerUser", itemsPerUser);
		/*	3. The average, the minimum, the maximum, and the standard deviation of the
			number of requests per showroom.		 */
        Map <String, Object> requestsPerUser = new HashMap <>();
        requestsPerUser.put("avg", administratorService.findAverageRequestsPerUser() != null ? administratorService.findAverageRequestsPerUser() : 0);
        requestsPerUser.put("min", administratorService.findMinimunRequestsPerUser() != null ? administratorService.findMinimunRequestsPerUser() : 0);
        requestsPerUser.put("max", administratorService.findMaximunRequestsPerUser() != null ? administratorService.findMaximunRequestsPerUser() : 0);
        requestsPerUser.put("stdev", administratorService.findStdevRequestsPerUser() != null ? administratorService.findStdevRequestsPerUser() : 0);
        levelC.put("requestsPerUser", requestsPerUser);
		/*	4. The average, the minimum, the maximum, and the standard deviation of the
			number of rejectedRequests per showroom.		 */
        Map <String, Object> rejectedRequestsPerUser = new HashMap <>();
        rejectedRequestsPerUser.put("avg", administratorService.findAverageRejectedRequestsPerUser() != null ? administratorService.findAverageRejectedRequestsPerUser() : 0);
        rejectedRequestsPerUser.put("min", administratorService.findMinimunRejectedRequestsPerUser() != null ? administratorService.findMinimunRejectedRequestsPerUser() : 0);
        rejectedRequestsPerUser.put("max", administratorService.findMaximunRejectedRequestsPerUser() != null ? administratorService.findMaximunRejectedRequestsPerUser() : 0);
        rejectedRequestsPerUser.put("stdev", administratorService.findStdevRejectedRequestsPerUser() != null ? administratorService.findStdevRejectedRequestsPerUser() : 0);
        levelC.put("rejectedRequestsPerUser", rejectedRequestsPerUser);
        dashboard.put("levelC", levelC);
        /* 	DASHBOARD B */
        Map <String, Map <String, Object>> levelB = new TreeMap <>();
        /*	1. The average, the minimum, the maximum, and the standard deviation of the
			number of chirp per showroom.		 */
        Map <String, Object> chirpsPerUser = new HashMap <>();
        chirpsPerUser.put("avg", administratorService.findAverageChirpsPerUser() != null ? administratorService.findAverageChirpsPerUser() : 0);
        chirpsPerUser.put("min", administratorService.findMinimunChirpsPerUser() != null ? administratorService.findMinimunChirpsPerUser() : 0);
        chirpsPerUser.put("max", administratorService.findMaximunChirpsPerUser() != null ? administratorService.findMaximunChirpsPerUser() : 0);
        chirpsPerUser.put("stdev", administratorService.findStdevChirpsPerUser() != null ? administratorService.findStdevChirpsPerUser() : 0);
        levelB.put("chirpsPerUser", chirpsPerUser);
		/*	2. 	The minimum, the maximum, the average, and the standard deviation of the
			number of users to whom users subscribe.*/
        Map <String, Object> followedsPerUser = new HashMap <>();
        followedsPerUser.put("avg", administratorService.findAverageFollowedsPerUser() != null ? administratorService.findAverageFollowedsPerUser() : 0);
        followedsPerUser.put("min", administratorService.findMinimunFollowedsPerUser() != null ? administratorService.findMinimunFollowedsPerUser() : 0);
        followedsPerUser.put("max", administratorService.findMaximunFollowedsPerUser() != null ? administratorService.findMaximunFollowedsPerUser() : 0);
        followedsPerUser.put("stdev", administratorService.findStdevFollowedsPerUser() != null ? administratorService.findStdevFollowedsPerUser() : 0);
        levelB.put("followedsPerUser", followedsPerUser);
		/*	3. 	The minimum, the maximum, the average, and the standard deviation of the
			number of subscriptors per user.*/
        Map <String, Object> followersPerUser = new HashMap <>();
        followersPerUser.put("avg", administratorService.findAverageFollowersPerUser() != null ? administratorService.findAverageFollowersPerUser() : 0);
        followersPerUser.put("min", administratorService.findMinimunFollowersPerUser() != null ? administratorService.findMinimunFollowersPerUser() : 0);
        followersPerUser.put("max", administratorService.findMaximunFollowersPerUser() != null ? administratorService.findMaximunFollowersPerUser() : 0);
        followersPerUser.put("stdev", administratorService.findStdevFollowersPerUser() != null ? administratorService.findStdevFollowersPerUser() : 0);
        levelB.put("followersPerUser", followersPerUser);
        /*	4.	A chart with the number of chirps published grouped by topic.  */
        Map <String, Object> chirpsPerTopic = new HashMap <>();
        Collection <Object> chirpsPT;
        chirpsPT = administratorService.findChirpsNumberPerTopic();// != null ? administratorService.findAverageChirpsPerTopic() : 0;
        if (!chirpsPT.isEmpty()) {
            for (Object object : chirpsPT) {
                final Object[] line = (Object[]) object;
                chirpsPerTopic.put((String) line[0], line[1]);
            }
        }
        levelB.put("chirpsPerTopic", chirpsPerTopic);
        dashboard.put("levelB", levelB);
        /* DASHBOARD A */
        Map <String, Map <String, Object>> levelA = new TreeMap <>();
		/*	1.	The average, the minimum, the maximum, and the standard deviation of the
			number of comments per showroom.*/
        Map <String, Object> commentsPerShowroom = new HashMap <>();
        commentsPerShowroom.put("avg", administratorService.findAverageCommentsPerShowroom() != null ? administratorService.findAverageCommentsPerShowroom() : 0);
        commentsPerShowroom.put("min", administratorService.findMinimunCommentsPerShowroom() != null ? administratorService.findMinimunCommentsPerShowroom() : 0);
        commentsPerShowroom.put("max", administratorService.findMaximunCommentsPerShowroom() != null ? administratorService.findMaximunCommentsPerShowroom() : 0);
        commentsPerShowroom.put("stdev", administratorService.findStdevCommentsPerShowroom() != null ? administratorService.findStdevCommentsPerShowroom() : 0);
        levelA.put("commentsPerShowroom", commentsPerShowroom);
		/*	2. 	The average, the minimum, the maximum, and the standard deviation of the
		number of comments per item.*/
        Map <String, Object> commentsPerItem = new HashMap <>();
        commentsPerItem.put("avg", administratorService.findAverageCommentsPerItem() != null ? administratorService.findAverageCommentsPerItem() : 0);
        commentsPerItem.put("min", administratorService.findMinimunCommentsPerItem() != null ? administratorService.findMinimunCommentsPerItem() : 0);
        commentsPerItem.put("max", administratorService.findMaximunCommentsPerItem() != null ? administratorService.findMaximunCommentsPerItem() : 0);
        commentsPerItem.put("stdev", administratorService.findStdevCommentsPerItem() != null ? administratorService.findStdevCommentsPerItem() : 0);
        levelA.put("commentsPerItem", commentsPerItem);
	 	/*	3. 	The average, the minimum, the maximum, and the standard deviation of the
		number of comments per user */
       Map <String, Object> commentsPerUser = new HashMap <>();
        commentsPerUser.put("avg", administratorService.findAverageCommentsPerUser() != null ? administratorService.findAverageCommentsPerUser() : 0);
        commentsPerUser.put("min", administratorService.findMinimunCommentsPerUser() != null ? administratorService.findMinimunCommentsPerUser() : 0);
        commentsPerUser.put("max", administratorService.findMaximunCommentsPerUser() != null ? administratorService.findMaximunCommentsPerUser() : 0);
        commentsPerUser.put("stdev", administratorService.findStdevCommentsPerUser() != null ? administratorService.findStdevCommentsPerUser() : 0);
        levelA.put("commentsPerUser", commentsPerUser);
     dashboard.put("levelA", levelA);


        result.addObject("dashboard", dashboard);
        return result;
    }

}
