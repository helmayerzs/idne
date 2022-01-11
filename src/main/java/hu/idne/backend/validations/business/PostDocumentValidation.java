package hu.idne.backend.validations.business;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.validations.system.BaseValidation;
import hu.idne.backend.validations.system.ModelValidation;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostDocumentValidation extends BaseValidation implements ModelValidation<PostDocument> {

    private final PostValidation postValidation;

    @Override
    @NonNull
    public Validator<PostDocument> getDefaultValidator() {
        return ValidatorBuilder.of(PostDocument.class)
                .constraint(PostDocument::getId, "ID", c -> c.greaterThan(0l))
                .constraint(PostDocument::getFolder, "FOLDER", c -> c.notBlank().lessThan(255))
                .constraint(PostDocument::getFileName, "FILE_NAME", c -> c.notBlank().lessThan(255))
                .nestIfPresent(PostDocument::getPost, "POST", postValidation.getDefaultValidator())
                .build();
    }


}
