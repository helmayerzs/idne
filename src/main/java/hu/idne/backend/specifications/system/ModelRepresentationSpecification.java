package hu.idne.backend.specifications.system;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

public abstract class ModelRepresentationSpecification<T, E> implements Specification<E> {
    @NonNull
    @Getter(AccessLevel.PROTECTED)
    protected final transient T model;

    public ModelRepresentationSpecification(T model) {
        this.model = model;
    }
}
