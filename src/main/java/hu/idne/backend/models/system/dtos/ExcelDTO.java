package hu.idne.backend.models.system.dtos;

import hu.idne.backend.models.system.AbstractEntity;
import hu.idne.backend.models.system.AbstractPersistentEntity;
import hu.idne.backend.utils.system.DateCommons;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
public abstract class ExcelDTO {

    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    /*
    Not real issue, since in this case the type beyond it being an AbstractEntity doesn't matter
    */
    @SuppressWarnings("squid:S3740")
    public ExcelDTO(@NonNull AbstractEntity entity) {
        this.createdBy = entity.getCreatedBy();
        this.createdDate = DateCommons.asDate(entity.getCreatedDate());
        this.updatedBy = entity.getUpdatedBy();
        this.updatedDate = DateCommons.asDate(entity.getUpdatedDate());
    }

    /*
    Not real issue, since in this case the type beyond it being an AbstractEntity doesn't matter
    */
    @SuppressWarnings("squid:S3740")
    public ExcelDTO(@NonNull AbstractPersistentEntity entity) {
        this.createdBy = entity.getCreatedBy();
        this.createdDate = DateCommons.asDate(entity.getCreatedDate());
        this.updatedBy = entity.getUpdatedBy();
        this.updatedDate = DateCommons.asDate(entity.getUpdatedDate());
    }

}
