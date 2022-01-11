package hu.idne.backend.models.system;


import hu.idne.backend.exceptions.OwnDefaultException;

import static hu.idne.backend.utils.system.CaseConverter.upperCaseLowerHyphenToLowerCaseDotted;

public interface MessageKey {

    default String key() {
        if (this.getClass().isEnum()) {
            Enum<?> thisEnum = (Enum<?>) this;
            return upperCaseLowerHyphenToLowerCaseDotted(thisEnum.name());
        }
        throw new OwnDefaultException("implement me pls");
    }

}