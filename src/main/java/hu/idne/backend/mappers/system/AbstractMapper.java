package hu.idne.backend.mappers.system;

import hu.idne.backend.models.system.AbstractEntity;
import hu.idne.backend.models.system.dtos.AbstractDTO;
import org.springframework.lang.NonNull;

public abstract class AbstractMapper<E extends AbstractEntity<?>, D extends AbstractDTO> extends AncientMapper<E, D> {

    @NonNull
    @Override
    public D toDto(@NonNull E from, @NonNull D to) {
        to = super.toDto(from, to);
        to.setVersion(from.getVersion());
        to.setCreatedBy(from.getCreatedBy());
        to.setCreatedDate(from.getCreatedDate());
        to.setUpdatedBy(from.getUpdatedBy());
        to.setUpdatedDate(from.getUpdatedDate());
        return to;
    }

    @NonNull
    @Override
    public E toEntity(@NonNull D from, @NonNull E to) {
        to.setVersion(from.getVersion());
        to.setCreatedBy(from.getCreatedBy());
        to.setCreatedDate(from.getCreatedDate());
        to.setUpdatedBy(from.getUpdatedBy());
        to.setUpdatedDate(from.getUpdatedDate());
        return to;
    }
}
