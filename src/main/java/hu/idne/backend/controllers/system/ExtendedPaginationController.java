package hu.idne.backend.controllers.system;

import hu.idne.backend.annotations.QueryParam;
import hu.idne.backend.models.system.ExtendedPageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.services.system.PaginationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class ExtendedPaginationController<S extends PaginationService<E>, E, T> extends AbstractBaseController<E> implements PaginationController<E, T> {

    protected final S service;

    public ExtendedPaginationController(S service) {
        this.service = service;
    }

    @GetMapping(path = "/page")
    public PageResult<E> page(@QueryParam ExtendedPageRequest<T> request) {
        if (request.getQuery() == null) {
            return service.page(request);
        }
        return service.page(request, toSpecification(request.getQuery()));
    }

    protected abstract Specification<E> toSpecification(@NonNull T input);
}
