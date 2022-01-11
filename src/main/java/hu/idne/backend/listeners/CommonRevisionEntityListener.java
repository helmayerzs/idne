package hu.idne.backend.listeners;

import hu.idne.backend.models.system.AuditUserProvider;
import hu.idne.backend.models.system.CommonRevisionEntity;
import hu.idne.backend.utils.system.AutowireUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CommonRevisionEntityListener implements RevisionListener {

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

    public void newRevision(Object revisionEntity) {
        autowire();
        CommonRevisionEntity exampleRevEntity = (CommonRevisionEntity) revisionEntity;
        exampleRevEntity.setTriggeredBy(auditUserProvider.getUser());
    }
}
