package hu.idne.backend.services.system.impl;

import hu.idne.backend.exceptions.OwnDefaultException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.Map;
import java.util.function.Supplier;

public class CriteriaUpdateBuilder<E> {
    private final EntityManager em;
    private final Class<E> domainClass;
    private final EntityType<E> entityType;

    @SuppressWarnings("unchecked")
    public CriteriaUpdateBuilder(EntityManager em, Class<E> domainClass) {
        this.em = em;
        this.domainClass = domainClass;
        this.entityType = (EntityType<E>) em.getMetamodel().getEntities().stream()
                .filter(et -> domainClass.equals(et.getJavaType()))
                .findFirst()
                .orElseThrow((Supplier<RuntimeException>) () -> new OwnDefaultException("What man? how?"));
    }

    @SuppressWarnings("unchecked")
    private Attribute<E, ?> getAttribute(String name) {
        try {
            return (Attribute<E, ?>) entityType.getAttribute(name);
        } catch (IllegalArgumentException e) {
            throw new OwnDefaultException(String.format("Entity %s has no such attribute named %s", domainClass, name));
        }
    }

    private boolean isGoodType(Attribute<E, ?> attribute, Object value) {
        if (value == null) {
            return true;
        }
        return attribute.getJavaType().isInstance(value);
    }

    public CriteriaUpdate<E> creteUpdate(final Map<String, Object> updateRequest, Specification<E> specification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<E> update = cb.createCriteriaUpdate(domainClass);
        Root<E> root = update.from(domainClass);

        updateRequest.forEach((key, value) -> {
            Attribute<E, ?> attribute = getAttribute(key);
            if (!isGoodType(attribute, value)) {
                throw new OwnDefaultException("type miss match");
            }
            update.set(key, value);
        });


        Predicate predicate = specification.toPredicate(root, cb.createQuery(), cb);

        update.where(predicate);

        return update;
    }

}
