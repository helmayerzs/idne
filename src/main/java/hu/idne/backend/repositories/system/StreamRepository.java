package hu.idne.backend.repositories.system;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Stream-ekkel operáló repository
 *
 * @param <T> entitás típusa
 * @param <I> ID típusa
 */
@NoRepositoryBean
public interface StreamRepository<T, I extends Serializable> extends OpenRepository<T, I> {

    /**
     * vissza tér egy Stream-mel ami az összer adatbázisban lévő T típust addja vissza;
     *
     * @return egy T típusú stream
     */
    Stream<T> findAllAsStream();

    Stream<T> findAllAsStream(Sort sort);

    /**
     * a szűrési feltételeknek megfelelő T entitás strea-el tér vissza
     *
     * @param example szűrési feltétel
     * @return T entitás stream
     */
    Stream<T> findAllAsStream(Example<T> example);

    Stream<T> findAllAsStream(Example<T> example, Sort sort);

    /**
     * a szűrési feltételeknek megfelelő T entitás strea-el tér vissza
     *
     * @param specification szűrési feltétel
     * @return T entitás stream
     */
    Stream<T> findAllAsStream(Specification<T> specification);

    Stream<T> findAllAsStream(Specification<T> example, Sort sort);

    Stream<T> getResultAsStream(Specification<T> spec, Sort sort);
}
