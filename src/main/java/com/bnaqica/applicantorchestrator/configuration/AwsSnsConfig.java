package com.bnaqica.applicantorchestrator.configuration;

import com.amazonaws.services.sns.AmazonSNS;
import lombok.Getter;
import org.springframework.cloud.aws.context.config.annotation.EnableContextInstanceData;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@EnableContextInstanceData
@Profile("!default & !test & !local")
public class AwsSnsConfig {

    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
        return new NotificationMessagingTemplate(amazonSNS);
    }

}
