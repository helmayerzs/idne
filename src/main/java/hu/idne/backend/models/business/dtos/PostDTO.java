package hu.idne.backend.models.business.dtos;

import hu.idne.backend.annotations.EntityResource;
import hu.idne.backend.models.system.dtos.AbstractDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@ApiModel(value = "Post")
@EqualsAndHashCode(callSuper = false)
@EntityResource(name = "posts")
public class PostDTO extends AbstractDTO<Long> {

    private Long id;

    private String title;
    private String content;
    private String videoLink;

    private List<PostDocumentDTO> postDocuments;

}
