package hu.idne.backend.models.system.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractDTO<I extends Serializable> extends EntityResourceDTO<I> {
    private long version;

    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "Europe/Budapest")
    @ApiModelProperty(example = "2019-12-09 13:53")
    private LocalDateTime createdDate;

    private String updatedBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "Europe/Budapest")
    @ApiModelProperty(example = "2019-12-09 13:53")
    private LocalDateTime updatedDate;
}
