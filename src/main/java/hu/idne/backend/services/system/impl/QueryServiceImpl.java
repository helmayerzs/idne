package hu.idne.backend.services.system.impl;

import hu.idne.backend.models.system.CountRequest;
import hu.idne.backend.models.system.CountResult;
import hu.idne.backend.models.system.PageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.models.system.QueryContainer;
import hu.idne.backend.models.system.QueryRequest;
import hu.idne.backend.repositories.system.CountRepository;
import hu.idne.backend.repositories.system.PaginationRepository;
import hu.idne.backend.repositories.system.QueryRepository;
import hu.idne.backend.repositories.system.StreamRepository;
import hu.idne.backend.services.system.QueryService;
import hu.idne.backend.specifications.system.QueryContainerSpecification;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Transactional(readOnly = true)
public class QueryServiceImpl<E, I extends Serializable, R extends QueryRepository<E, I> & PaginationRepository<E, I> & CountRepository<E, I> & StreamRepository<E, I>> extends BasicQueryServiceImpl<E, I, R> implements QueryService<E, I> {

    public QueryServiceImpl(R repository) {
        super(repository);
    }

    @Override
    public Optional<E> findOne(Example<E> example) {
        return repository.findOne(example);
    }

    @Override
    public Optional<E> findOne(Specification<E> specification) {
        return repository.findOne(specification);
    }

    @Override
    public Optional<E> findOne(List<Specification<E>> specifications) {
        return findOne(concat(specifications));
    }

    @Override
    public Optional<E> findOne(QueryRequest request) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setColumns(request.getColumns());
        pageRequest.setSearch(request.getSearch());
        pageRequest.setStart(0);
        pageRequest.setLength(1);

        PageResult<E> result = repository.page(pageRequest);

        if (result.getRecordsFiltered() > 1) {
            throw new IncorrectResultSizeDataAccessException(1);
        }

        if (result.getRecordsFiltered() == 0) {
            return Optional.empty();
        }

        return Optional.of(result.getData().get(0));
    }


    @Override
    public List<E> findAllById(Iterable<I> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<E> filter(Specification<E> specification) {
        return repository.findAll(specification);
    }

    @Override
    public List<E> filter(Specification<E> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    @Override
    public List<E> filter(Example<E> example) {
        return repository.findAll(example);
    }

    @Override
    public List<E> filter(Example<E> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public List<E> filter(List<Specification<E>> specifications) {
        return filter(concat(specifications));
    }

    @Override
    public List<E> filter(List<Specification<E>> specifications, Sort sort) {
        return filter(concat(specifications), sort);
    }

    @Override
    public List<E> filter(QueryContainer container) {
        Specification<E> specification = new QueryContainerSpecification<>(container, repository.getAssociatedDomainClass(), repository.getAssociatedEntityManager());
        return filter(specification);
    }

    @Override
    public List<E> filter(QueryRequest request) {
        return repository.query(request);
    }

    @Override
    public List<E> filter(QueryRequest request, Specification<E> additional) {
        return repository.query(request, additional);
    }

    @Override
    public Stream<E> filterAndStream(Specification<E> specification) {
        return repository.findAllAsStream(specification);
    }

    @Override
    public Stream<E> filterAndStream(Example<E> example) {
        return repository.findAllAsStream(example);
    }

    @Override
    public Stream<E> filterAndStream(QueryContainer container) {
        Specification<E> specification = new QueryContainerSpecification<>(container, repository.getAssociatedDomainClass(), repository.getAssociatedEntityManager());
        return filterAndStream(specification);
    }

    @Override
    public Stream<E> filterAndStream(List<Specification<E>> specifications) {
        return filterAndStream(concat(specifications));
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Long count(Specification<E> specification) {
        return repository.count(specification);
    }

    @Override
    public Long count(Example<E> example) {
        return repository.count(example);
    }

    @Override
    public Long count(List<Specification<E>> specifications) {
        return repository.count(concat(specifications));
    }

    @Override
    public Long count(QueryContainer container) {
        Specification<E> specification = new QueryContainerSpecification<>(container, repository.getAssociatedDomainClass(), repository.getAssociatedEntityManager());
        return count(specification);
    }

    @Override
    public CountResult count(CountRequest request) {
        return repository.count(request);
    }

    @Override
    public CountResult count(CountRequest request, Specification<E> additional) {
        return repository.count(request, additional);
    }

    private Specification<E> concat(List<Specification<E>> specifications) {
        return specifications.stream().filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElseThrow(() -> new RuntimeException("the array must not be empty"));
    }
}
