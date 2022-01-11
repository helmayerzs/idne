package hu.idne.backend.validations.system;

import am.ik.yavi.core.Validator;
import org.springframework.lang.NonNull;

public interface ModelValidation<T> {

    @NonNull
    Validator<T> getDefaultValidator();

}
