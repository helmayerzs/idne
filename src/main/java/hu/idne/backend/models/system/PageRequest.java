package hu.idne.backend.models.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PageRequest extends CountRequest implements Serializable {
    private Integer start;
    private Integer length;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("sorted_field")
    private String sortedField;
}
