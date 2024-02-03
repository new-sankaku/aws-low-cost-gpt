package com.yksc.dummy.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MyInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
			throws Exception {
		String requestUrl = request.getRequestURL().toString();
		String referer = request.getHeader( "Referer" );

		if( StringUtils.contains( referer, "localhost" ) ) {
			//nolog
		} else {
			System.out.println( "afterCompletion: Request URL: " + requestUrl + ", Referer: " + referer );

			String authorizationHeader = request.getHeader( "Authorization" );

			if( authorizationHeader != null && authorizationHeader.startsWith( "Bearer " ) ) {
				int index = "Bearer".length() + 1;
				String token = authorizationHeader.substring( index );

				//TODO AWS token
				boolean isValidToken = true;
				//			boolean isValidToken = validateToken( token );

				if( !isValidToken ) {
					response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token" );
					return false;
				}
			} else {
				System.out.println( "token is nothing!!!" );
			}
		}

		return true;
	}

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView ) throws Exception {
		String requestUrl = request.getRequestURL().toString();
		String referer = request.getHeader( "Referer" );

		if( StringUtils.contains( referer, "localhost" ) ) {
			return;
		}

		System.out.println( "postHandle: Request URL: " + requestUrl + ", Referer: " + referer );
	}

	@Override
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex ) throws Exception {
		String requestUrl = request.getRequestURL().toString();
		String referer = request.getHeader( "Referer" );

		if( StringUtils.contains( referer, "localhost" ) ) {
			return;
		}

		System.out.println( "afterCompletion: Request URL: " + requestUrl + ", Referer: " + referer );
	}

}
