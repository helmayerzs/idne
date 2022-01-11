package hu.idne.backend.repositories.system.impl;

import hu.idne.backend.exceptions.OwnDefaultException;
import hu.idne.backend.repositories.system.StreamRepository;
import lombok.NonNull;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.util.CloseableIterator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import hu.idne.backend.specifications.system.ExampleSpecification;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * {@inheritDoc}
 */
public class StreamRepositoryImpl<T, I extends Serializable> extends OpenRepositoryImpl<T, I> implements StreamRepository<T, I> {

    private static final Logger logger = LoggerFactory.getLogger(StreamRepositoryImpl.class);

    public StreamRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public StreamRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<T> findAllAsStream() {
        return this.findAllAsStream(Sort.unsorted());
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<T> findAllAsStream(Sort sort) {
        return getResultAsStream(null, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<T> findAllAsStream(Example<T> example) {
        return findAllAsStream(example, Sort.unsorted());
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<T> findAllAsStream(Example<T> example, Sort sort) {
        return getResultAsStream(new ExampleSpecification<>(example), sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<T> findAllAsStream(Specification<T> specification) {
        return findAllAsStream(specification, Sort.unsorted());
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<T> findAllAsStream(Specification<T> specification, Sort sort) {
        return getResultAsStream(specification, sort);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Stream<T> getResultAsStream(Specification<T> spec, Sort sort) {

        if (!getProvider().canExtractQuery()) {
            throw new OwnDefaultException("Cant unwrap query string");
        }

        org.hibernate.query.Query<?> query = buildQuery(spec, sort).unwrap(org.hibernate.query.Query.class);
        ScrollableResults scrollableResult = query.setReadOnly(TransactionSynchronizationManager.isCurrentTransactionReadOnly())
                .scroll(ScrollMode.FORWARD_ONLY);

        logger.debug("iterator created");
        HibernateScrollableResultsIterator closableIterator = new HibernateScrollableResultsIterator(scrollableResult);


        logger.debug("iterator returned");
        return iteratorToStream(closableIterator).map(o -> (T) o);
    }

    private Stream<Object> iteratorToStream(CloseableIterator<Object> closeableIterator) {
        logger.debug("stream from iterator");

        Spliterator<Object> spliterator = Spliterators.spliteratorUnknownSize(closeableIterator, Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false).onClose(closeableIterator::close);
    }

    private Query buildQuery(Specification<T> spec, Sort sort) {
        return this.buildQuery(spec, this.getDomainClass(), sort);
    }

    private <S extends T> TypedQuery<S> buildQuery(Specification<S> spec, @NonNull Class<S> domainClass, @NonNull Sort sort) {
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = specBuilder(spec, domainClass, query);
        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return addRepositoryMetadata(getEm().createQuery(query));
    }

    private <S, U extends T> Root<U> specBuilder(Specification<U> spec, @NonNull Class<U> domainClass, @NonNull CriteriaQuery<S> query) {

        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        query.where(predicate);

        return root;
    }

    private <S> TypedQuery<S> addRepositoryMetadata(TypedQuery<S> query) {

        if (getRepositoryMethodMetadata() == null) {
            return query;
        }

        LockModeType type = getRepositoryMethodMetadata().getLockModeType();
        return type == null ? query : query.setLockMode(type);

    }
}
