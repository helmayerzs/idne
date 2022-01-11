package hu.idne.backend.models.business.queries;

import lombok.Data;

@Data
public class PostDocumentQuery {
    private Long id;
    private PostQuery post;
    private String folder;
    private String fileName;
}
