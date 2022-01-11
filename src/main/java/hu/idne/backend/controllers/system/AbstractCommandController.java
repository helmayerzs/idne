package hu.idne.backend.controllers.system;

import am.ik.yavi.core.ConstraintViolations;
import hu.idne.backend.exceptions.SaveException;
import hu.idne.backend.exceptions.UpdateException;
import hu.idne.backend.models.system.DeletedResponse;
import hu.idne.backend.models.system.PersistentEntity;
import hu.idne.backend.models.system.UpdateWhereRequest;
import hu.idne.backend.models.system.UpdatedResponse;
import hu.idne.backend.services.system.AncientService;
import hu.idne.backend.services.system.CommandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCommandController<S extends CommandService<E, I> & AncientService<E, I>, E extends PersistentEntity<I>, I extends Serializable> extends AbstractBaseController<E> implements CommandController<E, I> {

    protected final S service;

    public AbstractCommandController(S service) {
        this.service = service;
    }

    @Override
    @ApiOperation(value = "Save one item")
    @PostMapping
    public E save(@RequestBody E entity) {
        if (service.exist(entity.getIdentifier())) {
            throw new SaveException(String.format("Item with ID '%s' is exist so you can't save it, please check if the given ID is correct  ", entity.getIdentifier()));
        }
        return service.persist(entity);
    }

    @Override
    @ApiOperation(value = "Update one item with the given ID")
    @PutMapping(path = "/{id}")
    public E update(@PathVariable(name = "id") I id, @RequestBody E entity) {
        if (!id.equals(entity.getIdentifier())) {
            throw new UpdateException("cannot update, no id preset or you want to change the id, that is illegal as hell :D");
        }

        if (service.exist(id)) {
            return service.persist(entity);
        }

        throw new UpdateException(String.format("Item with ID '%s' is not exist so you can't update it, please check if the given ID is correct  ", id));
    }

    @Override
    @ApiOperation(value = "Update multiple item")
    @PutMapping(path = "/batch")
    public UpdatedResponse update(@RequestBody List<E> entities) {
        return new UpdatedResponse(service.update(entities));
    }

    @Override
    @ApiOperation(value = "Create an update query")
    @PutMapping(path = "/where")
    public UpdatedResponse update(@RequestBody UpdateWhereRequest<E> item) {
        return new UpdatedResponse(service.updateWhere(toTypedRequest(item), toExample(item.getQuery())));
    }

    @Override
    @ApiOperation(value = "Delete an item with the given ID")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") I id) {
        service.delete(id);
    }

    @Override
    @ApiOperation(value = "Delete multiple item with the given IDs")
    @DeleteMapping(path = "/batch")
    public DeletedResponse deleteMultiple(@RequestBody List<I> ids) {
        return new DeletedResponse(service.delete(ids));
    }

    @Override
    @ApiOperation(value = "Create a delete query")
    @DeleteMapping("/where")
    public DeletedResponse deleteBatch(@RequestBody E query) {
        return new DeletedResponse(service.deleteWhere(toExample(query)));
    }

    @Override
    @ApiOperation(value = "Validate an item")
    @PostMapping("/validate")
    public ConstraintViolations validate(@RequestBody E entity) {
        return service.validate(entity);
    }

}
