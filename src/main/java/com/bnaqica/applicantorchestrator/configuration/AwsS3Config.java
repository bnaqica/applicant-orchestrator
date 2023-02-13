package com.bnaqica.applicantorchestrator.configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@Profile("!default & !test & !local")
@Slf4j
@RequiredArgsConstructor
public class AwsS3Config {

    @Bean
    public AmazonS3 amazonS3() {

        var credentials = DefaultAWSCredentialsProviderChain.getInstance();
        log.info("aws_secret_key: {}", credentials.getCredentials().getAWSSecretKey());
        log.info("aws_access_key: {}", credentials.getCredentials().getAWSAccessKeyId());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentials)
                .build();
    }
}
