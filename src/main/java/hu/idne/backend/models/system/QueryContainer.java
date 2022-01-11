package hu.idne.backend.models.system;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryContainer {

    private List<Object> queries;

    private boolean polish;
}
