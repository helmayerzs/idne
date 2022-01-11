package hu.idne.backend.configurations.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("hu.idne.backend.swagger")
public class SwaggerProperties {
    private Boolean enabled = true;
    private String authHost = "http://localhost:8080";
    private String apiHost = "http://localhost:8081";
}
