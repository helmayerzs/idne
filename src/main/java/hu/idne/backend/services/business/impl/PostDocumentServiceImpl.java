package hu.idne.backend.services.business.impl;

import hu.idne.backend.models.business.Post;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.services.business.PostDocumentCommandService;
import hu.idne.backend.services.business.PostDocumentService;
import hu.idne.backend.services.business.PostQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@AllArgsConstructor
@Service
@Transactional
public class PostDocumentServiceImpl implements PostDocumentService {

    private final PostQueryService postQueryService;
    private final PostDocumentCommandService postDocumentCommandService;

    @Override
    public Boolean addPhoto(Long postId, String folder, String fileName) {
        Optional<Post> post = postQueryService.findOne(postId);
        if (post.isPresent()) {
            PostDocument postDocument = new PostDocument();
            postDocument.setPost(post.get());
            postDocument.setFolder(folder);
            postDocument.setFileName(fileName);
            postDocumentCommandService.persist(postDocument);
            return true;
        }
        return false;
    }
}

