package hu.idne.backend.services.business.impl;

import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import hu.idne.backend.exceptions.NotImplementedException;
import hu.idne.backend.models.business.Post;
import hu.idne.backend.repositories.business.PostRepository;
import hu.idne.backend.services.business.PostCommandService;
import hu.idne.backend.services.system.impl.CommandServiceImpl;
import hu.idne.backend.validations.business.PostValidation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class PostCommandServiceImpl extends CommandServiceImpl<Post, Long, PostRepository> implements PostCommandService {


    @Getter
    private final Validator<Post> validator;

    public PostCommandServiceImpl(
            PostRepository repository,
            PostValidation validator) {
        super(repository);
        this.validator = validator.getDefaultValidator();
    }

    @Override
    public ConstraintViolations validate(@NonNull Post entity) {
        return validator.validate(entity);
    }


    @Override
    public int mergePersist(@NonNull Stream<Post> entityStream) {
        return (int) entityStream.map(this::mergePersist).count();
    }

    @NonNull
    @Override
    public Post mergePersist(@NonNull Post message) {
        throw new NotImplementedException("Need to implement");
    }
}
