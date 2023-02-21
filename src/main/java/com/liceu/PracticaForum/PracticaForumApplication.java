package com.liceu.PracticaForum;

import com.liceu.PracticaForum.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PracticaForumApplication implements WebMvcConfigurer {

	@Autowired
	TokenInterceptor tokenInterceptor;
	public static void main(String[] args) {
		SpringApplication.run(PracticaForumApplication.class, args);
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/getprofile");
	}
}
