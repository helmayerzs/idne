package hu.idne.backend.models.system;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class QueryOrder implements Serializable {

    @NonNull
    private Integer precedence = 0;

    @NonNull
    private OrderDirection dir = OrderDirection.ASC;
}
