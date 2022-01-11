package hu.idne.backend.services.system;

import hu.idne.backend.models.system.CountRequest;
import hu.idne.backend.models.system.CountResult;
import hu.idne.backend.models.system.QueryContainer;
import hu.idne.backend.models.system.QueryRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface QueryService<E, I extends Serializable> extends BasicQueryService<E, I> {

    Optional<E> findOne(Example<E> example);

    Optional<E> findOne(Specification<E> specification);

    Optional<E> findOne(List<Specification<E>> specification);

    Optional<E> findOne(QueryRequest specification);


    List<E> findAllById(Iterable<I> ids);


    List<E> filter(Specification<E> specification);

    List<E> filter(Specification<E> specification, Sort sort);

    List<E> filter(Example<E> example);

    List<E> filter(Example<E> example, Sort sort);

    List<E> filter(List<Specification<E>> specification);

    List<E> filter(List<Specification<E>> specification, Sort sort);

    List<E> filter(QueryContainer container);

    List<E> filter(QueryRequest request);

    List<E> filter(QueryRequest request, Specification<E> additional);


    Stream<E> filterAndStream(Specification<E> specification);

    Stream<E> filterAndStream(List<Specification<E>> specification);

    Stream<E> filterAndStream(Example<E> example);

    Stream<E> filterAndStream(QueryContainer container);


    Long count();

    Long count(Specification<E> specification);

    Long count(Example<E> example);

    Long count(List<Specification<E>> specification);

    Long count(QueryContainer container);

    CountResult count(CountRequest request);

    CountResult count(CountRequest request, Specification<E> additional);
}
