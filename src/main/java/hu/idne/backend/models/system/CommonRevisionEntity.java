package hu.idne.backend.models.system;

import hu.idne.backend.listeners.CommonRevisionEntityListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "common_revision_entity")
@RevisionEntity(CommonRevisionEntityListener.class)
public class CommonRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "triggered_by")
    private String triggeredBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultRevisionEntity)) {
            return false;
        }

        final DefaultRevisionEntity that = (DefaultRevisionEntity) o;
        return getId() == that.getId() && getTimestamp() == that.getTimestamp();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), triggeredBy);
    }
}
