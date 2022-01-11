package hu.idne.backend.models.system;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface AbstractPersistentEntity<I extends Serializable> extends PersistentEntity<I> {

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    LocalDateTime getCreatedDate();

    void setCreatedDate(LocalDateTime createdDate);

    String getUpdatedBy();

    void setUpdatedBy(String updatedBy);

    LocalDateTime getUpdatedDate();

    void setUpdatedDate(LocalDateTime updatedDate);
}
