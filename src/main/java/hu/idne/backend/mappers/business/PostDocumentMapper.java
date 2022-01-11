package hu.idne.backend.mappers.business;

import hu.idne.backend.mappers.system.AbstractMapper;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.models.business.dtos.PostDocumentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PostDocumentMapper extends AbstractMapper<PostDocument, PostDocumentDTO> {

    @Override
    public PostDocumentDTO toDto(PostDocument from, PostDocumentDTO to) {
        to = super.toDto(from, to);

        to.setId(from.getId());
        to.setFileName(from.getFileName());
        to.setFolder(from.getFolder());

        return to;
    }

    @Override
    public PostDocument toEntity(PostDocumentDTO from, PostDocument to) {
        to = super.toEntity(from, to);

        to.setId(from.getId());
        to.setFileName(from.getFileName());
        to.setFolder(from.getFolder());

        return to;
    }
}
