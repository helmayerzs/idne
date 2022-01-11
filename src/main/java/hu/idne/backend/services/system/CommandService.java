package hu.idne.backend.services.system;

import hu.idne.backend.models.system.QueryContainer;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface CommandService<E, I extends Serializable> extends BasicCommandService<E, I> {

    int persist(@NonNull List<E> entities);

    int persist(@NonNull Stream<E> entities);

    /**
     * @param entities list of items to persist;
     * @return number of entities saved;
     * @deprecated will be removed in further versions
     */
    @Deprecated
    default int update(@NonNull List<E> entities) {
        return persist(entities);
    }

    int updateWhere(Map<String, Object> request, Example<E> where);

    int updateWhere(Map<String, Object> request, Specification<E> where);

    int updateWhere(Map<String, Object> request, QueryContainer where);

    int delete(List<I> entities);

    int deleteWhere(Example<E> applyTo);

    int deleteWhere(Specification<E> applyTo);

    int deleteWhere(QueryContainer applyTo);
}
