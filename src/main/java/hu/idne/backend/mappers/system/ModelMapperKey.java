package hu.idne.backend.mappers.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelMapperKey<E, D> {
    @NonNull
    private Class<E> entityType;

    @NonNull
    private Class<D> dtoType;
}
