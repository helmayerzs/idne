package hu.idne.backend.services.system;

import java.util.List;
import java.util.Optional;

public interface BasicQueryService<E, I> extends AncientService<E, I> {

    Optional<E> findOne(I id);

    List<E> findAll();
}
