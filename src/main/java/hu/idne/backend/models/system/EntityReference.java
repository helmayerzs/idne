package hu.idne.backend.models.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EntityReference<I extends Serializable> implements Serializable {

    @JsonProperty("__resource__")
    private String nameOfTheResource;

    @JsonProperty("__url__")
    private String urlOfTheResource;

    @JsonProperty("__id__")
    private I idOfTheResource;
}
