package com.bnaqica.applicantorchestrator.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@Profile({"default", "local"})
@RequiredArgsConstructor
public class AwsS3ConfigLocal {

    @Value("${spring.cloud.localstack.s3.region}")
    private String awsRegion;

    @Value("${spring.cloud.localstack.s3.accesskey}")
    private String accessKey;

    @Value("${spring.cloud.localstack.s3.secretkey}")
    private String secretKey;

    @Value("${spring.cloud.localstack.s3.serviceEndpoint}")
    private String serviceEndpoint;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, awsRegion))
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
