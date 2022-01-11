package hu.idne.backend.repositories.system.impl;

import hu.idne.backend.repositories.system.OpenRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.EntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class OpenRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements OpenRepository<T, I> {


    @Getter(AccessLevel.PROTECTED)
    private final JpaEntityInformation<T, I> entityInformation;

    @Getter(AccessLevel.PROTECTED)
    private final EntityManager em;

    @Getter(AccessLevel.PROTECTED)
    private final PersistenceProvider provider;

    public OpenRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }

    @SuppressWarnings("unchecked")
    public OpenRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this((JpaEntityInformation<T, I>) JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    @Override
    public EntityManager getAssociatedEntityManager() {
        return em;
    }

    @Override
    public Class<T> getAssociatedDomainClass() {
        return getDomainClass();
    }

    @Override
    public EntityInformation<T, I> getAssociatedEntityInformation() {
        return getEntityInformation();
    }


    public I getId(T entity) {
        return getEntityInformation().getId(entity);
    }

}
