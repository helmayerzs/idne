package hu.idne.backend.repositories.system;


import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Base repository, a repository öröklődési fa tetején van mindig és üres, extendálja az összes használt repository-et
 *
 * @param <T> entitás típusa
 * @param <I> ID típusa
 */
@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable> extends PaginationRepository<T, I>, QueryRepository<T, I>, CountRepository<T, I>, StreamRepository<T, I> {

}
