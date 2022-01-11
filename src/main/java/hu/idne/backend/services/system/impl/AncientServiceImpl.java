package hu.idne.backend.services.system.impl;

import hu.idne.backend.repositories.system.OpenRepository;
import hu.idne.backend.services.system.AncientService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;

@Slf4j
public abstract class AncientServiceImpl<E, I extends Serializable, R extends OpenRepository<E, I>> implements AncientService<E, I> {

    @Getter(AccessLevel.PROTECTED)
    protected final R repository;

    AncientServiceImpl(R repository) {
        this.repository = repository;
        log.info("{} service is constructed", this.getClass().getSimpleName());
    }

    protected I extractID(E entity) {
        return repository.getAssociatedEntityInformation().getId(entity);
    }

    @Override
    public boolean isNew(@Nullable E entity) {

        if (entity == null) {
            return true;
        }

        return !exist(extractID(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean exist(@Nullable I id) {
        if (id == null) {
            return false;
        }

        EntityInformation<E, I> information = repository.getAssociatedEntityInformation();
        EntityType<E> entityType = repository.getAssociatedEntityManager().getMetamodel().entity(repository.getAssociatedDomainClass());
        SingularAttribute<E, I> declaredId = (SingularAttribute<E, I>) entityType.getId(information.getIdType());

        EntityManager em = repository.getAssociatedEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<I> query = cb.createQuery(information.getIdType());
        Root<E> root = query.from(repository.getAssociatedDomainClass());

        query.select(root.get(declaredId))
                .where(cb.equal(root.get(declaredId), id));

        return !em.createQuery(query).getResultList().isEmpty();
    }
}
