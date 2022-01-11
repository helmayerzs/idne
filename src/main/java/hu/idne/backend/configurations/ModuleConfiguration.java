package hu.idne.backend.configurations;

import hu.idne.backend.repositories.system.impl.BaseRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EntityScan({"hu.idne.backend.models"})
@ComponentScan({"hu.idne.backend.services"})
@EnableJpaRepositories(basePackages = "hu.idne.backend.repositories", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class ModuleConfiguration {
    
}
