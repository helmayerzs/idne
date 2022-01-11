package hu.idne.backend.models.business.dtos;

import hu.idne.backend.annotations.EntityResource;
import hu.idne.backend.models.system.dtos.AbstractDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "PostDocument")
@EqualsAndHashCode(callSuper = false)
@EntityResource(name = "post_documents")
public class PostDocumentDTO extends AbstractDTO<Long> {

    private Long id;
    private PostDTO post;
    private String folder;
    private String fileName;

}
