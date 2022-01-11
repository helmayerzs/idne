package hu.idne.backend.models.business.queries;

import lombok.Data;


@Data
public class PostQuery {
    private Long id;
    private String title;
    private String content;
    private String videoLink;
}
