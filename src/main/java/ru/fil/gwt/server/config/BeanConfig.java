package ru.fil.gwt.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "ru.fil.gwt.server.config",
        "ru.fil.gwt.server.service"
})
public class BeanConfig {
}
