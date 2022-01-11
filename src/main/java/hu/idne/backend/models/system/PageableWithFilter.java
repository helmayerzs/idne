package hu.idne.backend.models.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageableWithFilter<T> extends Pageable {

    @JsonProperty("query")
    private T query;
}
