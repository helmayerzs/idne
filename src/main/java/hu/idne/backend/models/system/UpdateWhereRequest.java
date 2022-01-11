package hu.idne.backend.models.system;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateWhereRequest<T> {
    private Map<String, Object> request;
    private T query;
}
