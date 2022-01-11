package hu.idne.backend.mappers.system;

import com.google.common.reflect.TypeToken;
import org.springframework.lang.NonNull;

@FunctionalInterface
public interface ToDtoFunction<E, D> {

    @NonNull
    D toDto(@NonNull E from, @NonNull D to);

    @SuppressWarnings("unchecked")
    @NonNull
    default Class<D> forDto() {
        TypeToken<D> typeToken = new TypeToken<D>(getClass()) {
        };
        return (Class<D>) typeToken.getRawType();
    }
}
