package hu.idne.backend.specifications.system;

import hu.idne.backend.models.system.QueryRequest;
import hu.idne.backend.utils.system.QueryRequestSpecificationUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class QueryRequestSpecification<T> implements Specification<T> {

    private final QueryRequest input;
    private final transient EntityManager em;

    public QueryRequestSpecification(EntityManager em, QueryRequest input) {
        this.em = em;
        this.input = input;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        QueryRequestSpecificationUtil<T> util = new QueryRequestSpecificationUtil<>(em, root, query, cb, input);
        return util.buildPredicate();
    }
}
