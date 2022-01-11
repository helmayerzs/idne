package hu.idne.backend.mappers.system;

public interface ModelMapper<E, D> extends ToDtoFunction<E, D>, ToEntityFunction<E, D> {

    default ModelMapperKey<E, D> key() {
        return new ModelMapperKey<>(forEntity(), forDto());
    }
}
