package hu.idne.backend.models.system;

import hu.idne.backend.listeners.AbstractEntityListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Audited
@EntityListeners(AbstractEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity<I extends Serializable> implements AbstractPersistentEntity<I> {

    /**
     * Verzió mező, alapértelmezett érték 0, és minden módosítás után eggyel növekszik az értéke
     */
    @Version
    @Column(name = "version")
    private long version;

    /**
     * Audit mező, az adatsor készitésének dátuma
     */
    @Column(name = "created_by", updatable = false, nullable = false)
    private String createdBy;

    /**
     * Audit mező, az adatsor készitésének időpontja (dátum és idő)
     */
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    /**
     * Az utolsó módosítás végző neve
     */
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    /**
     * Az utolsó módosítás időpontja (Dátom és idő)
     */
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;
}

