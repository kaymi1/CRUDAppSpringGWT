package ru.fil.gwt.server.config;

import org.gwtwidgets.server.spring.GWTHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.fil.gwt.shared.rpc.PersonService;
import ru.fil.gwt.shared.rpc.SimpleRpc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "ru.fil.gwt.server.rest",
        "ru.fil.gwt.server.rpc",
        "ru.fil.gwt.server.domain",
        "ru.fil.gwt.server.dao"
})
public class WebConfig extends WebMvcConfigurerAdapter implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(BeanConfig.class);
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(rootContext);
        servletContext.addListener(contextLoaderListener);

        AnnotationConfigWebApplicationContext webServletContext = new AnnotationConfigWebApplicationContext();
        webServletContext.register(WebConfig.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(webServletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.addMapping("/Application/rpc/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html").addResourceLocations("/index.html");
        registry.addResourceHandler("/Application/**").addResourceLocations("/Application/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }

    @Bean
    public GWTHandler gwtHandler(PersonService personService){
        GWTHandler bean = new GWTHandler();
        HashMap<String, Object> mapping = new HashMap<>();
        mapping.put("/Application/rpc/people", personService);
        bean.setMappings(mapping);
        return bean;
    }
}
