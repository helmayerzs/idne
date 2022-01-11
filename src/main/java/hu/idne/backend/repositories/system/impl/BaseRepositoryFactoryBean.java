package hu.idne.backend.repositories.system.impl;


import hu.idne.backend.repositories.system.CountRepository;
import hu.idne.backend.repositories.system.OpenRepository;
import hu.idne.backend.repositories.system.PaginationRepository;
import hu.idne.backend.repositories.system.QueryRepository;
import hu.idne.backend.repositories.system.RevisionRepository;
import hu.idne.backend.repositories.system.StreamRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * mindig BaseRepository-t gyárt le ez a factory, bármilyen plusz függőség be jön a egy repositorynál írtelem szerűen ezen a factorien keresztű adódik át
 *
 * @param <T> a repository típusa, mindig BaseRepository
 * @param <S> az Entitás típusa
 * @param <I> ID index típusa
 */
public class BaseRepositoryFactoryBean<T extends OpenRepository<S, I>, S, I extends Serializable & Comparable> extends JpaRepositoryFactoryBean<T, S, I> {


    public BaseRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @NonNull
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(@NonNull EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }

    private class BaseRepositoryFactory extends JpaRepositoryFactory {

        private final EntityManager entityManager;

        public BaseRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @NonNull
        @Override
        @SuppressWarnings({"unchecked", "Duplicates"})
        protected RepositoryComposition.RepositoryFragments getRepositoryFragments(@NonNull RepositoryMetadata metadata) {

            List<Object> fragments = new ArrayList<>();
            Class<?> repo = metadata.getRepositoryInterface();
            EntityInformation<S, I> entityInfo = getEntityInformation((Class<S>) metadata.getDomainType());

            if (PaginationRepository.class.isAssignableFrom(repo)) {
                fragments.add(getTargetRepositoryViaReflection(PaginationRepositoryImpl.class, entityInfo, entityManager));
            }

            if (QueryRepository.class.isAssignableFrom(repo)) {
                fragments.add(getTargetRepositoryViaReflection(QueryRepositoryImpl.class, entityInfo, entityManager));
            }

            if (CountRepository.class.isAssignableFrom(repo)) {
                fragments.add(getTargetRepositoryViaReflection(CountRepositoryImpl.class, entityInfo, entityManager));
            }

            if (StreamRepository.class.isAssignableFrom(repo)) {
                fragments.add(getTargetRepositoryViaReflection(StreamRepositoryImpl.class, entityInfo, entityManager));
            }

            if (RevisionRepository.class.isAssignableFrom(repo)) {
                fragments.add(getTargetRepositoryViaReflection(RevisionRepositoryImpl.class, entityInfo, entityManager));
            }

            if (!fragments.isEmpty()) {
                return RepositoryComposition.RepositoryFragments.just(fragments.toArray(new Object[0])).append(super.getRepositoryFragments(metadata));
            }

            return RepositoryComposition.RepositoryFragments.empty().append(super.getRepositoryFragments(metadata));

        }

        @NonNull
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return OpenRepositoryImpl.class;
        }
    }
}
