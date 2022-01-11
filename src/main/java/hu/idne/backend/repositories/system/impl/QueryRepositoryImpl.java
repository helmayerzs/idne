package hu.idne.backend.repositories.system.impl;

import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.models.system.QueryRequest;
import hu.idne.backend.repositories.system.QueryRepository;
import hu.idne.backend.specifications.system.QueryRequestSpecification;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueryRepositoryImpl<T, I extends Serializable> extends OpenRepositoryImpl<T, I> implements QueryRepository<T, I> {

    public QueryRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public QueryRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<T> query(QueryRequest request) {
        return query(request, null, null);
    }

    @Override
    public List<T> query(QueryRequest request, Example<T> additional) {
        Specification<T> specification = (root, query, cb) -> QueryByExamplePredicateBuilder.getPredicate(root, cb, additional);
        return query(request, specification, null);
    }

    @Override
    public List<T> query(QueryRequest request, Specification<T> additional) {
        return query(request, additional, null);
    }

    @Override
    public List<T> query(QueryRequest request, Specification<T> additional, Specification<T> preFilter) {
        PageResult<T> result = new PageResult<>();

        long recordsTotal = preFilter == null ? count() : count(preFilter);

        if (recordsTotal == 0) {
            return new ArrayList<>();
        }

        result.setRecordsTotal(recordsTotal);

        Specification<T> specification = new QueryRequestSpecification<>(getAssociatedEntityManager(), request);

        if (additional != null) {
            specification = specification.and(additional);
        }

        if (preFilter != null) {
            specification = specification.and(preFilter);
        }

        return findAll(specification);
    }
}
