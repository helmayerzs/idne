package hu.idne.backend.repositories.system;

import hu.idne.backend.models.system.QueryRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface QueryRepository<T, I extends Serializable> extends OpenRepository<T, I> {

    List<T> query(QueryRequest request);

    List<T> query(QueryRequest request, Example<T> additional);

    List<T> query(QueryRequest request, Specification<T> additional);

    List<T> query(QueryRequest request, Specification<T> additional, Specification<T> preFilter);
}
