package hu.idne.backend.models.system.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.idne.backend.models.system.EntityReference;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class EntityResourceDTO<I extends Serializable> implements Serializable {

    @JsonProperty("__ref__")
    private EntityReference<I> referenceToReferencedEntity;
}
