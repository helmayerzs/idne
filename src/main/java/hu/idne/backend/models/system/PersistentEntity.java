package hu.idne.backend.models.system;


import java.io.Serializable;

/**
 * minimális követelmény ahhoz hogy valami entitás legyen szerintünk
 *
 * @param <I> dz ID mező típusa
 */
public interface PersistentEntity<I extends Serializable> extends Serializable {

    long getVersion();

    void setVersion(long version);

    I getIdentifier();

    default boolean hasId() {
        return getIdentifier() != null;
    }
}
