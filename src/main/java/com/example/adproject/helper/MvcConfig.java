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

		String os = System.getProperty("os.name");
		String path2 = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
		if (os.toLowerCase().startsWith("win")) {
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploads\\";
			registry.addResourceHandler("/upload/**").
					addResourceLocations("file:" + path);
			registry.addResourceHandler("/images/**")
					.addResourceLocations("file:"+path2);
		}else {//linux和mac系统 可以根据逻辑再做处理
			;

			registry.addResourceHandler("/upload/**").
					addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
							+ "upload"+ System.getProperty("file.separator"));

			registry.addResourceHandler("/images/**").
					addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
							+ System.getProperty("file.separator")  + System.getProperty("file.separator"));

		}


	}
}
