package hu.idne.backend.mappers.system;

import hu.idne.backend.annotations.EntityResource;
import hu.idne.backend.exceptions.OwnDefaultException;
import hu.idne.backend.models.system.EntityReference;
import hu.idne.backend.models.system.PersistentEntity;
import hu.idne.backend.models.system.dtos.EntityResourceDTO;
import org.springframework.lang.NonNull;

public abstract class AncientMapper<E extends PersistentEntity<?>, D extends EntityResourceDTO> implements ModelMapper<E, D> {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public D toDto(@NonNull E from, @NonNull D to) {
        Class<?> dtoClass = to.getClass();

        EntityReference ref = new EntityReference();

        if (!dtoClass.isAnnotationPresent(EntityResource.class)) {
            throw new OwnDefaultException(String.format("%s  annotation is not preset on class %s", EntityResource.class, dtoClass));
        }

        ref.setNameOfTheResource(dtoClass.getAnnotation(EntityResource.class).name());
        ref.setUrlOfTheResource(String.format("/resource/%s", ref.getNameOfTheResource()));

        if (from.hasId()) {
            ref.setIdOfTheResource(from.getIdentifier());
        }

        to.setReferenceToReferencedEntity(ref);

        return to;
    }
}
