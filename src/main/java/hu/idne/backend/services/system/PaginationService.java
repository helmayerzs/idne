package hu.idne.backend.services.system;

import hu.idne.backend.models.system.PageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.models.system.QueryContainer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

public interface PaginationService<E> {

    PageResult<E> page(PageRequest request);

    PageResult<E> page(PageRequest request, Example<E> example);

    PageResult<E> page(PageRequest request, Specification<E> specification);

    PageResult<E> page(PageRequest request, QueryContainer container);
}
