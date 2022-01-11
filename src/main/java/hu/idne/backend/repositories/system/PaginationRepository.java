package hu.idne.backend.repositories.system;

import hu.idne.backend.models.system.PageRequest;
import hu.idne.backend.models.system.PageResult;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;

/**
 * saját lapozós, keresős, rendezős repository,
 * <p>
 * Saát alternatíva PagingAndOrderingRepository-ra
 * <p>
 * {@link PageRequest} teljesen meg egyezzik a Datatables javascript plugin ajaxos query objektumával, illetbe létezik hozzá hozzá programozottan könyne használható builder is {@link }
 * <p>
 * A kimenet minden esetben egy {@link PageResult} ami teljesen megfelel a Datatables ajaxos bemeneti objektumával, de bármivel könyenn feldolgozható
 * <p>
 *
 * @param <T> JPA entitás típusa
 * @param <I> A JPA entitásy ID mezőjének típusa
 */
@NoRepositoryBean
public interface PaginationRepository<T, I extends Serializable> extends OpenRepository<T, I>, JpaRepository<T, I>, JpaSpecificationExecutor<T>, QueryByExampleExecutor<T> {

    /**
     * egyszerű keresés, rendezés és lapozást
     *
     * @param request query objektum {@link PageRequest}
     * @return a lapozás/szűrés/rendezés/eredménye
     */
    PageResult<T> page(PageRequest request);

    /**
     * egyszerű keresés, rendezés és lapozást egy előre elkészített egyébb szűrési specifikációval
     *
     * @param request query objektum {@link PageRequest}
     * @param additional     egyább szűrési example
     * @return a lapozás/szűrés/rendezés/eredménye
     */
    PageResult<T> page(PageRequest request, Example<T> additional);

    /**
     * egyszerű keresés, rendezés és lapozást egy előre elkészített egyébb szűrési specifikációval
     *
     * @param request             query objektum {@link PageRequest}
     * @param additional egyább szűrési specifikácó
     * @return a lapozás/szűrés/rendezés/eredménye
     */
    PageResult<T> page(PageRequest request, Specification<T> additional);

    /**
     * ebben a esetben meg adható egy előszűrési specifikáció ami ha 0 számoságot add vissza akkor nem fut meg a drágább egyébb specifikáció az alap lapozó/szűrő/rendező query-vel
     *
     * @param request               query objektum {@link PageRequest}
     * @param additional   egyább szűrési specifikácó
     * @param preFilter elő szűrési specifikáció, affélre shortcut ként és működhet
     * @return a lapozás/szűrés/rendezés/eredménye
     */
    PageResult<T> page(PageRequest request, Specification<T> additional, Specification<T> preFilter);

}
