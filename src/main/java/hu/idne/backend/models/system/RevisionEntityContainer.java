package hu.idne.backend.models.system;

import hu.idne.backend.exceptions.OwnDefaultException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.RevisionType;

@Getter
@Setter
@ToString
public class RevisionEntityContainer<E, R> {
    private E entity;
    private R revision;
    private RevisionType type;

    public RevisionEntityContainer() {
    }

    @SuppressWarnings("unchecked")
    public RevisionEntityContainer(Object[] arr) {
        if (arr.length != 3) {
            throw new OwnDefaultException("Excuse me WTF? Call a priest ASAP");
        }

        this.entity = (E) arr[0];
        this.revision = (R) arr[1];
        this.type = (RevisionType) arr[2];
    }
}
