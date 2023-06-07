package com.example.bikego.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerUIConfiguration {
    @Bean
    public OpenAPI myOpenAPI() {

        Server localServer = new Server();
        localServer.setUrl("http://localhost:5000/bikego");
        localServer.setDescription("Server URL in Local environment");

        Server productionServer = new Server();
        productionServer.setUrl("https://bikego-server-production.up.railway.app/bikego");
        productionServer.setDescription("Server URL in Production environment");

        Info info = new Info()
                .title("BIKEGO MANAGER API")
                .version("1.0")
                .description("FOR EDUCATION PURPOSE ONLY");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}
