package com.bnaqica.applicantorchestrator.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"default", "local"})
public class AwsSnsConfigLocal {

    @Value("${spring.cloud.aws.region.static}")
    private String awsRegion;

    @Value("${spring.cloud.localstack.sns.url}")
    private String localStackSnsUrl;

    @Bean
    @Primary
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackSnsUrl, awsRegion))
                .build();
    }
}
