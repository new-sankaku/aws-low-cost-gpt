package com.yksc.dummy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yksc.dummy.web.interceptor.MyInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private MyInterceptor myInterceptor;

	@Override
	public void addInterceptors( InterceptorRegistry registry ) {
		registry.addInterceptor( myInterceptor );
	}

	@Override
	public void addCorsMappings( CorsRegistry registry ) {
		registry.addMapping( "/**" )
				.allowedOrigins( "http://localhost:8081" )
				.allowedMethods( "GET", "POST", "PUT", "DELETE", "OPTIONS" )
				.allowedHeaders( "*" )
				.allowCredentials( true );
	}

}
