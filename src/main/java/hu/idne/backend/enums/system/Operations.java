package hu.idne.backend.enums.system;

import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public enum Operations implements Operation {
    AND {
        @Override
        @SuppressWarnings("unchecked")
        public Specification apply(Specification one, Specification other) {
            return one.and(other);
        }
    },
    OR {
        @Override
        @SuppressWarnings("unchecked")
        public Specification apply(Specification one, Specification other) {
            return one.or(other);
        }
    };

    public static boolean hasValue(String value) {
        return Arrays.stream(Operations.values()).map(Enum::name).anyMatch(s -> s.equals(value));
    }
}
