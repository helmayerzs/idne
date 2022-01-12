package hu.idne.backend.models.business;

import hu.idne.backend.models.system.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "posts")
@Audited
@EqualsAndHashCode(callSuper = false)
public class Post extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "video_link")
    private String videoLink;

    @NotAudited
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostDocument> documents;

    @Override
    public Long getIdentifier() {
        return id;
    }

    @Override
    public boolean hasId() {
        return true;
    }
}
