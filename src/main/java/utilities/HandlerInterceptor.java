
package utilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

public class HandlerInterceptor extends org.springframework.web.servlet.i18n.LocaleChangeInterceptor {

	@Autowired
	ActorService actorService;

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
		if (modelAndView != null) {
			modelAndView.addObject("enterpriseLogo", "");
			modelAndView.addObject("nameB", "");
		}
	}

}
