package hu.idne.backend.models.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryColumn implements Serializable {
    private String name;
    private String path;
    private Boolean searchable = false;
    private Boolean orderable = false;

    private SearchValue search;
    private QueryOrder order;

    public String getAssociatedColumnName() {
        if (!StringUtils.isEmpty(path)) {
            return path;
        }
        return name;
    }

    public boolean hasSearch() {
        return search != null && StringUtils.hasText(search.getValue());
    }
}
