package hu.idne.backend.models.system;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QueryRequest implements Serializable {
    protected transient List<QueryColumn> columns;
    protected SearchValue search;

    public Collection<QueryColumn> getSearchableColumns() {
        return this.columns.stream().filter(QueryColumn::getSearchable).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean hasSearch() {
        return search != null && StringUtils.hasText(search.getValue());
    }
}
