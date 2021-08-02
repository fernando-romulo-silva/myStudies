package com.apress.prospring5.ch16;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.apress.prospring5.ch16.util.DateFormatter;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/index").setViewName("index");
	registry.addViewController("/").setViewName("index");
	registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
	formatterRegistry.addFormatter(dateFormatter());
    }

    @Bean
    public DateFormatter dateFormatter() {
	return new DateFormatter();
    }
}
