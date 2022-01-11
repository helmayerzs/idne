package hu.idne.backend.repositories.system;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Ez ez egyszerű repository interface arra szolgál, hogy a spring és JPA által feldolgozott JPA entitásainkról rendelkezésre álló információkat, és azokat kezelőosztályok elérhetőek legyenek kivülről is.
 *
 * @param <T> JPA entitás típusa
 * @param <I> A JPA entitásy ID mezőjének típusa
 */
@NoRepositoryBean
public interface OpenRepository<T, I extends Serializable> extends JpaRepository<T, I>, JpaSpecificationExecutor<T>, QueryByExampleExecutor<T> {

    /**
     * @return the entity manager associated with the current repository that responsible to manage tha actual entity
     */
    EntityManager getAssociatedEntityManager();

    /**
     * @return the entity class that managed by the current associated repository instance
     */
    Class<T> getAssociatedDomainClass();

    /**
     * @return the entity information that managed by the current associated repository instance
     */
    EntityInformation<T, I> getAssociatedEntityInformation();

    I getId(T entity);

}
