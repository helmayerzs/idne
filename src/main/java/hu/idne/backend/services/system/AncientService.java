package hu.idne.backend.services.system;

import org.springframework.lang.Nullable;

public interface AncientService<E, I> {

    boolean isNew(@Nullable E entity);

    boolean exist(@Nullable I id);
}
