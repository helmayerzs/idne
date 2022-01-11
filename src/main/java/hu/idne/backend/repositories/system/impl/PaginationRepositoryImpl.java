package hu.idne.backend.repositories.system.impl;

import hu.idne.backend.models.system.PageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.repositories.system.PaginationRepository;
import hu.idne.backend.specifications.system.QueryRequestSpecification;
import hu.idne.backend.utils.system.PagingAndFilterUtil;
import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * {@inheritDoc}
 */
@Getter
public class PaginationRepositoryImpl<T, I extends Serializable> extends OpenRepositoryImpl<T, I> implements PaginationRepository<T, I> {

    public PaginationRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public PaginationRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public PageResult<T> page(PageRequest request) {
        return page(request, null, null);
    }

    @Override
    public PageResult<T> page(PageRequest request, Specification<T> additional) {
        return page(request, additional, null);
    }

    @Override
    public PageResult<T> page(PageRequest request, Example<T> additional) {
        Specification<T> specification = (root, query, cb) -> QueryByExamplePredicateBuilder.getPredicate(root, cb, additional);
        return page(request, specification);
    }

    @Override
    public PageResult<T> page(PageRequest request, Specification<T> additional, Specification<T> preFilter) {
        PageResult<T> pageResult = new PageResult<>();

        long recordsTotal = preFilter == null ? count() : count(preFilter);

        if (recordsTotal == 0) {
            return pageResult;
        }

        pageResult.setRecordsTotal(recordsTotal);

        Specification<T> specification = new QueryRequestSpecification<>(getAssociatedEntityManager(), request);

        if (additional != null) {
            specification = specification.and(additional);
        }

        if (preFilter != null) {
            specification = specification.and(preFilter);
        }

        Page<T> data = findAll(specification, PagingAndFilterUtil.toPageable(request));

        pageResult.setData(data.getContent());
        pageResult.setRecordsFiltered(data.getTotalElements());

        return pageResult;
    }
}

