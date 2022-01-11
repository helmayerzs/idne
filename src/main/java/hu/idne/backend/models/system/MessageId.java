package hu.idne.backend.models.system;

import hu.idne.backend.enums.system.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MessageId implements Serializable {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "lang")
    private Language language;

    @Column(name = "message_code")
    private String messageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageId)) return false;
        MessageId that = (MessageId) o;
        if (getLanguage() != that.getLanguage()) return false;
        return getMessageCode() != null ? getMessageCode().equals(that.getMessageCode()) : that.getMessageCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getLanguage() != null ? getLanguage().hashCode() : 0;
        result = 31 * result + (getMessageCode() != null ? getMessageCode().hashCode() : 0);
        return result;
    }

}
