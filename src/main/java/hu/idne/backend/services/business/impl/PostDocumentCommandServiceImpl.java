package hu.idne.backend.services.business.impl;

import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import hu.idne.backend.exceptions.NotImplementedException;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.repositories.business.PostDocumentRepository;
import hu.idne.backend.services.business.PostDocumentCommandService;
import hu.idne.backend.services.system.impl.CommandServiceImpl;
import hu.idne.backend.validations.business.PostDocumentValidation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class PostDocumentCommandServiceImpl extends CommandServiceImpl<PostDocument, Long, PostDocumentRepository> implements PostDocumentCommandService {

    @Getter
    private final Validator<PostDocument> validator;

    public PostDocumentCommandServiceImpl(
            PostDocumentRepository repository,
            PostDocumentValidation validator) {
        super(repository);
        this.validator = validator.getDefaultValidator();
    }

    @Override
    public ConstraintViolations validate(@NonNull PostDocument entity) {
        return validator.validate(entity);
    }


    @Override
    public int mergePersist(@NonNull Stream<PostDocument> entityStream) {
        return (int) entityStream.map(this::mergePersist).count();
    }

    @NonNull
    @Override
    public PostDocument mergePersist(@NonNull PostDocument messageDocument) {
        throw new NotImplementedException("Need to implement");
    }
}
