package hu.idne.backend.controllers.system;


import hu.idne.backend.models.system.ExtendedPageRequest;
import hu.idne.backend.models.system.PageResult;

public interface PaginationController<E, T> {

    PageResult<E> page(ExtendedPageRequest<T> request);
}
