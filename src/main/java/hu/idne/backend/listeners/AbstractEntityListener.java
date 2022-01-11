package hu.idne.backend.listeners;

import hu.idne.backend.models.system.AbstractPersistentEntity;
import hu.idne.backend.models.system.AuditUserProvider;
import hu.idne.backend.utils.system.AutowireUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Slf4j
public class AbstractEntityListener {

    private AuditUserProvider auditUserProvider;
    private boolean autowired = false;

    private void autowire() {
        if (!autowired) {
            AutowireUtil.staticAutowire(this);
            autowired = true;
        }
    }

    @Autowired
    public void setAuditUserProvider(AuditUserProvider auditUserProvider) {
        this.auditUserProvider = auditUserProvider;
    }

    @PrePersist
    private void prePersist(AbstractPersistentEntity entity) {
        autowire();
        LocalDateTime currentDate = LocalDateTime.now();

        entity.setCreatedDate(currentDate);
        entity.setCreatedBy(auditUserProvider.getUser());

        entity.setUpdatedDate(currentDate);
        entity.setUpdatedBy(auditUserProvider.getUser());
    }

    @PreUpdate
    private void preUpdate(AbstractPersistentEntity entity) {
        autowire();

        LocalDateTime currentDate = LocalDateTime.now();

        entity.setUpdatedDate(currentDate);
        entity.setUpdatedBy(auditUserProvider.getUser());
    }
}
