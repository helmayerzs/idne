package hu.idne.backend.services.system;

import org.springframework.lang.NonNull;

import java.util.stream.Stream;

public interface MergePersist<T> {

    int mergePersist(@NonNull Stream<T> entityStream);

    @NonNull
    T mergePersist(@NonNull T entity);

}
