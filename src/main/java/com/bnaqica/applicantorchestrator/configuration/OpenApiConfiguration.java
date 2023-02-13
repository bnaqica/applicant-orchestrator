package com.bnaqica.applicantorchestrator.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Applicant Orchestrator API", version = "v1")
)
public class OpenApiConfiguration {


    @Bean
    public OpenApiCustomiser serverOpenApiCustomizer1() {
        return openAPI -> openAPI.getServers().forEach(this::changeSchema);
    }

    private Server changeSchema(Server server) {
        String url = server.getUrl().replaceFirst("http", "https");
        server.setUrl(url);
        return server;
    }
}
