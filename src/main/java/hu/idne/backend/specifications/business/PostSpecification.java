package hu.idne.backend.specifications.business;

import hu.idne.backend.models.business.Post;
import hu.idne.backend.models.business.Post_;
import hu.idne.backend.models.business.queries.PostQuery;
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

public class PostSpecification implements Specification<Post> {

    private final transient PostQuery model;

    public PostSpecification(PostQuery model) {
        this.model = model;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Post> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (model.getId() != null) {
            predicates.add(cb.equal(root.get("id"),model.getId()));
        }

        if (!StringUtils.isEmpty(model.getTitle())) {
            predicates.add(cb.like(root.get(Post_.TITLE),"%"+model.getTitle()+"%"));
        }

        if (!StringUtils.isEmpty(model.getContent())) {
            predicates.add(cb.like(root.get(Post_.CONTENT),"%"+model.getContent()+"%"));
        }

        if (!StringUtils.isEmpty(model.getVideoLink())) {
            predicates.add(cb.like(root.get(Post_.VIDEO_LINK),"%"+model.getVideoLink()+"%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
