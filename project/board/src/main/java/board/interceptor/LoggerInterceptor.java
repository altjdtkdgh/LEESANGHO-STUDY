package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ch.qos.logback.classic.Logger;

public class LoggerInterceptor  extends HandlerInterceptorAdapter{
	
	private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		log.debug("========================== START ======================");
		log.debug(" Request URI \t: " + request.getRequestURI());
		return super.preHandle(request, response, handler);
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		log.debug("==================================END =============================");
	}
}
