package hu.idne.backend.models.system;

import lombok.Data;

@Data
public class CountResult {
    protected Long recordsTotal;
    protected Long recordsFiltered;
}
