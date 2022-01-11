package hu.idne.backend.validations.business;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.constraint.CharSequenceConstraint;
import am.ik.yavi.core.Validator;
import hu.idne.backend.models.business.Post;
import hu.idne.backend.validations.system.BaseValidation;
import hu.idne.backend.validations.system.ModelValidation;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostValidation extends BaseValidation implements ModelValidation<Post> {

    @Override
    @NonNull
    public Validator<Post> getDefaultValidator() {
        return ValidatorBuilder.of(Post.class)
                .constraint(Post::getId, "ID", c -> c.greaterThan(0L))
                .constraint(Post::getTitle, "TITLE", CharSequenceConstraint::notBlank)
                .constraint(Post::getContent, "CONTENT", CharSequenceConstraint::notBlank)
                .build();
    }
}
