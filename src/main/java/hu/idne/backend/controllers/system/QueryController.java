package hu.idne.backend.controllers.system;

import hu.idne.backend.models.system.CountResponse;
import hu.idne.backend.models.system.CountResult;
import hu.idne.backend.models.system.ExtendedCountRequest;
import hu.idne.backend.models.system.ExtendedQueryRequest;

import java.util.List;

public interface QueryController<E, I, T> extends SimpleQueryController<E, I> {

    List<E> getAll();

    List<E> search(E entity);

    List<E> search(ExtendedQueryRequest<T> request);

    CountResponse count(E item);

    CountResult count(ExtendedCountRequest<T> request);
}
