package hu.idne.backend.services.system.impl;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import hu.idne.backend.exceptions.ModelMapperException;
import hu.idne.backend.mappers.system.ModelMapper;
import hu.idne.backend.mappers.system.ModelMapperKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ModelMapperService {

    private final ApplicationContext context;
    private final ListMultimap<ModelMapperKey<?, ?>, ModelMapper<?, ?>> mappers = ArrayListMultimap.create();

    @Autowired
    public ModelMapperService(@NonNull ApplicationContext context) {
        this.context = context;
        context.getBeansOfType(ModelMapper.class).values().forEach(mapper -> mappers.put(mapper.key(), mapper));
        log.debug("All mapper bean are registered: {}", mappers);
    }

    public Module factoryToDtoSerializerModule() {
        SimpleModule module = new SimpleModule();
        primaryMappers().forEach(mapper -> addToDtoConverter(module, mapper));
        return module;
    }

    public Module factoryToDtoDeserializerModule() {
        SimpleModule module = new SimpleModule();
        primaryMappers().forEach(mapper -> addToEntityConverter(module, mapper));
        return module;
    }

    public List<ModelMapper<?, ?>> primaryMappers() {
        return mappers.keySet()
                .stream()
                .map(mappers::get)
                .map((Function<List<ModelMapper<?, ?>>, ModelMapper<?, ?>>) mprs -> {
                    AnnotationAwareOrderComparator.sort(mprs);
                    return mprs.get(0);
                }).collect(Collectors.toList());
    }

    public Set<ModelMapperKey<?, ?>> getMapperKeys() {
        return mappers.keySet();
    }

    public <E, D> D mapToDto(E entity, ModelMapper<E, D> mapper) {
        return mapper.toDto(entity, constructViaReflection(mapper.forDto()));
    }

    public <E, D> D mapToDto(E entity, Class<? extends ModelMapper<E, D>> bean) {
        ModelMapper<E, D> mapper = context.getBean(bean);
        return mapper.toDto(entity, constructViaReflection(mapper.forDto()));
    }

    public <E, D> E mapToEntity(D dto, ModelMapper<E, D> mapper) {
        return mapper.toEntity(dto, constructViaReflection(mapper.forEntity()));
    }

    public <E, D> E mapToEntity(D dto, Class<? extends ModelMapper<E, D>> bean) {
        ModelMapper<E, D> mapper = context.getBean(bean);
        return mapper.toEntity(dto, constructViaReflection(mapper.forEntity()));
    }

    private <E, D> void addToDtoConverter(SimpleModule module, ModelMapper<E, D> mapper) {
        StdDelegatingSerializer serializer = new StdDelegatingSerializer(mapper.forEntity(), new ToDtoConverter<>(mapper));
        module.addSerializer(mapper.forEntity(), serializer);
    }

    @SuppressWarnings("unchecked")
    private <E, D> void addToEntityConverter(SimpleModule module, ModelMapper<E, D> mapper) {
        JavaType type = SimpleType.constructUnsafe(mapper.forDto());
        StdDelegatingDeserializer deserializer = new StdDelegatingDeserializer(new ToEntityConverter(mapper), type, null);


        module.addDeserializer(mapper.forEntity(), deserializer);
    }

    @NonNull
    private <T> T constructViaReflection(@NonNull Class<T> clazz) {
        Constructor<T> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new ModelMapperException(String.format("There is no default constructor for model '%s'", clazz), e);
        }

        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ModelMapperException(String.format("Can't create a new instance from model '%s' caused by", clazz), e);
        }
    }

    class ToDtoConverter<E, D> extends StdConverter<E, D> {

        private final ModelMapper<E, D> function;

        ToDtoConverter(ModelMapper<E, D> function) {
            this.function = function;
        }

        @Override
        public D convert(E source) {

            D target = constructViaReflection(function.forDto());
            return function.toDto(source, target);
        }

    }

    class ToEntityConverter<E, D> extends StdConverter<D, E> {

        private final ModelMapper<E, D> function;

        ToEntityConverter(ModelMapper<E, D> function) {
            this.function = function;
        }

        @Override
        public E convert(D source) {
            E target = constructViaReflection(function.forEntity());
            return function.toEntity(source, target);
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return typeFactory.constructType(function.forDto());
        }


        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return typeFactory.constructType(function.forEntity());
        }
    }
}
