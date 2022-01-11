package hu.idne.backend.enums.system;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface Operation {
    <T> Specification<T> apply(Specification<T> one, Specification<T> other);
}
