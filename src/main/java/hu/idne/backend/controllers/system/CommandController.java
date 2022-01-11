package hu.idne.backend.controllers.system;


import hu.idne.backend.models.system.DeletedResponse;
import hu.idne.backend.models.system.UpdateWhereRequest;
import hu.idne.backend.models.system.UpdatedResponse;

import java.util.List;

public interface CommandController<E, I> extends SimpleCommandController<E, I> {

    UpdatedResponse update(List<E> entities);

    UpdatedResponse update(UpdateWhereRequest<E> item);

    DeletedResponse deleteMultiple(List<I> ids);

    DeletedResponse deleteBatch(E query);

    Object validate(E entity);
}
