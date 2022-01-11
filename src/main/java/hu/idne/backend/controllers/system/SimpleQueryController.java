package hu.idne.backend.controllers.system;

import java.util.List;

public interface SimpleQueryController<E, I> {
    E getOne(I id);

    List<E> getAll();
}
