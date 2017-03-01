package com.giggs.component;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.giggs.repository.LogRepository;

@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	@Qualifier("logRepository")
	private LogRepository logRepository;

	private static final Log LOGGER = LogFactory.getLog(RequestTimeInterceptor.class);

	// Antes de ejecutar el metodo solicitado en la peticion
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("startTimeRequest", System.currentTimeMillis());
		return true;
	}

	// Despues de renderizar la vista
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String url = request.getRequestURL().toString();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = "";
		String details = "";
		if(auth != null && auth.isAuthenticated()) {
			username = auth.getName();
			details = auth.getDetails().toString();
		}
		
		com.giggs.entity.Log log = new com.giggs.entity.Log(new Date(), details, username, url);
		logRepository.save(log);
		
		long startTimeRequest = (long) request.getAttribute("startTimeRequest");
		LOGGER.info("URL to: '" + url + "' in '" + (System.currentTimeMillis() - startTimeRequest)
				+ " ms'");
	}

}
