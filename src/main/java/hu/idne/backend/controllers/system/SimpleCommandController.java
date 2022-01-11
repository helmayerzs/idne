package hu.idne.backend.controllers.system;

public interface SimpleCommandController<E, I> {

    E save(E item);

    E update(I id, E item);

    void delete(I id);
}
