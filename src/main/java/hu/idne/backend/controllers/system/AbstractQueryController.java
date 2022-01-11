package hu.idne.backend.controllers.system;

import hu.idne.backend.annotations.QueryParam;
import hu.idne.backend.models.system.CountResponse;
import hu.idne.backend.models.system.CountResult;
import hu.idne.backend.models.system.ExtendedCountRequest;
import hu.idne.backend.models.system.ExtendedQueryRequest;
import hu.idne.backend.services.system.QueryService;
import hu.idne.backend.specifications.system.ExampleSpecification;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractQueryController<S extends QueryService<E, I>, E, I extends Serializable> extends AbstractBaseController<E> implements QueryController<E, I, E> {

    protected final S service;

    public AbstractQueryController(S service) {
        this.service = service;
    }

    @Override
    @ApiOperation("Get item with the given ID")
    @GetMapping("/{id}")
    public E getOne(@PathVariable(name = "id") I id) {
        return service.findOne(id).orElse(null);
    }

    @Override
    @ApiOperation("Get all item")
    @GetMapping()
    public List<E> getAll() {
        return service.findAll();
    }

    @Override
    @ApiOperation("Query items by example, null and 'version' field will be ignored by default")
    @GetMapping("/search")
    public List<E> search(@QueryParam E entity) {
        return service.filter(toExample(entity));
    }

    @Override
    @ApiOperation("Query items by QueryRequest, null and 'version' field will be ignored by default")
    @GetMapping("/search/request")
    public List<E> search(@QueryParam ExtendedQueryRequest<E> request) {
        if (request.getQuery() == null) {
            return service.filter(request);
        }

        Specification<E> specification = new ExampleSpecification<>(toExample(request.getQuery()));
        return service.filter(request, specification);
    }

    @Override
    @ApiOperation("Count items by example, null and 'version' field will be ignored by default")
    @GetMapping("/count")
    public CountResponse count(@QueryParam E item) {
        E entity = mapper.convertValue(item, monoTypeReference());
        return new CountResponse(service.count(toExample(entity)));
    }

    @Override
    @ApiOperation("Count request, null and 'version' field will be ignored by default")
    @GetMapping("/count/request")
    public CountResult count(@QueryParam ExtendedCountRequest<E> request) {
        if (request.getQuery() == null) {
            return service.count(request);
        }

        Specification<E> specification = new ExampleSpecification<>(toExample(request.getQuery()));
        return service.count(request, specification);
    }
}
