package hu.idne.backend.configurations;

import com.fasterxml.classmate.TypeResolver;
import hu.idne.backend.configurations.properties.SwaggerProperties;
import hu.idne.backend.services.system.impl.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "hu.idne.backend.development", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    private final TypeResolver resolver;
    private final ModelMapperService modelMapperService;
    private final SwaggerProperties properties;

    @Autowired
    public SwaggerConfig(ModelMapperService modelMapperService, SwaggerProperties properties, TypeResolver resolver) {
        this.modelMapperService = modelMapperService;
        this.properties = properties;
        this.resolver = resolver;
    }

    @Bean
    public ApiInfo apiInfo() {
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.contact(new Contact("Helmayer Zsolt", "https://www.lehetetlennemletezik.hu/", "info@lehetetlennemletezik.hu"));
        builder.title("Idne Api");
        builder.description("Idne Api description");
        builder.version("1.0.0-Release");
        return builder.build();
    }

    @Bean
    public AlternateTypeRuleConvention alternateTypeRuleConvention() {
        return new AlternateTypeRuleConvention() {

            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return modelMapperService.getMapperKeys()
                        .stream()
                        .map(key -> newRule(resolver.resolve(key.getEntityType()), resolver.resolve(key.getDtoType()), Ordered.HIGHEST_PRECEDENCE))
                        .collect(Collectors.toList());
            }
        };
    }

    @Bean
    public Docket api() {
        List<SecurityScheme> schemes = new ArrayList<>();

        schemes.add(client());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .host(properties.getApiHost())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build()
                .securitySchemes(schemes)
                .securityContexts(Collections.singletonList(securityContext()))
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .directModelSubstitute(LocalTime.class, String.class);
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .scopeSeparator("+")
                .build();
    }

    private SecurityScheme client() {

        GrantType client = new ClientCredentialsGrant(String.format("%s/auth/realms/idne/protocol/openid-connect/token", properties.getAuthHost()));

        return new OAuthBuilder().name("keycloak_auth_client")
                .grantTypes(Collections.singletonList(client))
                .build();
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> result = new ArrayList<>();
        result.add(new AuthorizationScope("roles", "The default oauth2 scope, normally not used"));
        return result;
    }

    private AuthorizationScope[] scopesArray() {
        return scopes().toArray(new AuthorizationScope[0]);
    }

    private SecurityContext securityContext() {

        List<SecurityReference> references = new ArrayList<>();

        references.add(new SecurityReference("keycloak_auth_client", scopesArray()));

        return SecurityContext.builder()
                .securityReferences(references)
                .forPaths(PathSelectors.ant("/**"))
                .build();
    }
}

