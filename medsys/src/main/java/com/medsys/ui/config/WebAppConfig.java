package com.medsys.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.medsys.ui.util.MedsysUITiles;
import com.medsys.ui.util.UIActions;
 
/**
 * The Class WebAppConfig.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.medsys")
public class WebAppConfig extends WebMvcConfigurerAdapter {
     
	
    //(non-Javadoc)
    // * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
     
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(UIActions.FORWARD_SLASH + UIActions.LOGIN).setViewName(MedsysUITiles.LOGIN.getTile());
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
 
  
 
    /**
     * Setup view resolver.
     *
     * @return the url based view resolver
     */
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
    	TilesViewResolver resolver = new TilesViewResolver();
        //resolver.setPrefix("/WEB-INF/medsys/admin/");
        
        resolver.setSuffix(".tiles");
        resolver.setViewClass(TilesView.class);
        return resolver;
    }
    
    /**
     * Tiles configurer.
     *
     * @return the tiles configurer
     */
    @Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		 tilesConfigurer.setDefinitions(new String[] { "classpath:admintiles.xml" });
	        tilesConfigurer.setCheckRefresh(true);
	        return tilesConfigurer;		
	}
	
    // Maps resources path to webapp/resources
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
        .addResourceLocations("/resources/")
        .addResourceLocations("/")
        .addResourceLocations("classpath:/WEB-INF/resources")
        .addResourceLocations("classpath:");
    }
    
    // Provides internationalization of messages
    /**
     * Message source.
     *
     * @return the resource bundle message source
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(new String[]{"menu-titles",
		"labels",
		"ui-messages",
		"email-messages",
		"mobile-messages",
		"system-error-messages",
		"ValidationMessages"});
        return source;
    }
    
    /**
     * Multipart resolver.
     *
     * @return the commons multipart resolver
     */
    @Bean
    public CommonsMultipartResolver multipartResolver(){
    	return new CommonsMultipartResolver();
    }
    
   /* @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
    	SimpleMappingExceptionResolver expResolver = new SimpleMappingExceptionResolver(); 
    	Properties exceptionMappings = new Properties();
    	exceptionMappings.setProperty("Exception","common.error.tiles");
    	expResolver.setExceptionMappings(new Properties());
    	expResolver.setWarnLogCategory("WARN");
    	expResolver.setDefaultErrorView("common.error.tiles");
    	return expResolver;
    }
    */
    
    
 
}