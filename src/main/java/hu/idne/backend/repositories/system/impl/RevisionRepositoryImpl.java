package hu.idne.backend.repositories.system.impl;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import hu.idne.backend.exceptions.NotImplementedException;
import hu.idne.backend.exceptions.OwnDefaultException;
import hu.idne.backend.models.system.EnversAuditSupport;
import hu.idne.backend.models.system.RevisionEntityContainer;
import hu.idne.backend.repositories.system.RevisionRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RevisionRepositoryImpl<T, I extends Serializable, R> extends OpenRepositoryImpl<T, I> implements RevisionRepository<T, I, R> {

    private final EnversAuditSupport enversAuditSupport;

    public RevisionRepositoryImpl(@NonNull JpaEntityInformation<T, I> entityInformation, @NonNull EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.enversAuditSupport = new EnversAuditSupport(entityManager);
        assertAudited();
    }

    public RevisionRepositoryImpl(@NonNull Class<T> domainClass, @NonNull EntityManager entityManager) {
        super(domainClass, entityManager);
        this.enversAuditSupport = new EnversAuditSupport(entityManager);
        assertAudited();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionEntityContainer<T, R>> getAllRevisionOfId(I id) {
        //Egy adott ID-u entitás minde revízióját vissza adja
        AuditReader reader = AuditReaderFactory.get(this.getEm());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getDomainClass(), false, true).add(AuditEntity.id().eq(id));
        return extendedAuditQueryExecutor(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionEntityContainer<T, R>> findAllRevision() {
        //Nem rendezetten vissza adja az összes reviziót egy adott entitáshoz,
        AuditReader reader = AuditReaderFactory.get(this.getEm());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getDomainClass(), false, true);

        return extendedAuditQueryExecutor(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionEntityContainer<T, R>> findAllRevision(LocalDateTime from, LocalDateTime to) {
        //Nem rendezetten vissza adja az összes reviziót két dátum között
        AuditReader reader = AuditReaderFactory.get(this.getEm());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getDomainClass(), false, true)
                .add(AuditEntity.revisionProperty("timestamp").between(toMillis(from), toMillis(to)));

        return extendedAuditQueryExecutor(query);
    }

    @Override
    @Transactional(readOnly = true)
    public ListMultimap<I, RevisionEntityContainer<T, R>> findAllRevisionById() {
        //egy Map-ben vissza adja az összes változást ID-ra rendezve
        AuditReader reader = AuditReaderFactory.get(this.getEm());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getDomainClass(), false, true)
                .addOrder(AuditEntity.id().asc());

        return getRevisionMap(extendedAuditQueryExecutor(query));
    }

    @Override
    @Transactional(readOnly = true)
    public ListMultimap<I, RevisionEntityContainer<T, R>> findAllRevisionId(LocalDateTime from, LocalDateTime to) {
        //egy Map-ben vissza adja az összes változást ID-ra rendezve, adott idő intervallumra
        AuditReader reader = AuditReaderFactory.get(this.getEm());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getDomainClass(), false, true)
                .add(AuditEntity.revisionProperty("timestamp").between(toMillis(from), toMillis(to)))
                .addOrder(AuditEntity.id().asc());

        return getRevisionMap(extendedAuditQueryExecutor(query));
    }

    @Override
    @Modifying
    @Transactional
    public void deleteAllRevisionOfId(I id) {
        throw new NotImplementedException("temporary, waiting for final solution");
    }


    @SuppressWarnings("unchecked")
    private List<RevisionEntityContainer<T, R>> extendedAuditQueryExecutor(AuditQuery query) {
        List<Object[]> result = (List<Object[]>) query.getResultList();
        return result.stream().map((Function<Object[], RevisionEntityContainer<T, R>>) RevisionEntityContainer::new).collect(Collectors.toList());
    }

    private ListMultimap<I, RevisionEntityContainer<T, R>> getRevisionMap(List<RevisionEntityContainer<T, R>> data) {
        ListMultimap<I, RevisionEntityContainer<T, R>> containerMultimap = ArrayListMultimap.create();
        data.forEach(container -> containerMultimap.put(getId(container.getEntity()), container));
        return containerMultimap;
    }


    private boolean isAudited() {
        return enversAuditSupport.isAudited(getDomainClass());
    }

    private void assertAudited() {
        if (!isAudited()) {
            throw new OwnDefaultException(String.format("%s is not an audited entity", getEntityInformation().getEntityName()));
        }
    }

    private long toMillis(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime).getTime();
    }

}
