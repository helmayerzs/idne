package hu.idne.backend.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableScheduling
@Configuration
@ComponentScan({"hu.idne.backend.services", "hu.idne.backend.mappers"})
@Import({
        IdneAuditUserProvider.class,
        MapperConfiguration.class
})
public class ApplicationConfiguration {

    public ApplicationConfiguration() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
