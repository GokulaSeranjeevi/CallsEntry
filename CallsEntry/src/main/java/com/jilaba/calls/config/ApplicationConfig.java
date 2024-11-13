package com.jilaba.calls.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ConnectionConfig.class })
@ComponentScan("com.jilaba.*")
public class ApplicationConfig {

}
