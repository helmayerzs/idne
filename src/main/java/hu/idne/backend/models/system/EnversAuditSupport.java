package hu.idne.backend.models.system;

import hu.idne.backend.exceptions.OwnDefaultException;
import lombok.Getter;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.configuration.internal.AuditEntitiesConfiguration;
import org.hibernate.envers.internal.entities.EntitiesConfigurations;
import org.hibernate.envers.internal.entities.EntityConfiguration;

import javax.persistence.EntityManager;

public class EnversAuditSupport {


    private EntitiesConfigurations configurations;

    @Getter
    private AuditEntitiesConfiguration auditEntitiesConfiguration;

    @Getter
    private Class<?> auditEntity;

    public EnversAuditSupport(EntityManager em) {
        ClassLoader loader = this.getClass().getClassLoader();
        SessionFactoryImplementor sessionFactoryImplementor = em.getEntityManagerFactory().unwrap(SessionFactoryImplementor.class);
        EnversService enversService = sessionFactoryImplementor.getServiceRegistry().getService(EnversService.class);

        this.auditEntitiesConfiguration = enversService.getAuditEntitiesConfiguration();
        this.configurations = enversService.getEntitiesConfigurations();
        try {
            this.auditEntity = loader.loadClass(auditEntitiesConfiguration.getRevisionInfoEntityName());
        } catch (ClassNotFoundException e) {
            throw new OwnDefaultException("alma", e);
        }
    }

    public <T> boolean isAudited(Class<T> entity) {
        return configurations.get(entity.getName()) != null;
    }

    public <T> EntityConfiguration getEntityConfiguration(Class<T> entity) {
        return configurations.get(entity.getName());
    }

}
