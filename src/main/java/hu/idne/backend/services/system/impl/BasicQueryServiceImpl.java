package hu.idne.backend.services.system.impl;

import hu.idne.backend.annotations.QueryService;
import hu.idne.backend.repositories.system.OpenRepository;
import hu.idne.backend.services.system.BasicQueryService;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@QueryService
@Transactional(readOnly = true)
public abstract class BasicQueryServiceImpl<E, I extends Serializable, R extends OpenRepository<E, I>> extends AncientServiceImpl<E, I, R> implements BasicQueryService<E, I> {

    public BasicQueryServiceImpl(R repository) {
        super(repository);
    }

    @Override
    public Optional<E> findOne(@NonNull I id) {
        return repository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }
}
