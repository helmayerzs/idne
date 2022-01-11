package hu.idne.backend.services.system.impl;


import hu.idne.backend.repositories.system.OpenRepository;
import hu.idne.backend.services.system.CommandService;
import hu.idne.backend.specifications.system.ExampleSpecification;
import hu.idne.backend.specifications.system.QueryContainerSpecification;
import hu.idne.backend.models.system.QueryContainer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Transactional
public abstract class CommandServiceImpl<E, I extends Serializable, R extends OpenRepository<E, I>> extends BasicCommandServiceImpl<E, I, R> implements CommandService<E, I> {

    private final CriteriaUpdateBuilder<E> builder;

    public CommandServiceImpl(R repository) {
        super(repository);
        this.builder = new CriteriaUpdateBuilder<>(repository.getAssociatedEntityManager(), repository.getAssociatedDomainClass());
    }

    @Override
    public int persist(@NonNull List<E> entities) {
        return (int) entities.stream().map(this::persist).count();
    }

    @Override
    public int persist(@NonNull Stream<E> entities) {
        return (int) entities.map(this::persist).count();
    }

    @Override
    public int updateWhere(Map<String, Object> request, Example<E> where) {
        return updateExecutor(request, new ExampleSpecification<>(where));
    }

    @Override
    public int updateWhere(Map<String, Object> request, Specification<E> where) {
        return updateExecutor(request, where);
    }

    @Override
    public int updateWhere(Map<String, Object> request, QueryContainer where) {
        return updateExecutor(request, new QueryContainerSpecification<>(where, repository.getAssociatedDomainClass(), repository.getAssociatedEntityManager()));
    }

    private int updateExecutor(Map<String, Object> updateRequest, Specification<E> applyTo) {
        CriteriaUpdate<E> update = builder.creteUpdate(updateRequest, applyTo);
        return repository.getAssociatedEntityManager().createQuery(update).executeUpdate();
    }

    @Override
    public int delete(List<I> entities) {
        int count = 0;
        for (I id : entities) {
            delete(id);
            count += 1;
        }
        return count;
    }

    @Override
    public int deleteWhere(Specification<E> applyTo) {
        EntityManager em = repository.getAssociatedEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<E> delete = cb.createCriteriaDelete(repository.getAssociatedDomainClass());
        Root<E> root = delete.from(repository.getAssociatedDomainClass());

        Predicate predicate = applyTo.toPredicate(root, cb.createQuery(), cb);

        delete.where(predicate);

        return em.createQuery(delete).executeUpdate();
    }

    @Override
    public int deleteWhere(Example<E> applyTo) {
        return deleteWhere(new ExampleSpecification<E>(applyTo));
    }

    @Override
    public int deleteWhere(QueryContainer applyTo) {
        Specification<E> specification = new QueryContainerSpecification<>(applyTo, repository.getAssociatedDomainClass(), repository.getAssociatedEntityManager());
        return deleteWhere(specification);
    }
}
