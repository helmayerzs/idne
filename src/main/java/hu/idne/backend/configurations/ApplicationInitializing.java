package hu.idne.backend.configurations;

import hu.idne.backend.services.business.InitService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "hu.idne.backend.init", name = "enabled", havingValue = "true")
public class ApplicationInitializing implements ApplicationRunner {

    private final List<InitService> initServices;

    public ApplicationInitializing(ApplicationContext context) {
        this.initServices = new ArrayList<>(context.getBeansOfType(InitService.class).values());
        this.initServices.sort(AnnotationAwareOrderComparator.INSTANCE);
    }

    @Override
    public void run(ApplicationArguments args) {
        initServices.forEach(InitService::init);
    }
}
