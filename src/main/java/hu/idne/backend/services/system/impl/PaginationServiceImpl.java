package hu.idne.backend.services.system.impl;

import hu.idne.backend.models.system.PageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.models.system.QueryContainer;
import hu.idne.backend.repositories.system.PaginationRepository;
import hu.idne.backend.services.system.PaginationService;
import hu.idne.backend.specifications.system.QueryContainerSpecification;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional(readOnly = true)
public abstract class PaginationServiceImpl<E, I extends Serializable, R extends PaginationRepository<E, I>> implements PaginationService<E> {

    @Getter(AccessLevel.PROTECTED)
    protected final R repository;

    public PaginationServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public PageResult<E> page(PageRequest request) {
        return repository.page(request);
    }

    @Override
    public PageResult<E> page(PageRequest request, Example<E> example) {
        return repository.page(request, example);
    }

    @Override
    public PageResult<E> page(PageRequest request, Specification<E> specification) {
        return repository.page(request, specification);
    }

    @Override
    public PageResult<E> page(PageRequest request, QueryContainer container) {
        Specification<E> specification = new QueryContainerSpecification<>(container, repository.getAssociatedDomainClass(), repository.getAssociatedEntityManager());
        return page(request, specification);
    }
}
