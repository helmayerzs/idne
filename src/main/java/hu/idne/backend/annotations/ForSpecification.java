package hu.idne.backend.annotations;


import hu.idne.backend.specifications.system.ModelRepresentationSpecification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForSpecification {

    Class<? extends ModelRepresentationSpecification> represent();
}
