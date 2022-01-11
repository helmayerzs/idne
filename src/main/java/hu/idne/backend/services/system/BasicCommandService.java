package hu.idne.backend.services.system;

import am.ik.yavi.core.ConstraintViolations;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public interface BasicCommandService<E, I extends Serializable> extends AncientService<E, I> {

    /**
     * @param entity item to be saved
     * @return result of the update
     * @deprecated Will be removed in later version, use @link #persist(E) instead
     */
    @Deprecated
    default E save(E entity) {
        return persist(entity);
    }

    /**
     * @param entity item to be saved
     * @return result of the update
     * @deprecated Will be removed in later version, use @link #persist(E) instead
     */
    @Deprecated
    default E update(E entity) {
        return persist(entity);
    }

    /**
     * @param entity item to be saved or update
     * @return result the entity after persist
     */
    @NonNull
    E persist(@NonNull E entity);

    /**
     * Törli az adott ID-vel rendelkező elemet a perzisztens kontextusból, abban az esetben ha az enitás auditált létrehoz egy audit bejegyzést a megfelelő audit táblába a törlésről.
     *
     * @param id Annak az elemnek az ID-ja amit törölni szeretnénk
     */
    void delete(I id);

    /**
     * Törli az adott ID-vel rendelkező elemet a perzisztens kontextusból, abban az esetben ha az enitás auditált létrehoz egy audit bejegyzést a megfelelő audit táblába a törlésről.
     *
     * @param entity Az az elem amit törölni szeretnénk!
     */
    void delete(@NonNull E entity);

    /**
     * @param entity item to validate
     * @return result of the validation,
     */
    @NonNull
    ConstraintViolations validate(@NonNull E entity);
}
