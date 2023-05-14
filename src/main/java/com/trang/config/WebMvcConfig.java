package com.trang.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.trang" })
public class WebMvcConfig implements WebMvcConfigurer {

	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 5 MB

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {

		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setMaxUploadSize(maxUploadSizeInMb * 2);
		cmr.setMaxUploadSizePerFile(maxUploadSizeInMb); // bytes
		return cmr;

	}

	@Bean
	public TilesConfigurer configTiles() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		registry.viewResolver(tilesViewResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("messages", "error_message");

		return source;
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource());
		return validatorFactoryBean;
	}
}
