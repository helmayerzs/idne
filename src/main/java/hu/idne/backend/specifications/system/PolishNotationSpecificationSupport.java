package hu.idne.backend.specifications.system;

import hu.idne.backend.enums.system.Operation;
import hu.idne.backend.enums.system.Operations;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

public class PolishNotationSpecificationSupport<T> extends SpecificationSupport<T> {

    public PolishNotationSpecificationSupport(@NonNull EntityManager entityManager, @NonNull Class<T> domain) {
        super(entityManager, domain);
    }

    @SuppressWarnings("unchecked")
    public Specification<T> processPolish(@NonNull Stream<Object> items) {
        Deque<Specification<T>> stack = new ArrayDeque<>();
        items.forEach(o -> {
            if (!isOperation(o)) {
                stack.push(this.toSpecification(o));
            } else {
                stack.push(toOperation(o).apply(stack.pop(), stack.pop()));
            }
        });
        return stack.pop();
    }

    private boolean isOperation(@NonNull Object token) {
        if (token.getClass().equals(String.class)) {
            String operation = (String) token;
            return Operations.hasValue(operation);
        }
        return false;
    }

    private Operation toOperation(@NonNull Object token) {
        return Operations.valueOf((String) token);
    }

}
