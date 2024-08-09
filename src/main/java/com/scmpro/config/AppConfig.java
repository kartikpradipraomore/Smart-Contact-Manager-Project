package com.scmpro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {

    @Value("${Cloudnary.cloud.name}")
    private String cloudName;

    @Value("${Cloudnary.api.key}")
    private String apiKey;

    @Value("${Cloudnary.api.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret

                ));
    }

}
