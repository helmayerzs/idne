package hu.idne.backend.repositories.system;

import hu.idne.backend.models.system.CountRequest;
import hu.idne.backend.models.system.CountResult;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;

@NoRepositoryBean
public interface CountRepository<T, I extends Serializable> extends OpenRepository<T, I>, JpaRepository<T, I>, JpaSpecificationExecutor<T>, QueryByExampleExecutor<T> {

    CountResult count(CountRequest request);

    CountResult count(CountRequest request, Example<T> additional);

    CountResult count(CountRequest request, Specification<T> additional);

    CountResult count(CountRequest request, Specification<T> additional, Specification<T> preFilter);
}
