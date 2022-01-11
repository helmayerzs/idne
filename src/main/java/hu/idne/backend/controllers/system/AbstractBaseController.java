package hu.idne.backend.controllers.system;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import hu.idne.backend.exceptions.OwnDefaultException;
import hu.idne.backend.models.system.UpdateWhereRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBaseController<E> {
    protected static final ExampleMatcher DEFAULT_MATCHER = ExampleMatcher.matching().withIgnorePaths("version").withIgnoreNullValues();
    protected ObjectMapper mapper;

    protected JavaType monoTypeReference() {
        TypeToken<E> typeToken = new TypeToken<E>(getClass()) {
        };
        return mapper.getTypeFactory().constructType(typeToken.getRawType());
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    protected ExampleMatcher matcher() {
        return DEFAULT_MATCHER;
    }

    protected Example<E> toExample(E entity) {
        return Example.of(entity, matcher());
    }

    protected Object getValue(E entity, String field) {
        try {
            return new PropertyDescriptor(field, entity.getClass()).getReadMethod().invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            throw new OwnDefaultException("Something went wrong", e);
        }
    }

    protected Map<String, Object> toTypedRequest(UpdateWhereRequest<E> item) {
        Map<String, Object> requestMap = item.getRequest();
        E request = mapper.convertValue(requestMap, monoTypeReference());

        Map<String, Object> typedRequestMap = new HashMap<>();
        requestMap.keySet().forEach(s -> typedRequestMap.put(s, getValue(request, s)));

        return typedRequestMap;
    }
}
