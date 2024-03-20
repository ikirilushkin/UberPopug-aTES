package com.kirilushkin.aa6.audit.config;

import com.kirilushkin.SchemaValidator;
import com.kirilushkin.SchemaValidatorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public SchemaValidator schemaValidator() {
        return SchemaValidator.getInstance(new SchemaValidatorConfig("", "schemas/"));
    }
}
