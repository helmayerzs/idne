package hu.idne.backend.mappers.system;

import com.google.common.reflect.TypeToken;
import org.springframework.lang.NonNull;

@FunctionalInterface
public interface ToEntityFunction<E, D> {

    @NonNull
    E toEntity(@NonNull D from, @NonNull E to);

    @SuppressWarnings("unchecked")
    @NonNull
    default Class<E> forEntity() {
        TypeToken<E> typeToken = new TypeToken<E>(getClass()) {
        };
        return (Class<E>) typeToken.getRawType();
    }
}
