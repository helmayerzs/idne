package hu.idne.backend.specifications.system;

import hu.idne.backend.models.system.QueryContainer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

public class QueryContainerSpecification<T> implements Specification<T> {

    private final transient QueryContainer container;
    private final transient PolishNotationSpecificationSupport<T> polishNotationSpecificationSupport;

    public QueryContainerSpecification(@lombok.NonNull QueryContainer container, @lombok.NonNull Class<T> domain, @lombok.NonNull EntityManager em) {
        this.container = container;
        this.polishNotationSpecificationSupport = new PolishNotationSpecificationSupport<>(em, domain);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        Stream<Object> queries = container.getQueries().stream();

        final Specification<T> specification;

        if (container.isPolish()) {
            specification = polishNotationSpecificationSupport.processPolish(container.getQueries().stream());
        } else {
            specification = polishNotationSpecificationSupport.processSimple(queries);
        }

        return specification.toPredicate(root, query, criteriaBuilder);
    }
}
