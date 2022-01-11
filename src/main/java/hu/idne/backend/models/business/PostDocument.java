package hu.idne.backend.models.business;

import hu.idne.backend.models.system.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@ToString
@Entity
@Table(name = "post_documents")
@Audited
@EqualsAndHashCode(callSuper=false)
public class PostDocument extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @Audited(targetAuditMode = NOT_AUDITED)
    private Post post;

    @Column(name = "folder", nullable = false)
    private String folder;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Override
    public Long getIdentifier() {
        return id;
    }
}
