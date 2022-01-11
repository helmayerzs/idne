package hu.idne.backend.specifications.system;

import hu.idne.backend.exceptions.OwnDefaultException;
import lombok.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpecificationSupport<T> {

    private final EntityManager entityManager;
    private final Class<T> domain;

    public SpecificationSupport(@NonNull EntityManager entityManager, @NonNull Class<T> domain) {
        this.entityManager = entityManager;
        this.domain = domain;
    }

    public boolean isJpaEntity(@NonNull Object item) {
        if (!domain.isInstance(item)) {
            return false;
        }

        Stream<EntityType<?>> stream = entityManager.getMetamodel().getEntities().stream();
        return stream
                .filter(entityType -> entityType.getJavaType() != null)
                .anyMatch(entityType -> entityType.getJavaType().equals(domain));
    }

    public boolean isRepresent(@NonNull Object item) {
        return ModelToQuerySupport.isRepresent(item.getClass());
    }

    public Specification<T> processSimple(@NonNull Stream<Object> items) {
        return items.map(this::toSpecification).reduce(Specification::and).orElseThrow((Supplier<RuntimeException>) () -> new OwnDefaultException("lol"));
    }

    @SuppressWarnings("unchecked")
    public Specification<T> toSpecification(@NonNull Object item) {
        if (isJpaEntity(item)) {
            return entityToSpecification((T) item);
        } else if (isRepresent(item)) {
            return modelToSpecification(item);
        } else {
            throw new OwnDefaultException(String.format("%s is not an acceptable as a query representing class", item.getClass().getName()));
        }
    }

    public Specification<T> entityToSpecification(@NonNull T entity) {
        return new ExampleSpecification<>(Example.of(entity));
    }

    public Specification<T> modelToSpecification(@NonNull Object model) {
        return ModelToQuerySupport.toSpecification(model);
    }
}
