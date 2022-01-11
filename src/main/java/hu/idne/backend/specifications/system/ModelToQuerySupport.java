package hu.idne.backend.specifications.system;

import hu.idne.backend.annotations.ForSpecification;
import hu.idne.backend.exceptions.OwnDefaultException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class ModelToQuerySupport {

    private ModelToQuerySupport() {
    }

    @SuppressWarnings("unchecked")
    public static <T, E> ModelRepresentationSpecification<T, E> toSpecification(Object object) {
        Class<T> representer = (Class<T>) object.getClass();

        if (!isRepresent(representer)) {
            throw new OwnDefaultException(String.format("ForSpecification annotation is not preset on class %s", representer.getName()));
        }

        ForSpecification annotation = representer.getAnnotation(ForSpecification.class);
        Class<? extends ModelRepresentationSpecification> specificationClass = annotation.represent();
        Constructor<? extends ModelRepresentationSpecification> constructor;

        try {
            constructor = specificationClass.getConstructor(representer);
        } catch (NoSuchMethodException e) {
            throw new OwnDefaultException("Congratulation! It must be on of the biggest bug ever found in java", e);
        }

        try {
            return (ModelRepresentationSpecification<T, E>) constructor.newInstance(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new OwnDefaultException("Something went wrong during new instance creation", e);
        }
    }

    public static boolean isRepresent(Class<?> representer) {
        return representer.isAnnotationPresent(ForSpecification.class);
    }

}
