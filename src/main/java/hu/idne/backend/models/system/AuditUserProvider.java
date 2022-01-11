package hu.idne.backend.models.system;

@FunctionalInterface
public interface AuditUserProvider {

    String getUser();
}
