package com.example.adproject.helper;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login"); 
		registry.addViewController("/403").setViewName("403"); 
	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		Path profilePicUploadDir = Paths.get("./user-profilePic");
//		String profilePicPath = profilePicUploadDir.toFile().getAbsolutePath();
//
//		registry.addResourceHandler("/user-profilePic/**").addResourceLocations("file:/" + profilePicPath + "/");
//	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/images/**")
				.addResourceLocations("file:images/");
	}
}
