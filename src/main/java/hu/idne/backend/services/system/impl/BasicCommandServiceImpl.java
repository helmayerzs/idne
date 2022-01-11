package hu.idne.backend.services.system.impl;

import am.ik.yavi.core.ConstraintViolations;
import hu.idne.backend.exceptions.ConstraintViolationsException;
import hu.idne.backend.exceptions.DeleteException;
import hu.idne.backend.exceptions.PersistException;
import hu.idne.backend.repositories.system.OpenRepository;
import hu.idne.backend.services.system.BasicCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Slf4j
@Service
@Transactional
public abstract class BasicCommandServiceImpl<E, I extends Serializable, R extends OpenRepository<E, I>> extends AncientServiceImpl<E, I, R> implements BasicCommandService<E, I> {


    public BasicCommandServiceImpl(@NonNull R repository) {
        super(repository);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ConstraintViolationsException if validation fails for entity
     * @throws PersistException              if fail to persist the given entity, can be caused by many thing, good luck have debug this
     */
    @Override
    @NonNull
    public E       persist(@NonNull E entity) {
         ConstraintViolations violations = validate(entity);

        if (!violations.isValid()) {
            throw new ConstraintViolationsException(String.format("Validation failed for %s class", repository.getAssociatedDomainClass().getName()), violations, entity);
        }

        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new PersistException(String.format("Unable to persist entity %s caused by", entity.toString()), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NonNull I id) {
        delete(repository.getOne(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NonNull E entity) {
        if (!exist(extractID(entity))) {
            throw new DeleteException(String.format("Entity with wit id %s is not persisted", extractID(entity)));
        }
        repository.deleteById(extractID(entity));
    }

}
