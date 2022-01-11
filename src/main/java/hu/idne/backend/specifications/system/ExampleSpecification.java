package hu.idne.backend.specifications.system;


import lombok.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ExampleSpecification<T> implements Specification<T> {

    private final transient Example<T> example;

    public ExampleSpecification(@NonNull Example<T> example) {
        this.example = example;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
    }

}
