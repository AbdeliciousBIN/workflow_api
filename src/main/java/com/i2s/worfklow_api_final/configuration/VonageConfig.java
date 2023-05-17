package com.i2s.worfklow_api_final.configuration;

import org.springframework.context.annotation.Configuration;
import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
@Configuration
public class VonageConfig {

    @Value("${vonage.api.key}")
    private String apiKey;

    @Value("${vonage.api.secret}")
    private String apiSecret;

    @Bean
    public VonageClient vonageClient() {
        return VonageClient.builder().apiKey(apiKey).apiSecret(apiSecret).build();
    }
}
