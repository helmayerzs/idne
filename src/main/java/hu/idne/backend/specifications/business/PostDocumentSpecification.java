package hu.idne.backend.specifications.business;

import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.models.business.PostDocument_;
import hu.idne.backend.models.business.Post_;
import hu.idne.backend.models.business.queries.PostDocumentQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PostDocumentSpecification implements Specification<PostDocument> {

    private final transient PostDocumentQuery model;

    public PostDocumentSpecification(PostDocumentQuery model) {
        this.model = model;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<PostDocument> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(model.getId())) {
            predicates.add(cb.equal(root.get(PostDocument_.ID), model.getId()));
        }
        if (model.getPost() != null && !StringUtils.isEmpty(model.getPost().getId())) {
            predicates.add(cb.equal(root.get(PostDocument_.POST).get(Post_.ID), model.getPost().getId()));
        }
        if (!StringUtils.isEmpty(model.getFolder())) {
           predicates.add(cb.like(root.get(PostDocument_.FOLDER),"%"+model.getFolder()+"%"));
        }
        if (!StringUtils.isEmpty(model.getFileName())) {
            predicates.add(cb.like(root.get(PostDocument_.FILE_NAME),"%"+model.getFileName()+"%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
