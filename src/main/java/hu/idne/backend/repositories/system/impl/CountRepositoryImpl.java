package hu.idne.backend.repositories.system.impl;

import hu.idne.backend.models.system.CountRequest;
import hu.idne.backend.models.system.CountResult;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.repositories.system.CountRepository;
import hu.idne.backend.specifications.system.QueryRequestSpecification;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class CountRepositoryImpl<T, I extends Serializable> extends OpenRepositoryImpl<T, I> implements CountRepository<T, I> {

    public CountRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public CountRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public CountResult count(CountRequest request) {
        return count(request, null, null);
    }

    @Override
    public CountResult count(CountRequest request, Example<T> additional) {
        Specification<T> specification = (root, query, cb) -> QueryByExamplePredicateBuilder.getPredicate(root, cb, additional);
        return count(request, specification, null);
    }

    @Override
    public CountResult count(CountRequest request, Specification<T> additional) {
        return count(request, additional, null);
    }

    @Override
    public CountResult count(CountRequest request, Specification<T> additional, Specification<T> preFilter) {
        CountResult result = new PageResult<>();

        long recordsTotal = preFilter == null ? count() : count(preFilter);

        result.setRecordsTotal(recordsTotal);

        if (recordsTotal == 0) {
            result.setRecordsFiltered(0L);
        }

        result.setRecordsTotal(recordsTotal);

        Specification<T> specification = new QueryRequestSpecification<>(getAssociatedEntityManager(), request);

        if (additional != null) {
            specification = specification.and(additional);
        }

        if (preFilter != null) {
            specification = specification.and(preFilter);
        }

        result.setRecordsFiltered(count(specification));

        return result;
    }
}
